package br.com.ft.gdp.models.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * Classe NewPatientVisitDTO.java
 *
 * @author <a href="carolexc@gmail.com">Caroline Aguiar</a>
 *
 * @since 8 de set de 2019
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel("New patient visit request")
public class NewAttandenceDTO extends DataTransferObjectBase {

    private static final long serialVersionUID = 2370290606342755763L;

    @NotNull(message = "Informar o código do paciente é obrigatório")
    private Long patientId;

    private Long eventTypeId;

    private Long specialitityId;

    private Long responsibleId;

    @NotNull(message = "Informar o setor de entrada é obrigatório")
    private Long sectorId;

    @NotNull(message = "Informar o MOTIVO da visita é obrigatório")
    private String entryCause;

}
