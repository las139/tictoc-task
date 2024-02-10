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
    private static final String STATUS_NOT_FOUND = "NOT_FOUND";

    private final RestTemplate restTemplate;

    @Value("${vworld.api.url}")
    private String apiUrl;

    @Value("${vworld.api.key}")
    private String apiKey;

    @Autowired
    public GeoCodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 주소를 위,경도로 변환한다.
    public GetCoordinatesResponse getCoordinatesByAddress(final String address) {
        String requestUrl = apiUrl + "?service=address&request=getcoord&version=" + VERSION + "&crs=" + EPSG +
            "&address=" + address +
            "&refine=true&simple=false&format=json&type=road&key=" + apiKey;

        try {
            GetCoordinatesResponse response = restTemplate.getForObject(requestUrl, GetCoordinatesResponse.class);
            if (response == null || response.getResponse().getStatus().equals(STATUS_NOT_FOUND)) {
                throw new IllegalArgumentException("위,경도에 해당하는 주소를 찾지 못했습니다.");
            }
            return response;
        } catch (RestClientException e) {
            throw new RestClientException("지오코딩 변환 API 호출 중 오류가 발생하였습니다.", e);
        }
    }

    // 위,경도를 주소로 변환한다.
    public GetAddressResponse getAddressByCoordinates(final double latitude, final double longitude) {
        String requestUrl = apiUrl + "?service=address&request=getAddress&version=" + VERSION + "&crs=" + EPSG +
            "&point=" + (latitude + ", " + longitude) +
            "&refine=true&simple=false&format=json&type=road&zipcode=true&key=" + apiKey;

        try {
            GetAddressResponse response = restTemplate.getForObject(requestUrl, GetAddressResponse.class);
            if (response == null || response.getResponse().getStatus().equals(STATUS_NOT_FOUND)) {
                throw new IllegalArgumentException("주소에 해당하는 위,경도를 찾지 못했습니다.");
            }
            return response;
        } catch (RestClientException e) {
            throw new RestClientException("지오코딩 변환 API 호출 중 오류가 발생하였습니다.", e);
        }
    }
}
