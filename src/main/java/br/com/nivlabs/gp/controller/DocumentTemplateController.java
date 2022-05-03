package br.com.nivlabs.gp.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.nivlabs.gp.controller.filters.DocumentTemplateFilter;
import br.com.nivlabs.gp.models.dto.DocumentTemplateDTO;
import br.com.nivlabs.gp.models.dto.DocumentTemplateInfoDTO;
import br.com.nivlabs.gp.service.documenttemplate.DocumentTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Templates de documentos", description = "Endpoint - Templates de documentos")
@RestController
@RequestMapping(value = "/document-template")
public class DocumentTemplateController extends BaseController<DocumentTemplateService> {

    @Operation(summary = "document-template-get", description = "Busca uma página de templates")
    @GetMapping
    public ResponseEntity<Page<DocumentTemplateDTO>> findPage(DocumentTemplateFilter filters) {
        Pageable pageSettings = PageRequest.of(filters.getPage(), filters.getSize(), Direction.valueOf(filters.getDirection()),
                                               filters.getOrderBy());
        return ResponseEntity.ok(service.getPage(filters, pageSettings));
    }

    @Operation(summary = "document-template-get-id", description = "Busca um template de documento baseado no identificador")
    @GetMapping("/{id}")
    public ResponseEntity<DocumentTemplateInfoDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
