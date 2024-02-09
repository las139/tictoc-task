package com.lsm.task.domain.jobposting;

import com.lsm.task.domain.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "job_posting_address")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobPostingAddress extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_posting_id", foreignKey = @ForeignKey(name = "fk_job_posting_address_to_job_posting"))
    private JobPosting jobPosting;

    @Column(name = "detail_address", length = 255, nullable = false)
    private String detailAddress;

    @Column(name = "district", length = 255, nullable = false)
    private String district;

    @Column(name = "city", length = 255, nullable = false)
    private String city;

    @Column(name = "postal_code", length = 20, nullable = false)
    private String postalCode;

    @Builder
    public JobPostingAddress(Long id, JobPosting jobPosting, String detailAddress, String district, String city, String postalCode) {
        this.id = id;
        this.jobPosting = jobPosting;
        this.detailAddress = detailAddress;
        this.district = district;
        this.city = city;
        this.postalCode = postalCode;
    }
}
