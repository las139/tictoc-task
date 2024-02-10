package com.lsm.task.repository.teacher;

import static com.lsm.task.domain.teacher.TeacherAddress.ADDRESS_DISTANCE_CONDITION;
import static com.lsm.task.domain.teacher.TeacherSubwayStation.SUBWAY_DISTANCE_CONDITION;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lsm.task.domain.teacher.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    // 특정 위치(위,경도)로부터 선생님 설정한 주소와 일정 거리 내의 선생님을 찾는 네이티브 쿼리
    @Query(value =
        "SELECT t.id, t.name, t.phone_number, t.create_date, t.update_date " +
         "FROM tictoc.teacher t " +
         "WHERE t.id IN (" +
            "SELECT ta.teacher_id " +
            "FROM tictoc.teacher_address ta " +
            "WHERE ST_Distance_Sphere(point(:latitude, :longitude), ta.location) <= " + ADDRESS_DISTANCE_CONDITION +
         ")",
        nativeQuery = true)
    List<Teacher> findTeachersByDistanceFromAddress(@Param("latitude") double latitude, @Param("longitude") double longitude);

    // 특정 위치(위,경도)의 주소와 선생님 선호주소와 일치하는 선생님을 찾는 네이티브 쿼리
    @Query(value =
        "SELECT t.id, t.name, t.phone_number, t.create_date, t.update_date " +
        "FROM tictoc.teacher t " +
        "WHERE t.id IN (" +
            "SELECT tpa.teacher_id " +
            "FROM tictoc.teacher_preferred_area tpa " +
            "WHERE (tpa.area_level = 1 AND tpa.area_name = :district) " +
            "OR (tpa.area_level = 2 AND tpa.area_name = CONCAT(:district, ' ', :city)) " +
        ")",
        nativeQuery = true)
    List<Teacher> findTeachersByPreferredAddress(@Param("district") String district, @Param("city") String city);

    // 특정 위치(위,경도)로부터 선생님 선호지하철역과 일정 거리 내의 선생님을 찾는 네이티브 쿼리
    @Query(value =
        "SELECT t.id, t.name, t.phone_number, t.create_date, t.update_date " +
        "FROM tictoc.teacher t " +
        "WHERE t.id IN (" +
            "SELECT tss.teacher_id " +
            "FROM tictoc.teacher_subway_station tss " +
            "WHERE ST_Distance_Sphere(point(:latitude, :longitude), tss.location) <= " + SUBWAY_DISTANCE_CONDITION +
        ")",
        nativeQuery = true)
    List<Teacher> findTeachersByDistanceFromSubwayStation(@Param("latitude") double latitude, @Param("longitude") double longitude);
}
