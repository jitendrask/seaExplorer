package com.sea.probe.probe.controller;

import com.sea.probe.probe.model.*;
import com.sea.probe.probe.service.ProbeMotionControl;
import org.springframework.web.bind.annotation.*;

@RestController
public class SeaProbeController {

    ProbeMotionControl probeMotionControl;

    public SeaProbeController(ProbeMotionControl probeMotionControl){
        this.probeMotionControl = probeMotionControl;
    }

    @GetMapping("/probPosition")
    public CurrentPosition getProbePosition(){
        return new CurrentPosition(probeMotionControl.getCurrentCoordinate(), probeMotionControl.getCurrentDirection());
    }

    @PutMapping("/forward/{value}")
    public ServiceStatus moveForward(@PathVariable("value") Integer value){
        return probeMotionControl.moveForward(value);
    }

    @PutMapping("/backwards/{value}")
    public ServiceStatus moveBackward(@PathVariable("value") Integer value){
        return probeMotionControl.moveBackwards(value);
    }

    @PutMapping("/turn/right")
    public Direction turnRight(){
        return probeMotionControl.turnRight();
    }

    @PutMapping("/turn/left")
    public Direction turnLeft(){
        return probeMotionControl.turnLeft();
    }

    @PutMapping("/obstacle")
    public Coordinates addObstacle(@RequestParam("x") int x, @RequestParam("y") int y){
        probeMotionControl.introduceObstacle(new Coordinate(x,y));
        return new Coordinates(probeMotionControl.getObstacles());
    }
    @GetMapping("/obstacles")
    public Coordinates getAllObstacles(){
        return new Coordinates(probeMotionControl.getObstacles());
    }
}

