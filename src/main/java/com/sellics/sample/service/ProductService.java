package com.sellics.sample.service;

public interface ProductService {

	/**
	 * Calculates the estimated score of given keyword
	 * @param keyword
	 * @return
	 */
    double getKeywordScore(String keyword);

}
