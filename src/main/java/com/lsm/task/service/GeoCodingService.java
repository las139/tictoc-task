package com.lsm.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.lsm.task.dto.GetAddressResponse;
import com.lsm.task.dto.GetCoordinatesResponse;

@Service
public class GeoCodingService {
    private static final String VERSION = "2.0";
    private static final String EPSG = "epsg:4326";

    private final RestTemplate restTemplate;

    @Value("${vworld.api.url}")
    private String apiUrl;

    @Value("${vworld.api.key}")
    private String apiKey;

    @Autowired
    public GeoCodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GetCoordinatesResponse getCoordinatesByAddress(String address) {
        String requestUrl = apiUrl + "?service=address&request=getcoord&version=" + VERSION + "&crs=" + EPSG +
            "&address=" + address +
            "&refine=true&simple=false&format=json&type=road&key=" + apiKey;

        try {
            return restTemplate.getForObject(requestUrl, GetCoordinatesResponse.class);
        } catch (RestClientException e) {
            throw new RestClientException("지오코딩 변환 API 호출 중 오류가 발생하였습니다.", e);
        }
    }

    public GetAddressResponse getAddressByCoordinates(double latitude, double longitude) {
        String requestUrl = apiUrl + "?service=address&request=getAddress&version=" + VERSION + "&crs=" + EPSG +
            "&point=" + (latitude + ", " + longitude) +
            "&refine=true&simple=false&format=json&type=road&zipcode=true&key=" + apiKey;

        try {
            return restTemplate.getForObject(requestUrl, GetAddressResponse.class);
        } catch (RestClientException e) {
            throw new RestClientException("지오코딩 변환 API 호출 중 오류가 발생하였습니다.", e);
        }
    }
}
