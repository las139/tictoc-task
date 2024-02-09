package com.lsm.task.domain.teacher;

import com.lsm.task.domain.BaseEntity;
import com.lsm.task.domain.location.Location;
import com.lsm.task.domain.location.LocationConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
@Table(name = "teacher_preferred_area")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherPreferredArea extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", foreignKey = @ForeignKey(name = "fk_teacher_preferred_areas_to_teacher"))
    private Teacher teacher;

    @Column(name = "area_level", nullable = false)
    private int areaLevel;

    @Column(name = "area_name", length = 255, nullable = false)
    private String areaName;

    @Convert(converter = LocationConverter.class)
    @Column(name = "location", columnDefinition = "Point", nullable = false)
    private Location location;

    @Builder
    public TeacherPreferredArea(Long id, Teacher teacher, int areaLevel, String areaName, Location location) {
        this.id = id;
        this.teacher = teacher;
        this.areaLevel = areaLevel;
        this.areaName = areaName;
        this.location = location;
    }
}
