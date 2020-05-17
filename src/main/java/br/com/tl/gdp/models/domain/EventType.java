package br.com.tl.gdp.models.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.tl.gdp.models.BaseObject;
import br.com.tl.gdp.models.dto.EventTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Classe EventType.java
 * 
 * @author <a href="mailto:carolexc@gmail.com">Caroline Aguiar</a>
 * 
 * @since 8 de set de 2019
 */
@Entity
@Table(name = "TIPO_EVENTO")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class EventType extends BaseObject {

    private static final long serialVersionUID = -8716334303463572525L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_EVENTO")
    private EventType superEventType;

    @Column(name = "NOME")
    private String name;

    @Column(name = "DESCRICAO")
    private String description;

    public EventType(long id) {
        this.id = id;
    }

    @JsonIgnore
    public EventTypeDTO getEventTypeDTOFromDomain() {
        EventTypeDTO dtoEntity = new EventTypeDTO();

        dtoEntity.setId(getId());
        dtoEntity.setName(getName());
        dtoEntity.setDescription(getDescription());
        return dtoEntity;
    }

}
