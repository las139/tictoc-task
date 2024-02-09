-- JobPosting Table
CREATE TABLE job_posting (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    parent_id BIGINT NOT NULL,
    address_id BIGINT NOT NULL,
    details TEXT NOT NULL,
    create_date DATETIME NOT NULL,
    update_date DATETIME,
    FOREIGN KEY (parent_id) REFERENCES parent(id),
    FOREIGN KEY (address_id) REFERENCES job_posting_address(id)
);

-- JobPostingAddress Table
CREATE TABLE job_posting_address (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    detail_address VARCHAR(255) NOT NULL,
    district VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    create_date DATETIME NOT NULL,
    update_date DATETIME
);

-- Parent Table
CREATE TABLE parent (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    create_date DATETIME NOT NULL,
    update_date DATETIME
);

-- Teacher Table
CREATE TABLE teacher (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    create_date DATETIME NOT NULL,
    update_date DATETIME
);

-- TeacherAddress Table
CREATE TABLE teacher_addresses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    teacher_id BIGINT NOT NULL,
    address VARCHAR(255) NOT NULL,
    location POINT NOT NULL,
    create_date DATETIME NOT NULL,
    update_date DATETIME,
    SPATIAL INDEX(location), -- 위치 데이터에 대한 공간 인덱스 생성
    FOREIGN KEY (teacher_id) REFERENCES teacher(id)
);

-- TeacherSubwayStation Table
CREATE TABLE teacher_subway_station (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    teacher_id BIGINT NOT NULL,
    station_name VARCHAR(255) NOT NULL,
    location POINT NOT NULL,
    create_date DATETIME NOT NULL,
    update_date DATETIME,
    SPATIAL INDEX(location), -- 위치 데이터에 대한 공간 인덱스 생성
    FOREIGN KEY (teacher_id) REFERENCES teacher(id)
);

-- TeacherPreferredArea Table
CREATE TABLE teacher_preferred_area (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    teacher_id BIGINT NOT NULL,
    area_level VARCHAR(255) NOT NULL,  -- 1. 도 / 2.시/구, 3.읍/면/동
    area_name VARCHAR(255) NOT NULL,
    location POINT NOT NULL,
    create_date DATETIME NOT NULL,
    update_date DATETIME,
    SPATIAL INDEX(location), -- 위치 데이터에 대한 공간 인덱스 생성
    FOREIGN KEY (teacher_id) REFERENCES teacher(id)
);

-- JobPostingMatchedLog Table
CREATE TABLE `job_posting_matched_log` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    teacher_id BIGINT NOT NULL,
	job_posting_id BIGINT NOT NULL,
    create_date DATETIME NOT NULL,
    update_date DATETIME,
    FOREIGN KEY (teacher_id) REFERENCES teacher(id),
    FOREIGN KEY (job_posting_id) REFERENCES job_posting(id)
);

-- AlertSendLog Table
CREATE TABLE `alert_send_log` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    matched_log_id BIGINT NOT NULL,
	type ENUM NULL,
	message_content VARCHAR(255) NULL,
	send_result	VARCHAR(30)	NULL,
    create_date DATETIME NOT NULL,
    update_date DATETIME,
    FOREIGN KEY (matched_log_id) REFERENCES job_posting_matched_log(id)
);