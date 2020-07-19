package br.com.nivlabs.gp.controller.attendance;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.config.security.UserOfSystem;
import br.com.nivlabs.gp.models.dto.EvolutionInfoDTO;
import br.com.nivlabs.gp.service.EvolutionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controlador de entrada para processos com evolução clínica do paciente
 * 
 * @author viniciosarodrigues
 *
 */
@Api(value = "Endpoint - Evolução clínica do paciente")
@RestController
@RequestMapping("/attendance/evolution")
public class EvolutionController {

    @Autowired
    private EvolutionService service;

    @ApiOperation(nickname = "evolution-post", value = "Insere uma nova evolução do paciente na aplicação")
    @PostMapping
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ADMIN')")
    public ResponseEntity<EvolutionInfoDTO> persist(@Validated @RequestBody(required = true) EvolutionInfoDTO request,
                                                    HttpServletResponse response) {
        UserOfSystem userFromSession = (UserOfSystem) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED).body(service.persist(request, userFromSession.getUsername()));
    }

    @ApiOperation(nickname = "evolution-get-id", value = "Busca uma evolução do paciente baseada no identificador")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ATENDIMENTO_ESCRITA', 'ATENDIMENTO_LEITURA', 'ADMIN')")
    public ResponseEntity<EvolutionInfoDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

}