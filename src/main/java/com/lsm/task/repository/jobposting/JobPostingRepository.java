package com.lsm.task.repository.jobposting;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsm.task.domain.jobposting.JobPosting;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
}
