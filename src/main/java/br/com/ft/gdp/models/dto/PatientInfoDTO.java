package br.com.ft.gdp.models.dto;

import java.time.LocalDateTime;

import br.com.ft.gdp.models.domain.PatientType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Classe NewPatientDTO.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 3 de out de 2019
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PatientInfoDTO extends PersonInfoDTO {
    private static final long serialVersionUID = 1575416178033511932L;

    private String susNumber;

    private PatientType type;

    private String annotations;

    private LocalDateTime createdAt;
}
