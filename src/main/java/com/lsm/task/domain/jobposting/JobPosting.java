package com.lsm.task.domain.jobposting;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

import com.lsm.task.domain.BaseEntity;
import com.lsm.task.domain.parent.Parent;

@Getter
@Entity
@Table(name = "job_posting")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobPosting extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "fk_job_posting_to_parent"))
    private Parent parent;

    @OneToMany(mappedBy = "jobPosting", fetch = FetchType.LAZY)
    private Set<JobPostingMatchedLog> matchedLogs;

    @Column(name = "details", length = 255)
    private String details;

    @Builder
    public JobPosting(Long id, Parent parent, String details) {
        this.id = id;
        this.parent = parent;
        this.details = details;
    }
}
