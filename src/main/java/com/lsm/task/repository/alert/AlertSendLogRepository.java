package com.lsm.task.repository.alert;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsm.task.domain.alert.AlertSendLog;

public interface AlertSendLogRepository extends JpaRepository<AlertSendLog, Long> {
}
