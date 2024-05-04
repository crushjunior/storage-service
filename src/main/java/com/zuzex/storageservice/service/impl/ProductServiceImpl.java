package com.zuzex.storageservice.service.impl;

import com.zuzex.storageservice.exception.ResourceNotFoundException;
import com.zuzex.storageservice.model.entity.Product;
import com.zuzex.storageservice.model.entity.Reservation;
import com.zuzex.storageservice.repository.ProductRepository;
import com.zuzex.storageservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Integer getQuantityGoods(Long productId) throws ResourceNotFoundException{
        log.info("Receiving from DB an affordable amount of product {}", productId);

        Product product = productRepository.getProductByIdWithReservations(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Wrong id of product"));

        List<Reservation> reservations = product.getReservations();

        int countReservations = reservations.stream().mapToInt(Reservation::getAmount).sum();

        int result = product.getAmount() - countReservations;

        return Math.max(result, 0);
    }

    @Override
    public Product getProductById(Long productId) {
        log.info("Receiving product from DB {}", productId);
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Wrong id of product"));
    }

    @Override
    public Product saveProduct(Product product) {
        log.info("Save in DB product {}", product.getId());
        return productRepository.save(product);
    }

}
