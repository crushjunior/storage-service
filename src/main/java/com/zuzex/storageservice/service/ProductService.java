package com.zuzex.storageservice.service;

import com.zuzex.storageservice.model.entity.Product;

public interface ProductService {

    Integer getQuantityGoods(Long productId);

    Product getProductById(Long productId);

    Product saveProduct(Product product);
}
