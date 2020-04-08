package br.com.ft.gdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ft.gdp.controller.filters.UserFilters;
import br.com.ft.gdp.models.dto.UserDTO;
import br.com.ft.gdp.models.dto.UserInfoDTO;
import br.com.ft.gdp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Classe SpecialityController.java
 * 
 * @author <a href="mailto:viniciosarodrigues@gmail.com">Vinícios Rodrigues</a>
 * 
 * @since 6 de jan de 2020
 */
@Api("Endpoint - Usuário")
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(nickname = "user-get", value = "Busca uma página de usuários baseada em filtros")
    @GetMapping
    @PreAuthorize("hasAnyRole('COMUM', 'ADMIN')")
    public ResponseEntity<Page<UserDTO>> findPage(UserFilters filters, Pageable pageSettings) {
        return ResponseEntity.ok(userService.searchEntityPage(filters, pageSettings));
    }

    @ApiOperation(nickname = "user-get-id", value = "Busca um usuário baseado no ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('COMUM', 'ADMIN')")
    public ResponseEntity<UserInfoDTO> findById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
}