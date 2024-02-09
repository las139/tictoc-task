package com.lsm.task.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lsm.task.domain.jobposting.JobPosting;
import com.lsm.task.dto.GetAddressResponse;
import com.lsm.task.dto.GetCoordinatesResponse;

@SpringBootTest
class GeoCodingServiceTest {
    @Autowired
    GeoCodingService geoCodingService;

    JobPosting jobPosting;

    @Test
    @DisplayName("주소를 위,경도로 변환 테스트")
    void getCoordinatesByAddress() {
        String address = "경기도 용인시 기흥구 관곡로 39 (신갈동)";
        GetCoordinatesResponse result = geoCodingService.getCoordinatesByAddress(address);

        System.out.println("#### result:"+result);

        // then
        assertAll(
            () -> assertThat(result).isNotNull(),
            () -> assertThat(result.getResponse()).isNotNull(),
            () -> assertThat(result.getResponse().getResult().getPoint().getX()).isEqualTo("127.110288839"),
            () -> assertThat(result.getResponse().getResult().getPoint().getY()).isEqualTo("37.283281362")
        );
    }

    @Test
    @DisplayName("위,경도를 주소로 변환 테스트")
    void getAddressByCoordinates() {
        double latitude = 127.110288839;
        double longitude = 37.283281362;
        GetAddressResponse result = geoCodingService.getAddressByCoordinates(latitude, longitude);

        System.out.println("#### result:"+result);

        // then
        assertAll(
            () -> assertThat(result).isNotNull(),
            () -> assertThat(result.getResponse()).isNotNull(),
            () -> assertThat(result.getResponse().getResult()).isNotEmpty(),
            () -> assertThat(result.getResponse().getResult().get(0).getZipcode()).isEqualTo("16962"),
            () -> assertThat(result.getResponse().getResult().get(0).getText()).isEqualTo("경기도 용인시 기흥구 관곡로 39 (신갈동,용인시 첨단교통정보센터)"),
            () -> assertThat(result.getResponse().getResult().get(0).getStructure().getLevel0()).isEqualTo("대한민국"),
            () -> assertThat(result.getResponse().getResult().get(0).getStructure().getLevel1()).isEqualTo("경기도"),
            () -> assertThat(result.getResponse().getResult().get(0).getStructure().getLevel2()).isEqualTo("용인시 기흥구"),
            () -> assertThat(result.getResponse().getResult().get(0).getStructure().getLevel3()).isEqualTo("신갈동"),
            () -> assertThat(result.getResponse().getResult().get(0).getStructure().getLevel4L()).isEqualTo("관곡로"),
            () -> assertThat(result.getResponse().getResult().get(0).getStructure().getLevel5()).isEqualTo("39"),
            () -> assertThat(result.getResponse().getResult().get(0).getStructure().getDetail()).isEqualTo("용인시 첨단교통정보센터")
        );
    }
}
