package com.lsm.task.domain.jobposting;

import com.lsm.task.domain.alert.AlertSendLog;
import com.lsm.task.domain.BaseEntity;
import com.lsm.task.domain.teacher.Teacher;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "job_posting_matched_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobPostingMatchedLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_posting_id", foreignKey = @ForeignKey(name = "fk_job_posting_matched_log_to_job_posting"))
    private JobPosting jobPosting;

    @ManyToOne
    @JoinColumn(name = "teacher_id", foreignKey = @ForeignKey(name = "fk_job_posting_matched_log_to_teacher"))
    private Teacher teacher;

    @OneToOne(mappedBy = "matchedLog")
    private AlertSendLog alertSendLog;

    @Builder
    public JobPostingMatchedLog(Long id, JobPosting jobPosting, Teacher teacher) {
        this.id = id;
        this.jobPosting = jobPosting;
        this.teacher = teacher;
    }
}
