package com.demo.api.controller;

import com.demo.api.dto.ProductRequest;
import com.demo.api.dto.ProductResponse;
import com.demo.api.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "CRUD de produtos")
public class ProductController {

    private final ProductService service;

    @GetMapping
    @Operation(summary = "Lista todos os produtos")
    public List<ProductResponse> findAll(
            @RequestParam(required = false) String name) {
        return name != null ? service.findByName(name) : service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca produto por ID")
    public ProductResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria um novo produto")
    public ProductResponse create(@Valid @RequestBody ProductRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um produto existente")
    public ProductResponse update(@PathVariable Long id,
                                  @Valid @RequestBody ProductRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove um produto")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
