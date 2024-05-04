package com.zuzex.storageservice.controller;

import com.zuzex.storageservice.exception.ErrorMessage;
import com.zuzex.storageservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Этот контроллер через Feign будет синхронно отвечать на запросы Магазина, о том какое количество
 * товара сейчас на складе доступно
 */

@RestController
@RequestMapping("/storage-service")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/products")
    @Operation(summary = "SS-1: Sending information about the available quantity of goods",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Integer.class)))),
                    @ApiResponse(responseCode = "400", description = "Bad request",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ErrorMessage.class))),
            })
    public ResponseEntity<Integer> getQuantityGoods(@RequestParam @Parameter(description = "Product ID") Long productId) {
        return ResponseEntity.ok(productService.getQuantityGoods(productId));
    }
}
