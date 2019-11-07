package com.sellics.sample.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import com.sellics.sample.model.AmazonResponse;
import com.sellics.sample.model.Suggestion;

@Service
public class ProductServiceImpl implements ProductService {

	Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
    @Override
    public double getKeywordScore(String keyword) {

        Date startDate = new Date();

        int divisor = 0;
        int index = 0;
        double score = 0;

        //Make as many as request within 10 seconds
        while((new Date().getTime() - startDate.getTime() < 90000) && index < keyword.length()) {

            index ++;
            String subText = keyword.substring(0, index);
            //logger.info("call : " + index);
            List<Suggestion> list = getSuggestions(subText);

            double currentScore = 0;
            //logger.info(list.size() + "");
            if(list.size() > 1)
                currentScore = calculateScore(keyword, list);

            score += currentScore * index;
            divisor += index;
            
        }

        //calculate weighted average
        BigDecimal bd = BigDecimal.valueOf((score / divisor));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        double finalScore = bd.doubleValue();

        return  finalScore ;
    }

    /**
     * Calculate score according to order of keyword
     * @param keyword
     * @param list
     * @return
     */
    public double calculateScore(String keyword, List<Suggestion> list) {

        double score = 100;
        for(Suggestion suggestion : list) {
        	//logger.info(suggestion.getValue() + "-" + keyword);
            if(suggestion.getValue().equals(keyword))
                break;
            score -= 10;
        }

        return score;
    }

    /**
     * Gets suggestions from Amazon service
     * @param search
     * @return
     */
    public List<Suggestion> getSuggestions(String search) {

    	
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://completion.amazon.com/api/2017/suggestions")
                .queryParam("client-info", "amazon-search-ui")
                .queryParam("suggestion-type", "KEYWORD")
                .queryParam("prefix", search)
                .queryParam("mid", "ATVPDKIKX0DER")
                .queryParam("alias", "aps")
                .queryParam("fresh", "0")
                .queryParam("limit", "10")
                ;

        String url = builder.toUriString();
        //logger.info(url);
        
        RestTemplate restTemplate = new RestTemplate();
    	DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
    	defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);

        restTemplate.setUriTemplateHandler(defaultUriBuilderFactory);
        
        AmazonResponse amazonResponse = restTemplate.getForObject(url, AmazonResponse.class);

        //logger.info(amazonResponse.toString());
        //logger.info(amazonResponse.getSuggestions().toString());
        
        return amazonResponse.getSuggestions();


    }

}
