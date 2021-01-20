package br.com.nivlabs.gp.models.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * DTO resumido de especialidade
 * 
 * @author viniciosarodrigues
 *
 */
@ApiModel("Especialidade")
public class SpecialityDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = -8113675301888267134L;

    @ApiModelProperty("Identificador único da especialidade")
    private Long id;

    @ApiModelProperty("Nome da especialidade")
    private String name;

    public SpecialityDTO(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SpecialityDTO other = (SpecialityDTO) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SpecialityDTO [id=" + id + ", name=" + name + "]";
    }

}
