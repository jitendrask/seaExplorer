package com.sea.probe.probe.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
public class SeaProbeControllerTest {

    MockMvc mockMvc;

    public SeaProbeControllerTest(MockMvc mockMvc){
        this.mockMvc = mockMvc;
    }

    @Test
    public void getProbePositionTest() throws Exception {
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
        mockMvc.perform(put("/forward/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.currentCoordinate.x").value(6))
                .andExpect(jsonPath("$.currentCoordinate.y").value(6))
                .andExpect(jsonPath("$.coordinateList", Matchers.hasSize(1)));
    }

    @Test
    public void moveBackwardTest() throws Exception {
        mockMvc.perform(put("/backwards/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.currentCoordinate.x").value(4))
                .andExpect(jsonPath("$.currentCoordinate.y").value(4))
                .andExpect(jsonPath("$.coordinateList", Matchers.hasSize(1)));
    }

    @Test
    public void turnRight() throws Exception {
        mockMvc.perform(put("/turn/right"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("E"));
    }

    @Test
    public void turnLeft() throws Exception {
        mockMvc.perform(put("/turn/left"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("E"));
    }

    @Test
    public void addObstacle() throws Exception {
        mockMvc.perform(put("/obstacle?x=4&y=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coordinates", Matchers.hasSize(1)));
    }

    @Test
    public void getAllObstacles() throws Exception {
        mockMvc.perform(get("/obstacles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coordinates", Matchers.hasSize(1)));
    }
}
