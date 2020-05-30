/**
 * 
 */
package br.com.nivlabs.gp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.nivlabs.gp.exception.HttpException;
import br.com.nivlabs.gp.models.domain.AnamnesisItem;
import br.com.nivlabs.gp.models.dto.AnamnesisItemDTO;
import br.com.nivlabs.gp.repository.AnamnesisItemRepository;

/**
 * AnamnesisItem.java
 *
 * @author <a href="henriquedreyer@gmail.com">Henrique Dreyer</a>
 *
 * @since 12 de set de 2019
 * 
 */
@Service
public class AnamnesisItemService implements GenericService<AnamnesisItem, Long> {

	@Autowired
	private AnamnesisItemRepository dao;

	public Page<AnamnesisItemDTO> searchDTOPage(Pageable pageRequest) {
		Page<AnamnesisItem> pageFromDb = searchEntityPage(pageRequest);
		List<AnamnesisItemDTO> responseList = new ArrayList<>();
		pageFromDb.getContent().stream().map(AnamnesisItem::getAnamnesisItemDTOFromDomain).forEach(responseList::add);
		return new PageImpl<>(responseList, pageRequest, pageFromDb.getTotalElements());
	}

	@Override
	public Page<AnamnesisItem> searchEntityPage(Pageable pageRequest) {
		return dao.findAll(pageRequest);
	}

	@Override
	public AnamnesisItem findById(Long id) {
		return dao.findById(id).orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND,
				String.format("Anamnesis Item ID: [%s] não encontrado!", id)));
	}

	@Override
	public AnamnesisItem update(Long id, AnamnesisItem entity) {
		AnamnesisItem anamnesisItem = findById(id);
		BeanUtils.copyProperties(entity, anamnesisItem, "id");
		return dao.save(anamnesisItem);
	}

	@Override
	public void delete(AnamnesisItem entity) {
		deleteById(entity.getId());
	}

	@Override
	public void deleteById(Long id) {
		AnamnesisItem anamnesisItem = findById(id);
		dao.delete(anamnesisItem);
	}

	@Override
	public AnamnesisItem persist(AnamnesisItem entity) {
		entity.setId(null);
		return dao.save(entity);
	}

}
