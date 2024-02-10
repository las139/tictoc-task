package com.lsm.task.repository.teacher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;

import com.lsm.task.TransactionalTestExecutionListener;
import com.lsm.task.domain.location.Location;
import com.lsm.task.domain.teacher.Teacher;
import com.lsm.task.domain.teacher.TeacherAddress;
import com.lsm.task.domain.teacher.TeacherPreferredArea;
import com.lsm.task.domain.teacher.TeacherSubwayStation;

@SpringBootTest
@TestExecutionListeners(listeners = TransactionalTestExecutionListener.class, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class TeacherRepositoryTest {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    TeacherAddressRepository addressRepository;
    @Autowired
    TeacherPreferredAreaRepository preferredAreaRepository;
    @Autowired
    TeacherSubwayStationRepository subwayStationRepository;

    Teacher teacher;

    @BeforeEach
    void init() {
        //given
        teacher = Teacher.builder().name("홍길동").phoneNumber("010-2222-1111").build();
    }

    @Test
    @DisplayName("교사 저장 및 값 비교 테스트")
    void save() {
        // when
        final Teacher actual = teacherRepository.save(teacher);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(teacher.getName()),
            () -> assertThat(actual.getPhoneNumber()).isEqualTo(teacher.getPhoneNumber())
        );
    }

    @Test
    @DisplayName("교사 주소 저장 및 값 비교 테스트")
    void save_address() {
        // given
        final Teacher actualTeacher = teacherRepository.save(teacher);
        String address = "경기 성남시 분당구 대왕판교로606번길 10 알파돔시티판교알파리움1단지아파트";
        Location location = new Location(127.109016805, 37.395860765);
        TeacherAddress teacherAddress = TeacherAddress.builder().teacher(actualTeacher).address(address).location(location).build();

        // when
        final TeacherAddress actual = addressRepository.save(teacherAddress);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getTeacher()).isEqualTo(teacherAddress.getTeacher()),
            () -> assertThat(actual.getAddress()).isEqualTo(teacherAddress.getAddress()),
            () -> assertThat(actual.getLocation()).isEqualTo(teacherAddress.getLocation())
        );
    }

    @Test
    @DisplayName("교사 선호 주소 저장 및 값 비교 테스트")
    void save_preferred_address() {
        // given
        final Teacher actualTeacher = teacherRepository.save(teacher);
        Location locationSeongnam = new Location(127.141380398, 37.444300836);
        Location locationYongin = new Location(127.096134812, 37.500518694);
        TeacherPreferredArea preferredAreaSeongnam = TeacherPreferredArea.builder()
                                                                         .teacher(actualTeacher)
                                                                         .areaLevel(1)
                                                                         .areaName("경기도 성남시")
                                                                         .location(locationSeongnam)
                                                                         .build();
        TeacherPreferredArea preferredAreaYongin = TeacherPreferredArea.builder()
                                                                       .teacher(actualTeacher)
                                                                       .areaLevel(1)
                                                                       .areaName("경기도 용인시")
                                                                       .location(locationYongin)
                                                                       .build();

        // when
        final TeacherPreferredArea actualSeongnam = preferredAreaRepository.save(preferredAreaSeongnam);
        final TeacherPreferredArea actualYongin = preferredAreaRepository.save(preferredAreaYongin);

        // then
        assertAll(
            () -> assertThat(actualSeongnam.getId()).isNotNull(),
            () -> assertThat(actualSeongnam.getTeacher()).isEqualTo(preferredAreaSeongnam.getTeacher()),
            () -> assertThat(actualSeongnam.getAreaLevel()).isEqualTo(preferredAreaSeongnam.getAreaLevel()),
            () -> assertThat(actualSeongnam.getAreaName()).isEqualTo(preferredAreaSeongnam.getAreaName()),
            () -> assertThat(actualSeongnam.getLocation()).isEqualTo(preferredAreaSeongnam.getLocation()),
            () -> assertThat(actualYongin.getId()).isNotNull(),
            () -> assertThat(actualYongin.getTeacher()).isEqualTo(preferredAreaYongin.getTeacher()),
            () -> assertThat(actualYongin.getAreaLevel()).isEqualTo(preferredAreaYongin.getAreaLevel()),
            () -> assertThat(actualYongin.getAreaName()).isEqualTo(preferredAreaYongin.getAreaName()),
            () -> assertThat(actualYongin.getLocation()).isEqualTo(preferredAreaYongin.getLocation())
        );
    }

    @Test
    @DisplayName("교사 지하철역 저장 및 값 비교 테스트")
    void save_subway_station() {
        // given
        final Teacher actualTeacher = teacherRepository.save(teacher);
        String stationName = "수서역";
        Location location = new Location(127.101851061, 37.487386331);
        TeacherSubwayStation subwayStation = TeacherSubwayStation.builder().teacher(actualTeacher).stationName(stationName).location(location).build();

        // when
        final TeacherSubwayStation actual = subwayStationRepository.save(subwayStation);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getTeacher()).isEqualTo(subwayStation.getTeacher()),
            () -> assertThat(actual.getStationName()).isEqualTo(subwayStation.getStationName()),
            () -> assertThat(actual.getLocation()).isEqualTo(subwayStation.getLocation())
        );
    }
}
