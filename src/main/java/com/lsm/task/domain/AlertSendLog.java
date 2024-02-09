package com.lsm.task.domain;

import com.lsm.task.domain.jobposting.JobPostingMatchedLog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "alert_send_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlertSendLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "matched_log_id", foreignKey = @ForeignKey(name = "fk_alert_send_log_to_job_posting_matched_log"))
    private JobPostingMatchedLog matchedLog;

    @Column(name = "type", nullable = false)
    private AlertType type;

    @Column(name = "message_content", length = 255, nullable = false)
    private String messageContent;

    @Column(name = "send_result", length = 30, nullable = false)
    private String sendResult;

    @Builder
    public AlertSendLog(Long id, JobPostingMatchedLog matchedLog, AlertType type, String messageContent, String sendResult) {
        this.id = id;
        this.matchedLog = matchedLog;
        this.type = type;
        this.messageContent = messageContent;
        this.sendResult = sendResult;
    }
}
