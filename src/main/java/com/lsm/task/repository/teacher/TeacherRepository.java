package com.lsm.task.repository.teacher;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lsm.task.domain.teacher.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    //List<Teacher> findByQuestionIdAndDeletedFalse(Long questionId);

    //Optional<Teacher> findByIdAndDeletedFalse(Long id);

    // 특정 위치에서 일정 거리 내의 선생님을 찾는 네이티브 쿼리
    @Query(value = "SELECT * FROM Teacher WHERE ST_Distance_Sphere(location, POINT(:longitude, :latitude)) <= :distance", nativeQuery = true)
    List<Teacher> findTeachersNearby(@Param("longitude") double longitude, @Param("latitude") double latitude, @Param("distance") double distance);
}