package com.example.codingexercise.service;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.example.codingexercise.entity.ProductPackage;

public interface PackageService {
    ProductPackage createPackage(String currency, ProductPackage p);
    ProductPackage getPackageById(String currency, String id);
    ProductPackage updatePackage(String id, String currency, ProductPackage p) throws NotFoundException;
    void deletePackage(String id);
    List<ProductPackage> getAllPackages(String currency);
}
