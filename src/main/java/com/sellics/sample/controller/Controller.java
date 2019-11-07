package com.sellics.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sellics.sample.model.Product;
import com.sellics.sample.service.ProductService;

@RestController
public class Controller {

	@Autowired
	ProductService productService;

	/**
	 * 
	 * @param keyword
	 * @return
	 */
	@GetMapping("/estimate")
	public Product estimate(@RequestParam String keyword) {
	
	    double score = productService.getKeywordScore(keyword);
	    return new Product(keyword, score);
	
	}
}
