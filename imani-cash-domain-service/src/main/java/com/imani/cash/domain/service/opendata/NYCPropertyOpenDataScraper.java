package com.imani.cash.domain.service.opendata;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NYCPropertyOpenDataScraper implements IPropertyOpenDataScraper {



    public static final String SPRING_BEAN = "com.imani.cash.domain.service.opendata.NYCPropertyOpenDataScraper";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(NYCPropertyOpenDataScraper.class);


    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Fetching NYC Open Data set....");
        ResponseEntity<NYCProperty[]> results = restTemplate.getForEntity("https://data.cityofnewyork.us/resource/2vyb-t2nz.json", NYCProperty[].class);

        NYCProperty[] nycProperties = results.getBody();

//        System.out.println("nycProperties.length = " + nycProperties.length);

        for(NYCProperty nycProperty : nycProperties) {
            System.out.println("nycProperty = " + nycProperty);
        }

    }
}
