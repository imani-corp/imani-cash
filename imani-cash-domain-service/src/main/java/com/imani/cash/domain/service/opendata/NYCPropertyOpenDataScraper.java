package com.imani.cash.domain.service.opendata;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class NYCPropertyOpenDataScraper implements IPropertyOpenDataScraper {



    public static final String SPRING_BEAN = "com.imani.cash.domain.service.opendata.NYCPropertyOpenDataScraper";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(NYCPropertyOpenDataScraper.class);


    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        System.out.println("Posting for Plaid account balance object....");

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String jsonRequest = "{\n" +
                    "  \"client_id\": \"5cf09bb96590ed001352c26f\",\n" +
                    "  \"secret\": \"e0cd2b5363f8864089a2cf23d2e1de\",\n" +
                    "  \"access_token\": \"access-sandbox-41e7c7bf-4eee-41cb-bfea-b4c2ec227a7b\",\n" +
                    "  \"options\": {\n" +
                    "    \"account_ids\": [\n" +
                    "      \"m1ELdBMPLAHBGR7eDGaktknLRNaN8VHLe663j\"\n" +
                    "    ]\n" +
                    "  }\n" +
                    "}";
            HttpEntity<String> request = new HttpEntity<>(jsonRequest, headers);


            String balanceInformation = restTemplate.postForObject("https://sandbox.plaid.com/accounts/balance/get", request, String.class);

            System.out.println("Object = " + balanceInformation);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

//        System.out.println("Fetching NYC Open Data set....");
//        ResponseEntity<NYCProperty[]> results = restTemplate.getForEntity("https://data.cityofnewyork.us/resource/2vyb-t2nz.json", NYCProperty[].class);
//
//        NYCProperty[] nycProperties = results.getBody();
//
////        System.out.println("nycProperties.length = " + nycProperties.length);
//
//        for(NYCProperty nycProperty : nycProperties) {
//            System.out.println("nycProperty = " + nycProperty);
//        }

    }
}
