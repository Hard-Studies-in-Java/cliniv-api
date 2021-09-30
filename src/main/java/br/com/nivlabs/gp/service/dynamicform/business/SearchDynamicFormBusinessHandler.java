package br.com.nivlabs.gp.service.dynamicform.business;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.nivlabs.gp.controller.filters.DynamicFormFilters;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.DynamicForm;
import br.com.nivlabs.gp.models.dto.DynamicFormDTO;
import br.com.nivlabs.gp.models.dto.DynamicFormQuestionDTO;
import br.com.nivlabs.gp.repository.DynamicFormRepository;
import br.com.nivlabs.gp.service.BaseBusinessHandler;

/**
 * 
 * Componente principal para busca de formulários dinâmicos
 *
 * @author viniciosarodrigues
 * @since 24-09-2021
 *
 */
@Component
public class SearchDynamicFormBusinessHandler implements BaseBusinessHandler {

    @Autowired
    private Logger logger;

    @Autowired
    private DynamicFormRepository dynamicFormRepo;

    /**
     * Busca uma página de formulários dinâmicos
     * 
     * @param pageSettings Configurações de paginação
     * @return Página de formulários de anamnese
     */
    public Page<DynamicFormDTO> getPage(DynamicFormFilters filters, Pageable pageSettings) {
        logger.info("Iniciando busca de formulários dinâmicos..");
        return dynamicFormRepo.resumedList(filters, pageSettings);
    }

    /**
     * Busca um formulário dinâmico pelo identificador único do formulário
     * 
     * @param id Identificador único do formulário de Anamnese
     * @return Formulário dinâmico com título e perguntas
     */
    public DynamicFormDTO byId(Long id) {
        logger.info("Iniciando processo de busca de formulário dinâmico pelo identificador :: {}", id);

        DynamicForm entity = dynamicFormRepo.findById(id)
                .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
                        String.format("Formulário com o identificador %s não encontrado", id)));

        logger.info("Formulário encontrado :: {} | {} :: Iniciando processo de conversão...", entity.getId(), entity.getTitle());

        DynamicFormDTO response = new DynamicFormDTO();

        response.setId(entity.getId());
        response.setTitle(entity.getTitle());

        logger.info("Convertendo as questões do formulário :: Total de questões: {}", entity.getQuestions().size());
        entity.getQuestions().forEach(question -> {
            DynamicFormQuestionDTO convertedQuestion = new DynamicFormQuestionDTO();
            convertedQuestion.setId(question.getId());
            convertedQuestion.setMetaType(question.getMetaType());
            convertedQuestion.setQuestion(question.getQuestion());
            logger.info("Adicionando questão :: {}", convertedQuestion);
            response.getQuestions().add(convertedQuestion);
        });

        logger.info("Conversão finalizada, devolvendo resposta...");

        return response;
    }
}
