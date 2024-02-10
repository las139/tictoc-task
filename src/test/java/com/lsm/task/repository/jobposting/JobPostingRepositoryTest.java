package com.lsm.task.repository.jobposting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;

import com.lsm.task.TransactionalTestExecutionListener;
import com.lsm.task.domain.jobposting.JobPosting;
import com.lsm.task.domain.jobposting.JobPostingAddress;
import com.lsm.task.domain.parent.Parent;
import com.lsm.task.repository.parent.ParentRepository;

@SpringBootTest
@TestExecutionListeners(listeners = TransactionalTestExecutionListener.class, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
class JobPostingRepositoryTest {
    @Autowired
    JobPostingRepository jobPostingRepository;
    @Autowired
    ParentRepository parentRepository;
    @Autowired
    JobPostingAddressRepository addressRepository;
    @Autowired
    JobPostingMatchedLogRepository matchedLogRepository;

    JobPosting jobPosting;

    @BeforeEach
    void init() {
        //given
        Parent parent = parentRepository.save(Parent.builder().name("김부모").phoneNumber("010-5344-1112").build());

        jobPosting = JobPosting.builder().parent(parent).details("아이 돌봄. 2시간 희망").build();
    }

    @Test
    @DisplayName("공고 저장 및 값 비교 테스트")
    void save() {
        // when
        final JobPosting actual = jobPostingRepository.save(jobPosting);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getParent()).isEqualTo(jobPosting.getParent()),
            () -> assertThat(actual.getDetails()).isEqualTo(jobPosting.getDetails())
        );
    }

    @Test
    @DisplayName("공고 주소 저장 및 값 비교 테스트")
    void save_address() {
        // given
        final JobPosting actualJobPosting = jobPostingRepository.save(jobPosting);

        // when
        JobPostingAddress address = JobPostingAddress.builder()
                                                     .jobPosting(actualJobPosting)
                                                     .detailAddress("관곡로 39 (신갈동)")
                                                     .district("경기도 용인시")
                                                     .city("기흥구")
                                                     .postalCode("16962")
                                                     .build();

        // when
        final JobPostingAddress actual = addressRepository.save(address);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getJobPosting()).isEqualTo(address.getJobPosting()),
            () -> assertThat(actual.getDetailAddress()).isEqualTo(address.getDetailAddress()),
            () -> assertThat(actual.getDistrict()).isEqualTo(address.getDistrict()),
            () -> assertThat(actual.getCity()).isEqualTo(address.getCity()),
            () -> assertThat(actual.getPostalCode()).isEqualTo(address.getPostalCode())
        );
    }
}
