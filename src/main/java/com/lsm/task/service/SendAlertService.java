package com.lsm.task.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lsm.task.domain.alert.AlertSendLog;
import com.lsm.task.domain.alert.AlertType;
import com.lsm.task.domain.jobposting.JobPostingMatchedLog;
import com.lsm.task.domain.teacher.Teacher;
import com.lsm.task.repository.alert.AlertSendLogRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SendAlertService {
    private static final String SEND_RESULT_SUCCESS = "SUCCESS";
    private static final String SEND_RESULT_FAIL = "FAIL";

    private final AlertSendLogRepository alertSendLogRepository;

    @Transactional
    public boolean sendAlert(final Teacher teacher, final JobPostingMatchedLog matchedLog) {
        // fixme: 실제 서비스한다고 가정 시 이 부분에 SMS, 이메일, 푸쉬알림 등 서비스 전송 기능 추가
        boolean sendResult = false;

        // 알림 전송
        this.saveSendLog(matchedLog, sendResult);

        return true;
    }

    @Transactional
    public void saveSendLog(final JobPostingMatchedLog matchedLog, final boolean sendResult) {
        String messageContent = "[공고 추천 알림]\n새로운 공고가 올라왔습니다. 마이페이지에서 확인해주세요.";
        AlertSendLog alertSendLog = AlertSendLog.builder()
                                                .matchedLog(matchedLog)
                                                .sendResult(sendResult ? SEND_RESULT_SUCCESS : SEND_RESULT_FAIL)
                                                .type(AlertType.APP_PUSH)
                                                .messageContent(messageContent)
                                                .build();

        alertSendLogRepository.save(alertSendLog);
    }
}
