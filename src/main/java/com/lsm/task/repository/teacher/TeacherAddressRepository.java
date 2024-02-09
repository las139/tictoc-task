package com.lsm.task.repository.teacher;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsm.task.domain.teacher.TeacherAddress;

public interface TeacherAddressRepository extends JpaRepository<TeacherAddress, Long> {
}
