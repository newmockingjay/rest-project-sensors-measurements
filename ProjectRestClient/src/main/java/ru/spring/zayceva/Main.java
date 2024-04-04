package ru.spring.zayceva;

import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //
        String sensorName = "sensor1";

        //
        int maximum = 30;
        int minimun = -30;
        Random random = new Random();

        RestTemplate restTemplate = new RestTemplate();

        for (int i = 0; i < 10; i ++){
            double randNum = random.nextDouble(maximum - minimun + 1) + minimun;
            boolean randBoolean = random.nextBoolean();
            //
            String url = "http://localhost:8080/measurements/add";

            Map<String, String> jsonSensor = new HashMap<>();
            jsonSensor.put("name", sensorName);

            Map<String, Object> jsonData = new HashMap<>();
            jsonData.put("value", randNum);
            jsonData.put("raining", randBoolean);
            jsonData.put("sensor", jsonSensor);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(jsonData);

            restTemplate.postForObject(url, request, String.class);
        }



    }
}