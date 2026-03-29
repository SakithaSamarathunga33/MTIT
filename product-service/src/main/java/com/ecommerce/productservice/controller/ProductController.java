package com.ecommerce.productservice.controller;

import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Product API", description = "CRUD operations for Products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get all products")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved list of products")
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(summary = "Get a product by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Product found"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(
            @Parameter(description = "Product ID") @PathVariable("id") String id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new product")
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product created = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "Update an existing product")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Product updated successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable("id") String id,
            @RequestBody Product product) {
        return productService.updateProduct(id, product)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a product")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get products by category")
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable("category") String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @Operation(summary = "Search products by name")
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchProductsByName(name));
    }
}
