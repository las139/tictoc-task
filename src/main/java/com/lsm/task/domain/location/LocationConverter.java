package com.lsm.task.domain.location;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

@Converter
public class LocationConverter implements AttributeConverter<Location, Point> {
    private static final GeometryFactory geometryFactory = new GeometryFactory();

    @Override
    public Point  convertToDatabaseColumn(Location attribute) {
        if (attribute == null) {
            return null;
        }
        // Location 을 데이터베이스 컬럼으로 변환
        return geometryFactory.createPoint(new Coordinate(attribute.getLatitude(), attribute.getLongitude()));
    }

    @Override
    public Location convertToEntityAttribute(Point dbData) {
        if (dbData == null) {
            return null;
        }
        // 데이터베이스 컬럼 값을 Location 으로 변환
        return new Location(dbData.getY(), dbData.getX());
    }
}
