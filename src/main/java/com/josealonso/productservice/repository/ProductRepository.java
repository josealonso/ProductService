package com.josealonso.productservice.repository;

import com.josealonso.productservice.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository     // optional annotation
// public interface ProductRepository extends PagingAndSortingRepository<Product, UUID> {
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
