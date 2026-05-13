package com.demo.api.service;

import com.demo.api.dto.ProductRequest;
import com.demo.api.dto.ProductResponse;
import com.demo.api.exception.ResourceNotFoundException;
import com.demo.api.model.Product;
import com.demo.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<ProductResponse> findAll() {
        return repository.findAll().stream().map(ProductResponse::from).toList();
    }

    public ProductResponse findById(Long id) {
        return ProductResponse.from(getOrThrow(id));
    }

    public List<ProductResponse> findByName(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .stream().map(ProductResponse::from).toList();
    }

    public ProductResponse create(ProductRequest req) {
        Product product = Product.builder()
                .name(req.name())
                .description(req.description())
                .price(req.price())
                .build();
        return ProductResponse.from(repository.save(product));
    }

    public ProductResponse update(Long id, ProductRequest req) {
        Product product = getOrThrow(id);
        product.setName(req.name());
        product.setDescription(req.description());
        product.setPrice(req.price());
        return ProductResponse.from(repository.save(product));
    }

    public void delete(Long id) {
        getOrThrow(id);
        repository.deleteById(id);
    }

    private Product getOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }
}
