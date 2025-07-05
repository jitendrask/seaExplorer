package com.sea.probe.probe.model;

import java.util.List;

public record ServiceStatus(Status status, List<Coordinate> coordinateList, Coordinate currentCoordinate)  {
}
