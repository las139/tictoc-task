package com.lsm.task.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.lsm.task.domain.jobposting.JobPosting;
import com.lsm.task.domain.jobposting.JobPostingMatchedLog;
import com.lsm.task.domain.teacher.Teacher;
import com.lsm.task.dto.GetAddressResponse;
import com.lsm.task.dto.RecommendTeacherRequest;
import com.lsm.task.dto.RecommendTeacherResponse;
import com.lsm.task.repository.jobposting.JobPostingMatchedLogRepository;
import com.lsm.task.repository.jobposting.JobPostingRepository;
import com.lsm.task.repository.teacher.TeacherRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecommendTeacherService {
    private final GeoCodingService geoCodingService;
    private final SendAlertService sendAlertService;
    private final TeacherRepository teacherRepository;
    private final JobPostingRepository jobPostingRepository;
    private final JobPostingMatchedLogRepository jobPostingMatchedLogRepository;

    @Transactional
    public List<RecommendTeacherResponse> findMatchingTeachers(final RecommendTeacherRequest request) {
        List<Teacher> teachersByAddress = findTeachersByAddress(request);
        List<Teacher> teachersByPreferredAddress = findTeachersByPreferredAddress(request);
        List<Teacher> teachersByStation = findTeachersByStation(request);

        List<Teacher> response = Stream.of(teachersByAddress.stream(), teachersByPreferredAddress.stream(), teachersByStation.stream())
                                                         .flatMap(stream -> stream)
                                                         .map(t -> Teacher.builder().id(t.getId()).name(t.getName()).phoneNumber(t.getPhoneNumber()).build())
                                                         .distinct()
                                                         .toList();

        for (Teacher t : response) {
            Optional<JobPosting> jobPosting = jobPostingRepository.findById(request.getJobPostingId());
            if (jobPosting.isPresent()) {
                // 매칭공고 기록 db 저장
                JobPostingMatchedLog matchedLog = jobPostingMatchedLogRepository.save(JobPostingMatchedLog.builder().jobPosting(jobPosting.get()).teacher(t).build());
                jobPosting.get().addMatchedLog(matchedLog);

                // 알림 전송 후 전송 기록 db 저장
                sendAlertService.sendAlert(t, matchedLog);
            }
        }

        return response.stream().map(RecommendTeacherResponse::of).toList();
    }

    @Transactional(readOnly = true)
    public List<Teacher> findTeachersByAddress(final RecommendTeacherRequest request) {
        // 공고 위,경도와 교사가 설정한 주소와 거리 비교 (5km 이내만)
        return teacherRepository.findTeachersByDistanceFromAddress(request.getLatitude(), request.getLongitude());
    }

    @Transactional(readOnly = true)
    public List<Teacher> findTeachersByPreferredAddress(final RecommendTeacherRequest request) {
        // 공고 위,경도와 교사가 설정한 선호 주소와 비교 (같은 도/시)
        GetAddressResponse address = geoCodingService.getAddressByCoordinates(request.getLatitude(), request.getLongitude());
        if(address == null || address.getResponse() == null || address.getResponse().getResult().isEmpty()) {
            throw new IllegalArgumentException("해당 주소는 존재하지 않습니다.");
        }

        String district = address.getResponse().getResult().get(0).getStructure().getLevel1();  // 도,시
        String city = address.getResponse().getResult().get(0).getStructure().getLevel2();  // 시,군,구
        return teacherRepository.findTeachersByPreferredAddress(district, city);
    }

    @Transactional(readOnly = true)
    public List<Teacher> findTeachersByStation(final RecommendTeacherRequest request) {
        // 공고 위,경도와 교사가 설정한 지하철역과 거리 비교 (1km 이내만)
        return teacherRepository.findTeachersByDistanceFromSubwayStation(request.getLatitude(), request.getLongitude());
    }
}
