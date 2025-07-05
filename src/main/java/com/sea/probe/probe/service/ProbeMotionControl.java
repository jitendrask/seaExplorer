package com.sea.probe.probe.service;

import com.sea.probe.probe.model.Coordinate;
import com.sea.probe.probe.model.Direction;
import com.sea.probe.probe.model.ServiceStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProbeMotionControl {
    public Coordinate getCurrentCoordinate() {
        return new Coordinate(5,5);
    }

    public ServiceStatus moveForward(Integer value) {
        return null;
    }

    public ServiceStatus moveBackwards(Integer value) {
        return null;
    }

    public Direction turnRight() {
        return null;
    }

    public Direction turnLeft() {
        return null;
    }

    public void introduceObstacle(Coordinate coordinate) {

    }

    public List<Coordinate> getObstacles() {
        return null;
    }

    public Direction getCurrentDirection() {
        return Direction.N;
    }
}
