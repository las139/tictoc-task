package com.lsm.task.repository.parent;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.lsm.task.domain.parent.Parent;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ParentRepositoryTest {
    @Autowired
    ParentRepository parentRepository;

    @Test
    @DisplayName("부모 저장 및 값 비교 테스트")
    void save() {
        // when
        final Parent expected = Parent.builder().name("김부모").phoneNumber("010-5344-1112").build();
        final Parent actual = parentRepository.save(expected);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPhoneNumber()).isEqualTo(expected.getPhoneNumber())
        );
    }
}
