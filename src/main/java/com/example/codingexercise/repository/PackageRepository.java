package com.example.codingexercise.repository;

import org.springframework.stereotype.Repository;

import com.example.codingexercise.entity.ProductPackage;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PackageRepository extends JpaRepository<ProductPackage, String> {}
