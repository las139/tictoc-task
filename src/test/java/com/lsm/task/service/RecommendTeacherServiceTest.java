package com.lsm.task.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.lsm.task.domain.jobposting.JobPosting;
import com.lsm.task.domain.location.Location;
import com.lsm.task.domain.parent.Parent;
import com.lsm.task.domain.teacher.Teacher;
import com.lsm.task.domain.teacher.TeacherAddress;
import com.lsm.task.domain.teacher.TeacherPreferredArea;
import com.lsm.task.domain.teacher.TeacherSubwayStation;
import com.lsm.task.dto.RecommendTeacherRequest;
import com.lsm.task.repository.jobposting.JobPostingRepository;
import com.lsm.task.repository.parent.ParentRepository;
import com.lsm.task.repository.teacher.TeacherAddressRepository;
import com.lsm.task.repository.teacher.TeacherPreferredAreaRepository;
import com.lsm.task.repository.teacher.TeacherRepository;
import com.lsm.task.repository.teacher.TeacherSubwayStationRepository;

@SpringBootTest
class RecommendTeacherServiceTest {

    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private JobPostingRepository jobPostingRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private TeacherAddressRepository teacherAddressRepository;
    @Autowired
    private TeacherPreferredAreaRepository teacherPreferredAreaRepository;
    @Autowired
    private TeacherSubwayStationRepository teacherSubwayStationRepository;

    @Autowired
    private RecommendTeacherService recommendTeacherService;

    @Autowired
    private PlatformTransactionManager transactionManager;
    private TransactionStatus transactionStatus;

    private Parent parent;
    private JobPosting jobPosting;
    private Teacher teacherGildong;
    private Teacher teacherSeojun;
    private Teacher teacherJayce;

    @BeforeEach
    public void setUp() {
        transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());

        parent = parentRepository.save(Parent.builder().name("김부모").phoneNumber("010-5344-1112").build());
        jobPosting = jobPostingRepository.save(JobPosting.builder().parent(parent).details("아이 돌봄. 2시간 희망").build());

        teacherGildong = teacherRepository.save(Teacher.builder().name("홍길동").phoneNumber("010-5555-2222").build());
        teacherSeojun = teacherRepository.save(Teacher.builder().name("박서준").phoneNumber("010-3333-4444").build());
        teacherJayce = teacherRepository.save(Teacher.builder().name("제이스").phoneNumber("010-6763-8888").build());

        // 교사 홍길동 주소
        String address = "경기 성남시 분당구 대왕판교로606번길 10 알파돔시티판교알파리움1단지아파트";
        Location locationSeongnam = new Location(127.109016805, 37.395860765);
        TeacherAddress teacherAddress = TeacherAddress.builder().teacher(teacherGildong).address(address).location(locationSeongnam).build();

        // 교사 박서준 선호 주소
        Location locationYongin = new Location(127.096134812, 37.500518694);
        TeacherPreferredArea teacherPreferredArea = TeacherPreferredArea.builder()
                                                                        .teacher(teacherSeojun)
                                                                        .areaLevel(2)
                                                                        .areaName("경기도 용인시 기흥구")
                                                                        .location(locationYongin)
                                                                        .build();

        // 교사 제이스 지하철역
        String stationName = "수서역";
        Location locationSooseo = new Location(127.101851061, 37.487386331);
        TeacherSubwayStation teacherSubwayStation = TeacherSubwayStation.builder()
                                                                        .teacher(teacherJayce)
                                                                        .stationName(stationName)
                                                                        .location(locationSooseo)
                                                                        .build();

        // 교사 주소, 선호 주소, 지하철역 저장
        teacherAddressRepository.save(teacherAddress);
        teacherPreferredAreaRepository.save(teacherPreferredArea);
        teacherSubwayStationRepository.save(teacherSubwayStation);
    }

    @Test
    @DisplayName("공고 주소와 교사 설정 주소와의 거리가 5km 이내인 데이터가 있을 경우  해당 교사 정보를 리턴한다.")
    void findTeachersByAddress_success() {
        // given
        String address = "경기 성남시 분당구 성남대로 449 로얄팰리스아파트";
        RecommendTeacherRequest request = new RecommendTeacherRequest(jobPosting.getId(), 127.111323255, 37.375666818, address, "13596");

        // when
        List<Teacher> result = recommendTeacherService.findTeachersByAddress(request);

        // then
        boolean expected = result.stream().anyMatch(teacher -> teacherGildong.getName().equals(teacher.getName()) &&
                                                                teacherGildong.getPhoneNumber().equals(teacher.getPhoneNumber()));

        // then
        assertAll(
            () -> assertThat(result).isNotEmpty(),
            () -> assertThat(expected).isTrue()
        );
    }

    @Test
    @DisplayName("공고 주소와 교사 설정 주소와의 거리가 5km 이내인 데이터가 없을 경우 조회되지 않는다.")
    void findTeachersByAddress_fail() {
        // given
        String address = "경기도 여주시 도예로 83-60 (현암동,e편한세상 여주)";
        RecommendTeacherRequest request = new RecommendTeacherRequest(jobPosting.getId(), 127.638981710, 37.313654451, address, "12637");

        // when
        List<Teacher> result = recommendTeacherService.findTeachersByAddress(request);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("공고 주소와 교사 설정 선호주소와 일치하는 데이터가 있을 경우 해당 교사 정보를 리턴한다.")
    void findTeachersByPreferredAddress_success() {
        // given
        String address = "경기도 용인시 기흥구 기흥역로 63 (구갈동,힐스테이트 기흥)";
        RecommendTeacherRequest request = new RecommendTeacherRequest(jobPosting.getId(), 127.118778733, 37.274842927, address, "17065");

        // when
        List<Teacher> result = recommendTeacherService.findTeachersByPreferredAddress(request);

        // then
        boolean expected = result.stream().anyMatch(teacher -> teacherSeojun.getName().equals(teacher.getName()) &&
                                                                teacherSeojun.getPhoneNumber().equals(teacher.getPhoneNumber()));
        // then
        assertAll(
            () -> assertThat(result).isNotEmpty(),
            () -> assertThat(expected).isTrue()
        );
    }

    @Test
    @DisplayName("공고 주소와 교사 설정 선호주소와 일치하는 데이터가 없을 경우 조회되지 않는다.")
    void findTeachersByPreferredAddress_fail() {
        // given
        String address = "경기도 여주시 도예로 83-60 (현암동,e편한세상 여주)";
        RecommendTeacherRequest request = new RecommendTeacherRequest(jobPosting.getId(), 127.638981710, 37.313654451, address, "12637");

        // when
        List<Teacher> result = recommendTeacherService.findTeachersByPreferredAddress(request);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("공고 주소와 교사 설정 지하철역과의 거리가 5km 이내인 데이터가 있을 경우 해당 교사 정보를 리턴한다.")
    void findTeachersByStation_success() {
        // given
        String address = "서울특별시 강남구 밤고개로 165 (자곡동,LH 수서 1단지 아파트)";
        RecommendTeacherRequest request = new RecommendTeacherRequest(jobPosting.getId(), 127.106144070, 37.479394341, address, "06369");

        // when
        List<Teacher> result = recommendTeacherService.findTeachersByStation(request);

        // then
        boolean expected = result.stream().anyMatch(teacher -> teacherJayce.getName().equals(teacher.getName()) &&
                                                                teacherJayce.getPhoneNumber().equals(teacher.getPhoneNumber()));

        // then
        assertAll(
            () -> assertThat(result).isNotEmpty(),
            () -> assertThat(expected).isTrue()
        );
    }

    @Test
    @DisplayName("공고 주소와 교사 설정 지하철역과의 거리가 5km 이내인 데이터가 없을 경우 조회되지 않는다.")
    void findTeachersByStation_fail() {
        // given
        String address = "경기도 여주시 도예로 83-60 (현암동,e편한세상 여주)";
        RecommendTeacherRequest request = new RecommendTeacherRequest(jobPosting.getId(), 127.638981710, 37.313654451, address, "12637");

        // when
        List<Teacher> result = recommendTeacherService.findTeachersByStation(request);

        // then
        assertThat(result).isEmpty();
    }

    // DB 트랜잭션 롤백
    @AfterEach
    void afterEach() {
        transactionManager.rollback(transactionStatus);
    }
}
