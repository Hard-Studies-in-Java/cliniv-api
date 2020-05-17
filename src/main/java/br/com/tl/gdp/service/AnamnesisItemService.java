/**
 * 
 */
package br.com.tl.gdp.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.tl.gdp.exception.ObjectNotFoundException;
import br.com.tl.gdp.models.domain.AnamnesisItem;
import br.com.tl.gdp.repository.AnamnesisItemRepository;

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

    @Override
    public Page<AnamnesisItem> searchEntityPage(Pageable pageRequest) {
        return dao.findAll(pageRequest);
    }

    @Override
    public AnamnesisItem findById(Long id) {
        return dao.findById(id).orElseThrow(
                                            () -> new ObjectNotFoundException(
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
