package br.com.ft.gdp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.ft.gdp.controller.filters.ResponsibleFilters;
import br.com.ft.gdp.exception.ObjectNotFoundException;
import br.com.ft.gdp.models.domain.Person;
import br.com.ft.gdp.models.domain.Responsible;
import br.com.ft.gdp.models.dto.AddressDTO;
import br.com.ft.gdp.models.dto.DocumentDTO;
import br.com.ft.gdp.models.dto.ProfessionalIdentityDTO;
import br.com.ft.gdp.models.dto.ResponsibleDTO;
import br.com.ft.gdp.models.dto.ResponsibleInfoDTO;
import br.com.ft.gdp.models.dto.SpecialityDTO;
import br.com.ft.gdp.models.enums.DocumentType;
import br.com.ft.gdp.repository.ResponsibleRepository;
import br.com.ft.gdp.repository.SpecialityRepository;

/**
 * Classe ResponsibleService.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 7 de set de 2019
 */
@Service
public class ResponsibleService {

    @Autowired
    private ResponsibleRepository dao;

    @Autowired
    private PersonService personService;
    @Autowired
    private SpecialityRepository specDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Realiza uma busca paginada de profissionais e responsáveis
     * 
     * @param pageRequest
     * @return
     */
    public Page<ResponsibleDTO> searchEntityPage(ResponsibleFilters filters, Pageable pageRequest) {
        return dao.resumedList(filters, pageRequest);
    }

    /**
     * Busca um profissional ou responsável pelo identificador
     * 
     * @param id
     * @return
     */
    public ResponsibleInfoDTO findById(Long id) {
        Responsible responsibleFromDb = dao.findById(id).orElseThrow(
                                                                     () -> new ObjectNotFoundException(
                                                                             String.format("Responsável com ID: [%s] não encontrado", id)));
        return handelResponsible(responsibleFromDb);
    }

    /**
     * Busca um cadastro de pessoa ativa pelo CPF, utilizado para cadastro ainda não existente de profissional.
     * 
     * @param cpf
     * @return
     */
    private ResponsibleInfoDTO findPersonByCpf(String cpf) {
        Person personFromDb = personService.findByCpf(cpf);

        ResponsibleInfoDTO responsibleInfo = new ResponsibleInfoDTO();
        BeanUtils.copyProperties(personFromDb, responsibleInfo, "id");
        responsibleInfo.setDocument(new DocumentDTO(DocumentType.CPF, personFromDb.getCpf()));

        if (personFromDb.getAddress() != null) {
            AddressDTO address = new AddressDTO();
            BeanUtils.copyProperties(personFromDb.getAddress(), address);
            responsibleInfo.setAddress(address);
        }

        return responsibleInfo;
    }

    /**
     * Trata o objeto de resposta à partir do objeto domínio
     * 
     * @param responsibleOrigin
     * @return
     */
    private ResponsibleInfoDTO handelResponsible(Responsible responsibleOrigin) {
        Person person = responsibleOrigin.getPerson();

        ResponsibleInfoDTO responsibleConverted = new ResponsibleInfoDTO();
        BeanUtils.copyProperties(responsibleOrigin.getPerson(), responsibleConverted, "id");
        BeanUtils.copyProperties(responsibleOrigin, responsibleConverted);
        responsibleConverted.setDocument(new DocumentDTO(DocumentType.CPF, person.getCpf()));

        if (person.getAddress() != null) {
            AddressDTO address = new AddressDTO();
            BeanUtils.copyProperties(person.getAddress(), address);
            responsibleConverted.setAddress(address);
        }
        if (responsibleOrigin.getProfessionalIdentity() != null) {
            ProfessionalIdentityDTO professionalIdentity = new ProfessionalIdentityDTO();
            professionalIdentity.setRegisterValue(responsibleOrigin.getProfessionalIdentity());
            if (responsibleOrigin.getInitialsIdentity() != null) {
                professionalIdentity.setRegisterType(responsibleOrigin.getInitialsIdentity());
            }
            responsibleConverted.setProfessionalIdentity(professionalIdentity);
        }

        responsibleConverted.setId(responsibleOrigin.getId());

        return responsibleConverted;

    }

    /**
     * Busca um responsável pelo CPF
     * 
     * @param cpf
     * @return
     */
    public ResponsibleInfoDTO findByCpf(String cpf) {
        try {
            Responsible responsibleFromDb = dao.findByCpf(cpf).orElseThrow(
                                                                           () -> new ObjectNotFoundException(
                                                                                   String.format("Responsável com cpf: [%s] não encontrado",
                                                                                                 cpf)));
            return handelResponsible(responsibleFromDb);
        } catch (ObjectNotFoundException e) {
            return findPersonByCpf(cpf);
        }

    }

    /**
     * Atualiza o cadastro de responsável ou profissional
     * 
     * @param id
     * @param responsible
     * @return
     */
    public ResponsibleInfoDTO update(Long id, ResponsibleInfoDTO responsible) {
        logger.info("Inciando processo de atualização de profissional ou responsável...");
        Responsible responsibleFromDb = dao.findById(id).orElseThrow(
                                                                     () -> new ObjectNotFoundException(
                                                                             String.format("Responsável com ID: [%s] não encontrado", id)));

        BeanUtils.copyProperties(responsible, responsibleFromDb.getPerson(), "id", "createdAt");
        if (responsible.getProfessionalIdentity() != null) {
            responsibleFromDb.setProfessionalIdentity(responsible.getProfessionalIdentity().getRegisterValue());
            responsibleFromDb.setInitialsIdentity(responsible.getProfessionalIdentity().getRegisterType());
        }

        handleSpecializations(responsible, responsibleFromDb);
        dao.saveAndFlush(responsibleFromDb);
        return responsible;
    }

    /**
     * Trata as especializações para atualização
     * 
     * @param responsible
     * @param responsibleFrom
     */
    private void handleSpecializations(ResponsibleInfoDTO responsible, Responsible responsibleFrom) {
        logger.info("Verificando especializações...");
        if (responsible.getSpecializations() != null) {
            logger.info("Alterações encontradas, atualizando especializações do profissional :: {}",
                        responsible.getFirstName());
            responsibleFrom.setSpecializations(new ArrayList<>());
            responsible.getSpecializations().stream().map(SpecialityDTO::getId).forEach(specId -> {
                responsibleFrom.getSpecializations()
                        .add(specDao.findById(specId).orElseThrow(() -> new ObjectNotFoundException(
                                String.format("Especialidade com identificador %s não encontrada", specId))));
            });
        }
    }

    /**
     * Deleta o profissional
     * 
     * @param entity
     */
    public void delete(Responsible entity) {
        deleteById(entity.getId());
    }

    public void deleteById(Long id) {
        Responsible responsibleFromDb = dao.findById(id).orElseThrow(
                                                                     () -> new ObjectNotFoundException(
                                                                             String.format("Responsável com ID: [%s] não encontrado", id)));
        dao.delete(responsibleFromDb);

    }

    /**
     * Insere um profissional à base de dados
     * 
     * @param entity
     * @return
     */
    public Responsible persist(Responsible entity) {
        logger.info("Inciando processo de atualização de profissional ou responsável...");
        entity.setId(null);
        return dao.save(entity);
    }

    /**
     * Realiza a persistência à partir de um DTO
     * 
     * @param responsible
     * @return
     */
    public ResponsibleDTO persistDTO(ResponsibleInfoDTO responsible) {
        logger.info("Inciando processo de criação de profissional ou responsável...");
        Responsible responsibleFromDb = new Responsible();

        Person personFromDb;
        try {
            personFromDb = personService.findByCpf(responsible.getDocument().getValue());
        } catch (ObjectNotFoundException e) {
            personFromDb = new Person();
        }

        BeanUtils.copyProperties(responsible, personFromDb, "id");
        if (responsible.getProfessionalIdentity() != null) {
            responsibleFromDb.setProfessionalIdentity(responsible.getProfessionalIdentity().getRegisterValue());
            responsibleFromDb.setInitialsIdentity(responsible.getProfessionalIdentity().getRegisterType());
        }
        if (responsible.getDocument() != null && responsible.getDocument().getType().equals(DocumentType.CPF)) {
            personFromDb.setCpf(responsible.getDocument().getValue());
        }

        handleSpecializations(responsible, responsibleFromDb);

        if (personFromDb.getId() == null) {
            personService.persist(personFromDb);
        }
        responsibleFromDb.setPerson(personFromDb);
        responsibleFromDb.setCreatedAt(LocalDateTime.now());
        persist(responsibleFromDb);

        ResponsibleDTO newResponsible = new ResponsibleDTO();
        newResponsible.setId(responsibleFromDb.getId());
        BeanUtils.copyProperties(responsibleFromDb.getPerson(), newResponsible, "id");

        return newResponsible;
    }
}
