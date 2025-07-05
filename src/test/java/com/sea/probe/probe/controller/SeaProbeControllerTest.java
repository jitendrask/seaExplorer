package com.sea.probe.probe.controller;

import com.sea.probe.probe.model.Coordinate;
import com.sea.probe.probe.model.Direction;
import com.sea.probe.probe.model.ServiceStatus;
import com.sea.probe.probe.model.Status;
import com.sea.probe.probe.service.ProbeMotionControl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SeaProbeController.class)
@AutoConfigureMockMvc
public class SeaProbeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ProbeMotionControl probeMotionControl;

    @Test
    public void getProbePositionTest() throws Exception {
        when(probeMotionControl.getCurrentCoordinate()).thenReturn(new Coordinate(5,5));
        when(probeMotionControl.getCurrentDirection()).thenReturn(Direction.N);

        mockMvc.perform(get("/probPosition"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.currentDirection")
                        .value("N"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.currentCoordinate.x")
                        .value("5"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.currentCoordinate.y")
                        .value("5"));
    }

    @Test
    public void moveForwardTest() throws Exception {
        ServiceStatus serviceStatus = new ServiceStatus(
                Status.SUCCESS,
                List.of(new Coordinate(5,6)),
                new Coordinate(6,6));
        when(probeMotionControl.moveForward(anyInt())).thenReturn(serviceStatus);

        mockMvc.perform(put("/forward/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.currentCoordinate.x").value(6))
                .andExpect(jsonPath("$.currentCoordinate.y").value(6))
                .andExpect(jsonPath("$.coordinateList", Matchers.hasSize(1)));
    }

    @Test
    public void moveBackwardTest() throws Exception {
        ServiceStatus serviceStatus = new ServiceStatus(
                Status.SUCCESS,
                List.of(new Coordinate(5,4)),
                new Coordinate(4,4));
        when(probeMotionControl.moveBackwards(anyInt())).thenReturn(serviceStatus);

        mockMvc.perform(put("/backwards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.currentCoordinate.x").value(4))
                .andExpect(jsonPath("$.currentCoordinate.y").value(4))
                .andExpect(jsonPath("$.coordinateList", Matchers.hasSize(1)));
    }

    @Test
    public void turnRight() throws Exception {
        when(probeMotionControl.turnRight()).thenReturn(Direction.E);
        mockMvc.perform(put("/turn/right"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("E"));
    }

    @Test
    public void turnLeft() throws Exception {
        when(probeMotionControl.turnLeft()).thenReturn(Direction.E);
        mockMvc.perform(put("/turn/left"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("E"));
    }

    @Test
    public void addObstacle() throws Exception {
        when(probeMotionControl.getObstacles()).thenReturn(List.of(new Coordinate(4,5)));
        mockMvc.perform(put("/obstacle?x=4&y=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coordinates", Matchers.hasSize(1)));
    }

    @Test
    public void getAllObstacles() throws Exception {
        when(probeMotionControl.getObstacles()).thenReturn(List.of(new Coordinate(4,5)));
        mockMvc.perform(get("/obstacles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coordinates", Matchers.hasSize(1)));
    }
}
