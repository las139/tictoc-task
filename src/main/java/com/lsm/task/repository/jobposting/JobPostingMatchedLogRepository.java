package com.lsm.task.repository.jobposting;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsm.task.domain.jobposting.JobPostingMatchedLog;

public interface JobPostingMatchedLogRepository extends JpaRepository<JobPostingMatchedLog, Long> {
}
