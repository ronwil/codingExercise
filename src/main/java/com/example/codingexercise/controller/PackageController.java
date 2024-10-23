package com.example.codingexercise.controller;

import com.example.codingexercise.entity.ProductPackage;
import com.example.codingexercise.service.PackageService;
import com.example.codingexercise.utils.ErrorResponse;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/packages")
public class PackageController {

    private PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @PostMapping
    public ResponseEntity<?> createPackage(@RequestBody ProductPackage p, @RequestParam(required = false, defaultValue = "USD") String currency) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(packageService.createPackage(currency, p));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Failed to create package", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPackageById(@RequestParam(required = false, defaultValue = "USD") String currency, @PathVariable String id) {
        try {
            ProductPackage productPackage = packageService.getPackageById(currency, id);
            if (productPackage != null) {
                return ResponseEntity.ok(productPackage);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Failed to retrieve package", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePackage(@PathVariable String id, @RequestBody ProductPackage p, @RequestParam(required = false, defaultValue = "USD") String currency) {
        try {
            ProductPackage updatedPackage = packageService.updatePackage(id, currency, p);
            if (updatedPackage != null) {
                return ResponseEntity.ok(updatedPackage);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Failed to update package", e.getMessage()));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable String id) {
        try {
            packageService.deletePackage(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllPackages(@RequestParam(required = false, defaultValue = "USD") String currency) {
        try {
            List<ProductPackage> productPackages = packageService.getAllPackages(currency);
            return ResponseEntity.ok(productPackages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Failed to retrieve packages", e.getMessage()));
        }
    }
}
