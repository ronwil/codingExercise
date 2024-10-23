package com.example.codingexercise.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.codingexercise.entity.ProductPackage;
import com.example.codingexercise.gateway.ProductServiceGateway;
import com.example.codingexercise.gateway.dto.ExchangeRates;
import com.example.codingexercise.gateway.dto.Product;
import com.example.codingexercise.repository.PackageRepository;
import com.example.codingexercise.service.PackageService;

@Service
public class PackageServiceImpl implements PackageService {

    private ProductServiceGateway productServiceGateway;
    private PackageRepository packageRepository;

    public PackageServiceImpl(ProductServiceGateway productServiceGateway, PackageRepository packageRepository) {
        this.productServiceGateway = productServiceGateway;
        this.packageRepository = packageRepository;
    }

    private Double calculateTotalUsdPrice(List<String> productIds) {
        List<Product> products = productIds.stream().map(id -> productServiceGateway.getProduct(id)).toList();
        return products.stream().mapToDouble(Product::usdPrice).sum();
    }

    private Double convertPriceToCurrency(Double price, String targetCurrency) {
        ExchangeRates exchangeRates = productServiceGateway.getLatestConversion(price);
        return exchangeRates.rates().get(targetCurrency);
    }

    @Override
    public ProductPackage createPackage(String currency, ProductPackage p) {
        p.setPrice(calculateTotalUsdPrice(p.getProductIds()));
        ProductPackage savedProductPackage = packageRepository.save(p);

        if (!currency.equals( "USD")) {
            savedProductPackage.setPrice(convertPriceToCurrency(savedProductPackage.getPrice(), currency));
        } 
        return savedProductPackage;   
    }

    @Override
    public ProductPackage getPackageById(String currency, String id) {
        Optional<ProductPackage> optProductPackage =  packageRepository.findById(id);
        if (optProductPackage.isPresent()) {
            ProductPackage pp = optProductPackage.get();
            if (!currency.equals( "USD")) {
                pp.setPrice(convertPriceToCurrency(pp.getPrice(), currency));
            }
            return pp;
        } else {
            return null;
        }
    }

    @Override
    public ProductPackage updatePackage(String id, String currency, ProductPackage updatedPackage) throws NotFoundException {
       Optional<ProductPackage> existingPackage = packageRepository.findById(id);
        if (existingPackage.isPresent()) {
            ProductPackage packageToUpdate = existingPackage.get();

            if (updatedPackage.getName() != null) {
                packageToUpdate.setName(updatedPackage.getName());
            }
            if (updatedPackage.getDescription() != null) {
                packageToUpdate.setDescription(updatedPackage.getDescription());
            }
            if(updatedPackage.getProductIds().size() > 0) {
                packageToUpdate.getProductIds().addAll(updatedPackage.getProductIds());
            }
            packageToUpdate.setPrice(calculateTotalUsdPrice(packageToUpdate.getProductIds()));
            packageRepository.save(packageToUpdate);

            if(!currency.equals( "USD")) {
                packageToUpdate.setPrice(convertPriceToCurrency(packageToUpdate.getPrice(), currency));
            }

            return packageToUpdate;
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void deletePackage(String id) {
        packageRepository.deleteById(id);
    }

    @Override
    public List<ProductPackage> getAllPackages(String currency) {
        List<ProductPackage> productPackages = packageRepository.findAll();

        if (!currency.equals("USD")) {
            for (ProductPackage productPackage : productPackages) {
                productPackage.setPrice(convertPriceToCurrency(productPackage.getPrice(), currency));
            }
        }
        
        return productPackages;
    }
}
