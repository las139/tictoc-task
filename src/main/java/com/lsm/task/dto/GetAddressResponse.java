package com.lsm.task.dto;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class GetAddressResponse {
    private Response response;

    @Getter
    @ToString
    public static class Response {
        private Service service;
        private String status;
        private Input input;
        private List<Result> result;
    }

    @Getter
    @ToString
    public static class Service {
        private String name;
        private String version;
        private String operation;
        private String time;
    }

    @Getter
    @ToString
    public static class Input {
        private Point point;
        private String crs;
        private String type;
    }

    @Getter
    @ToString
    public static class Point {
        private String x;
        private String y;
    }

    @Getter
    @ToString
    public static class Result {
        private String zipcode;
        private String type;
        private String text;
        private Structure structure;
    }

    @Getter
    @ToString
    public static class Structure {
        private String level0;
        private String level1;
        private String level2;
        private String level3;
        private String level4L;
        private String level4LC;
        private String level4A;
        private String level4AC;
        private String level5;
        private String detail;
    }
}
