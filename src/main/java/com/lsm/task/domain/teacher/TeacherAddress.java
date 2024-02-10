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
@Table(name = "teacher_address")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherAddress extends BaseEntity {
    public static final int ADDRESS_DISTANCE_CONDITION = 5000;   // 거리 조건 5km

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id", foreignKey = @ForeignKey(name = "fk_teacher_address_to_teacher"))
    private Teacher teacher;

    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @Convert(converter = LocationConverter.class)
    @Column(name = "location", columnDefinition = "Point", nullable = false)
    private Location location;

    @Builder
    public TeacherAddress(Long id, Teacher teacher, String address, Location location) {
        this.id = id;
        this.teacher = teacher;
        this.address = address;
        this.location = location;
    }
}
