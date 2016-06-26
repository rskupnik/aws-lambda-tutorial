package com.github.rskupnik.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.javalite.http.Get;
import org.javalite.http.Http;

import java.io.IOException;

public class GetWeatherDataFunction implements RequestHandler<Request, Response> {

    private static final String API_KEY = "081cfe3b3ff076da70e365a1ab1abb37";
    private static final String ENDPOINT = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";

    private String buildEndpoint(String city, String countryCode) {
        return String.format(ENDPOINT, (countryCode != null ? city+","+countryCode : city), API_KEY);
    }

    public Response handleRequest(Request request, Context context) {
        Get weatherResponse = Http.get(buildEndpoint(request.getCity(), request.getCountryCode()));

        ObjectMapper objectMapper = new ObjectMapper();
        Response response = null;
        try {
            response = objectMapper.readValue(weatherResponse.text(), Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
