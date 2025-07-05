package com.sea.probe.probe.service;

import com.sea.probe.probe.model.Coordinate;
import com.sea.probe.probe.model.Direction;
import com.sea.probe.probe.model.ServiceStatus;
import com.sea.probe.probe.model.Status;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
class ProbeMotionControlTest {

    @InjectMocks
    ProbeMotionControl probeMotionControl;

    @Test
    @Order(1)
    void getCurrentCoordinate() {
        ProbeMotionControl probeMotionControl = new ProbeMotionControl();
        Coordinate coordinate = probeMotionControl.getCurrentCoordinate();
        assertEquals(5, coordinate.x());
        assertEquals(5, coordinate.y());
    }


    @Test
    @Order(2)
    void moveForwardSuccess() {
        ServiceStatus serviceStatus = probeMotionControl.moveForward(4);
        assertEquals(Status.SUCCESS, serviceStatus.status());
    }

    @Test
    @Order(3)
    void moveForwardObstructed() {
        probeMotionControl.turnRight();
        probeMotionControl.turnRight();
        probeMotionControl.introduceObstacle(new Coordinate(5,7));
        ServiceStatus serviceStatus = probeMotionControl.moveForward(4);
        assertEquals(Status.OBSTRUCTED, serviceStatus.status());
    }



    @Test
    @Order(4)
    void moveForwardOutOfBounds() {
        probeMotionControl.turnRight();
        ServiceStatus serviceStatus = probeMotionControl.moveForward(6);
        assertEquals(Status.OUTOFBOUNDS, serviceStatus.status());
    }

    @Test
    @Order(5)
    void moveBackwardsObstructed() {
        probeMotionControl.introduceObstacle(new Coordinate(6,8));
        ServiceStatus serviceStatus = probeMotionControl.moveBackwards(2);
        System.out.println(serviceStatus);
        assertEquals(Status.OBSTRUCTED, serviceStatus.status());
    }

    @Test
    @Order(6)
    void moveBackwardsSuccess() {
        probeMotionControl.turnRight();
        probeMotionControl.turnRight();
        ServiceStatus serviceStatus = probeMotionControl.moveBackwards(4);
        assertEquals(Status.SUCCESS, serviceStatus.status());
    }

    @Test
    @Order(7)
    void moveBackwardsOutOfBounds() {
        ServiceStatus serviceStatus = probeMotionControl.moveBackwards(6);
        assertEquals(Status.OUTOFBOUNDS,serviceStatus.status());
    }

    @Test
    @Order(8)
    void turnRight() {
        Direction currentDirection = probeMotionControl.getCurrentDirection();
        Direction expectedDirection = switch (currentDirection){
            case E -> Direction.S;
            case N -> Direction.E;
            case S -> Direction.W;
            case W -> Direction.N;
        };
        assertEquals(probeMotionControl.turnRight(),expectedDirection);
    }

    @Test
    @Order(9)
    void turnLeft() {
        Direction currentDirection = probeMotionControl.getCurrentDirection();
        Direction expectedDirection = switch (currentDirection){
            case E -> Direction.N;
            case N -> Direction.W;
            case S -> Direction.E;
            case W -> Direction.S;
        };
        assertEquals(probeMotionControl.turnLeft(),expectedDirection);
    }

    @Test
    @Order(10)
    void introduceObstacle() {
        probeMotionControl.introduceObstacle(new Coordinate(5,6));
        assertEquals(3, probeMotionControl.getObstacles().size());
    }

    @Test
    @Order(11)
    void getObstacles() {
        assertEquals(3, probeMotionControl.getObstacles().size());
    }

    @Test
    @Order(12)
    void getCurrentDirection() {
        assertEquals(Direction.E, probeMotionControl.getCurrentDirection());
    }
}