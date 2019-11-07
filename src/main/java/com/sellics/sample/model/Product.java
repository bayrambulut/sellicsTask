package com.sellics.sample.model;

public class Product {

	private String keyword;
	
	private double score;
	
	public Product(String keyword, double score) {
		
		this.keyword = keyword;
		this.score = score;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
}
