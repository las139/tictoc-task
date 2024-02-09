package com.lsm.task.repository.jobposting;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsm.task.domain.jobposting.JobPostingAddress;

public interface JobPostingAddressRepository extends JpaRepository<JobPostingAddress, Long> {
}
