package com.zuzex.storageservice.repository;

import com.zuzex.storageservice.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p " +
            "JOIN FETCH p.reservations " +
            "WHERE p.id = :productId")
    Optional<Product> getProductByIdWithReservations(Long productId);
}
