package br.com.nivlabs.gp.service.patient.business;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.controller.filters.PatientFilters;
import br.com.nivlabs.gp.enums.DocumentType;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.Patient;
import br.com.nivlabs.gp.models.domain.Person;
import br.com.nivlabs.gp.models.domain.PersonDocument;
import br.com.nivlabs.gp.models.dto.AddressDTO;
import br.com.nivlabs.gp.models.dto.DocumentDTO;
import br.com.nivlabs.gp.models.dto.HealthPlanDTO;
import br.com.nivlabs.gp.models.dto.PatientDTO;
import br.com.nivlabs.gp.models.dto.PatientInfoDTO;
import br.com.nivlabs.gp.repository.PatientRepository;
import br.com.nivlabs.gp.repository.PersonRepository;
import br.com.nivlabs.gp.service.AttendanceService;
import br.com.nivlabs.gp.service.BaseBusinessHandler;
import br.com.nivlabs.gp.util.StringUtils;

/**
 * 
 * Camada de serviço para todo tipo de consulta de paciente
 *
 * @author viniciosarodrigues
 * @since 18-09-2021
 *
 */
@Component
public class SearchPatientBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private PatientRepository patientRepo;
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private PersonRepository personRepo;

    /**
     * Busca uma página de informações resumidas de pacientes
     * 
     * @param filters Filtros de busca
     * @param pageRequest Configurações da paginação
     * @return Página com informações resumidas de pacientes
     */
    public Page<PatientDTO> getPage(PatientFilters filters, Pageable pageRequest) {
        logger.info("Iniciando busca paginada de paciente com filtros :: {}", filters);
        return patientRepo.resumedList(filters, pageRequest);
    }

    /**
     * Busca informações detalhadas do paciente por ID
     * 
     * @param id Identificador único do paciente
     * @return Informações detalhadas do paciente
     */
    public PatientInfoDTO getById(Long id) {
        logger.info("Iniciando busca de paciente por ID :: {}", id);
        Patient patient = patientRepo.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Paciente com o identificador %s não encontrado", id)));
        return convertPatientEntity(patient);

    }

    /**
     * Busca informações do paciente baseado no Código da Carteira Nacional de Saúde
     * 
     * @param CNS Código da Carteira Nacional de Saúde
     * @return Informações detalhadas do paciente
     */
    public PatientInfoDTO getByCnsNumber(String cnsCode) {
        logger.info("Iniciando busca de paciente por CNS :: {}", cnsCode);
        Patient patient = patientRepo.findByCnsNumber(cnsCode).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                String.format("Paciente com cartão SUS de número %s não encontrado", cnsCode)));
        return convertPatientEntity(patient);
    }

    /**
     * Busca informações do paciente
     * 
     * OBS: Se o paciente não for encontrado, uma busca por informações de pessoa física é realizada e retornada
     * 
     * @param cpf CPF do paciente
     * @return Informações detalhadas do paciente ou da pessoa física
     */
    public PatientInfoDTO getByCpf(String cpf) {
        logger.info("Iniciando busca de paciente por CPF :: {}", cpf);
        try {
            if (StringUtils.isNullOrEmpty(cpf)) {
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,
                        "O CPF informado é nulo, informe um CPF para que a consulta possa ser realizada");
            }
            Patient patient = patientRepo.findByCpf(cpf).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                    String.format("Paciente com o CPF %s não encontrado", cpf)));
            return convertPatientEntity(patient);
        } catch (HttpException e) {
            if (e.getStatus() == HttpStatus.NOT_FOUND) {
                logger.warn(e.getMessage());
                logger.info("Iniciando busca de informações de pessoa física por CPF :: {}", cpf);
                Person personFromDb = personRepo.findByCpf(cpf)
                        .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "Paciente não encontrado no CPF " + cpf));

                Patient patient = new Patient();
                patient.setPerson(personFromDb);

                return convertPatientEntity(patient);
            }
            throw e;
        }
    }

    /**
     * Converte entidade do modelo relacional em objeto de transferência
     * 
     * @param patient Entidade do modelo relacional
     * @return Objeto de transferência
     */
    private PatientInfoDTO convertPatientEntity(Patient patient) {
        logger.info("Iniciando processo de conversão de dados de entidade para objeto de transferência :: Pacient -> PatientInfoDTO");
        Person person = patient.getPerson();

        PatientInfoDTO patientInfo = new PatientInfoDTO();
        patientInfo.setId(patient.getId());
        patientInfo.setPersonId(person.getId());
        patientInfo.setFullName(person.getFullName());
        patientInfo.setSocialName(person.getSocialName());
        patientInfo.setBornDate(person.getBornDate());
        patientInfo.setDocument(new DocumentDTO(null, DocumentType.CPF, person.getCpf(), null, null, null, null));
        patientInfo.setDocuments(convertDocuments(person.getDocuments()));
        patientInfo.setGender(person.getGender());
        patientInfo.setGenderIdentity(person.getGenderIdentity());
        patientInfo.setFatherName(person.getFatherName());
        patientInfo.setMotherName(person.getMotherName());
        patientInfo.setPrincipalNumber(person.getPrincipalNumber());
        patientInfo.setSecondaryNumber(person.getSecondaryNumber());

        if (person.getAddress() != null) {
            AddressDTO address = new AddressDTO();
            BeanUtils.copyProperties(person.getAddress(), address);
            patientInfo.setAddress(address);
        }

        patientInfo.setProfilePhoto(person.getProfilePhoto());
        patientInfo.setCnsNumber(patient.getCnsNumber());
        patientInfo.setType(patient.getType());
        patientInfo.setAnnotations(patient.getAnnotations());
        patientInfo.setCreatedAt(patient.getCreatedAt());

        patient.getAllergies().forEach(allergy -> patientInfo.getAllergies().add(allergy.getDescription()));

        handletPatientHistory(patientInfo);

        if (patient.getHealthPlan() != null) {
            HealthPlanDTO healthPlan = new HealthPlanDTO();
            BeanUtils.copyProperties(patient.getHealthPlan(), healthPlan);
            healthPlan.setPatientPlanNumber(patient.getHealthPlanCode());
            healthPlan.setOperatorCode(patient.getHealthPlan().getHealthOperator().getAnsCode());
            healthPlan.setOperatorName(patient.getHealthPlan().getHealthOperator().getFantasyName());
            patientInfo.setHealthPlan(healthPlan);
        }

        patientInfo.setEthnicGroup(person.getEthnicGroup());
        patientInfo.setBloodType(person.getBloodType());
        patientInfo.setNationality(person.getNationality());

        return patientInfo;
    }

    /**
     * Converte documentos de domínio para documentos de transferência
     * 
     * @param documents Lista de documentos à serem convertidos
     * @return Lista de documentos convertidos
     */
    private List<DocumentDTO> convertDocuments(List<PersonDocument> documents) {
        List<DocumentDTO> convertedDocuments = new ArrayList<>();

        documents.forEach(doc -> {
            DocumentDTO convertedDoc = new DocumentDTO();
            BeanUtils.copyProperties(doc, convertedDoc);
            convertedDoc.setPersonId(doc.getId().getPersonId());
            convertedDoc.setType(doc.getId().getType());
            convertedDoc.setValue(doc.getId().getValue());
            convertedDocuments.add(convertedDoc);
        });

        return convertedDocuments;
    }

    /**
     * Adiciona o histórico do paciente
     * 
     * @param patientInfo Informações detalhadas do paciente
     */
    private void handletPatientHistory(PatientInfoDTO patientInfo) {
        try {
            if (patientInfo.getId() != null) {
                logger.info("Buscando histórico de atendimentos do paciente...");
                patientInfo.getAttendanceHistory().addAll(attendanceService.getAttandenceByPatientId(patientInfo.getId()));
            }
        } catch (HttpException e) {
            logger.info("Nenhum atendimento para o paciente...");
        }
    }
}
