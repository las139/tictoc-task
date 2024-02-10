package com.lsm.task.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.lsm.task.dto.GetAddressResponse;
import com.lsm.task.dto.GetCoordinatesResponse;

@SpringBootTest
class GeoCodingServiceTest {
    @Autowired
    GeoCodingService geoCodingService;

    @Test
    @DisplayName("정상적인 주소를 입력 시 위,경도로 변환 성공한다.")
    void getCoordinatesByAddress_success() {
        // given
        String address = "경기도 용인시 기흥구 관곡로 39 (신갈동)";

        // when
        GetCoordinatesResponse result = geoCodingService.getCoordinatesByAddress(address);

        // then
        assertAll(
            () -> assertThat(result).isNotNull(),
            () -> assertThat(result.getResponse()).isNotNull(),
            () -> assertThat(result.getResponse().getResult().getPoint().getX()).isEqualTo("127.110288839"),
            () -> assertThat(result.getResponse().getResult().getPoint().getY()).isEqualTo("37.283281362")
        );
    }

    @Test
    @DisplayName("비정상적인 주소를 입력 시 위,경도로 변환 실패한다.")
    void getCoordinatesByAddress_fail() {
        // given
        String address = "dsadasczxqeqweqewdsadasd";

        // then
        assertThatThrownBy(() -> {
            // when
            geoCodingService.getCoordinatesByAddress(address);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("정상적인 위,경도를 입력 시 주소로 변환 성공한다.")
    void getAddressByCoordinates_success() {
        double latitude = 127.110288839;
        double longitude = 37.283281362;
        GetAddressResponse result = geoCodingService.getAddressByCoordinates(latitude, longitude);

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
    @Test
    @DisplayName("정상적인 위,경도를 입력 시 주소로 변환 실패한다.")
    void getAddressByCoordinates_fail() {
        // given
        double latitude = 0;
        double longitude = 9999;

        // then
        assertThatThrownBy(() -> {
            // when
            geoCodingService.getAddressByCoordinates(latitude, longitude);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
