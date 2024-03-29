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
@Table(name = "teacher_subway_station")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherSubwayStation extends BaseEntity {
    public static final int SUBWAY_DISTANCE_CONDITION = 1000;   // 거리 조건 1km

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", foreignKey = @ForeignKey(name = "fk_teacher_subway_station_to_teacher"))
    private Teacher teacher;

    @Column(name = "station_name", length = 255, nullable = false)
    private String stationName;

    @Convert(converter = LocationConverter.class)
    @Column(name = "location", columnDefinition = "Point", nullable = false)
    private Location location;

    @Builder
    public TeacherSubwayStation(Long id, Teacher teacher, String stationName, Location location) {
        this.id = id;
        this.teacher = teacher;
        this.stationName = stationName;
        this.location = location;
    }
}
