package com.lsm.task.domain.teacher;

import java.util.Objects;
import java.util.Set;

import com.lsm.task.domain.BaseEntity;
import com.lsm.task.domain.jobposting.JobPostingMatchedLog;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "teacher")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Teacher extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<TeacherAddress> address;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<TeacherPreferredArea> preferredAreas;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Set<TeacherSubwayStation> subwayStations;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private Set<JobPostingMatchedLog> matchedLogs;

    @Builder
    public Teacher(Long id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public void addMatchedLogs(JobPostingMatchedLog matchedLog) {
        this.matchedLogs.add(matchedLog);
        matchedLog.setTeacher(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher that = (Teacher) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, name);
    }
}
