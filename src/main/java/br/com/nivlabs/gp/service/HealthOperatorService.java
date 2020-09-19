package br.com.nivlabs.gp.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.controller.filters.HealthOperatorFilters;
import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.HealthOperator;
import br.com.nivlabs.gp.models.domain.HealthPlan;
import br.com.nivlabs.gp.models.dto.HealthOperatorDTO;
import br.com.nivlabs.gp.models.dto.HealthOperatorInfoDTO;
import br.com.nivlabs.gp.models.dto.HealthPlanDTO;
import br.com.nivlabs.gp.repository.HealthOperatorRepository;
import br.com.nivlabs.gp.repository.HealthPlanRepository;

/**
 * 
 * Classe HealthOperatorService.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 04 de agosto de 2020
 */
@Service
public class HealthOperatorService implements GenericService {

	@Autowired
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private HealthOperatorRepository healthOperatorRepository;

	@Autowired
	private HealthPlanRepository healthPlanRepository;

	/**
	 * Busca uma página de operadoras de planos de saúde
	 * 
	 * @param pageRequest
	 * @return Page
	 */
	public Page<HealthOperatorDTO> getListOfHealthOperator(HealthOperatorFilters filters, Pageable pageRequest) {
		logger.info("Inicinado busca filtrada por Operadoras de saúde");
		return healthOperatorRepository.resumedList(filters, pageRequest);
	}

	/**
	 * Busca os detalhes de uma operadora de plano de saúde
	 * 
	 * @param id
	 * @return HealthOperatorInfoDTO
	 */
	public HealthOperatorInfoDTO findByHealthOperatorId(Long id) {
		HealthOperator healthOperator = healthOperatorRepository.findById(id)
				.orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
						String.format("Operadora com o identificador %s não encontrado", id)));
		List<HealthPlan> plans = healthOperator.getHealthPlans();
		List<HealthPlanDTO> plansDTO = new ArrayList<>();

		HealthOperatorInfoDTO healthOperatorInfoDTO = new HealthOperatorInfoDTO();

		plans.forEach(plan -> {
			HealthPlanDTO newHealthPlanDTO = new HealthPlanDTO();
			BeanUtils.copyProperties(plan, newHealthPlanDTO);
			plansDTO.add(newHealthPlanDTO);
		});

		healthOperatorInfoDTO.setHealthPlans(plansDTO);

		return healthOperatorInfoDTO;
	}

	/**
	 * Busca o plano de saúde pelo identificador único
	 * 
	 * @param id Identificador único do plano de saúde
	 * @return
	 */
	public HealthPlanDTO findHealthPlanById(Long id) {
		logger.info("Iniciando busca de plano de saúde por id :: {}", id);
		if (id == null) {
			throw new HttpException(HttpStatus.BAD_REQUEST, "Informe o identificador único do plano para a pesquisa");
		}
		HealthPlanDTO response = new HealthPlanDTO();
		HealthPlan objectFromDB = healthPlanRepository.findById(id)
				.orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
						String.format("Plano de saúde com o identificador %s não encontrado", id)));

		BeanUtils.copyProperties(objectFromDB, response);
		logger.info("Plano encontrado :: {}", response);

		return response;
	}

	/**
	 * Busca o plando de saúde pelo código da ANS
	 * 
	 * @param ansCode Código ANS do plano de saúde
	 * @return
	 */
	public HealthPlanDTO findHealthPlanByAnsCode(Long ansCode) {
		if (ansCode == null) {
			throw new HttpException(HttpStatus.BAD_REQUEST, "Informe o código da ANS do plano para a pesquisa");
		}
		HealthPlanDTO response = new HealthPlanDTO();
		HealthPlan objectFromDB = healthPlanRepository.findByPlanCode(ansCode)
				.orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
						String.format("Plano de saúde com o código ANS %s não encontrado", ansCode)));

		BeanUtils.copyProperties(objectFromDB, response);
		logger.info("Plano encontrado :: {}", response);

		return response;
	}

}
