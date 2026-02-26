package cityrescue;

import cityrescue.enums.*;
import cityrescue.exceptions.*;

/**
 * CityRescueImpl (Starter)
 *
 * Your task is to implement the full specification.
 * You may add additional classes in any package(s) you like.
 */
public class CityRescueImpl implements CityRescue {

    // TODO: add fields (map, arrays for stations/units/incidents, counters, tick, etc.)
    // Declare a variable to hold the city map
    final private int MAX_UNITS = 50;

    CityMap cityMap;
    Station[] stations = new Station[MAX_UNITS];

    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        // Throw exception if width and height are invalid
        if (width <= 0 || height <= 0) {
            throw new InvalidGridException("Invalid width or height.");
        } else {
            // Initialise a new CitMap
            // ...
        }
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getGridSize() {
        // Ask CityMap for the grid size
        return cityMap.getGridSize();
    }

    @Override
    public void addObstacle(int x, int y) throws InvalidLocationException {
        // Retrieve the grid size
        int[] gridSize = getGridSize();

        // Check if the provided coordinates are outside the bounds
        if (x < 0 || y < 0 || x >= gridSize[0] || y >= gridSize[1]) {
            // Therefore, throw new exception
            throw new InvalidLocationException("Invalid location");
        }

        // Otherwise, add the obstacle
        cityMap.addObstacle(x, y);
    }

    @Override
    public void removeObstacle(int x, int y) throws InvalidLocationException {
        // Retrieve the grid size
        int[] gridSize = getGridSize();

        // Check if the provided coordinates are outside the bounds
        if (x < 0 || y < 0 || x >= gridSize[0] || y >= gridSize[1]) {
            // Therefore, throw new exception
            throw new InvalidLocationException("Invalid location");
        }

        // Otherwise, remove the obstacle
        cityMap.removeObstacle(x, y);
    }

    @Override
    public int addStation(String name, int x, int y) throws InvalidNameException, InvalidLocationException {
        // Check if the name is blank
        if (name.isBlank()) {
            throw new InvalidNameException("Name is invalid");
        }

        // Retrieve the grid size
        int[] gridSize = getGridSize();

        // Check if the provided coordinates are within the bounds of the map
        if (x < 0 || y < 0 || x >= gridSize[0] || y >= gridSize[1]) {
            throw new InvalidLocationException("Invalid location");
        }

        // Check if an obstacle is present at the provided coordinates
        if (cityMap.isObstaclePresent(x,y)) {
            throw new InvalidLocationException("Obstacle exists in location");
        }

        // Create a new station
        Station newStation = new Station(name, x, y);
        // Add this station to the array of stations
        stations[newStation.getStationId()-1] = newStation;

        // Mark its location as blocked
        cityMap.addObstacle(x, y);

        // Return the station id
        return newStation.getStationId();
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        // Initial value of stationIndex
        int stationIndex = findStationIndex(stationId);

        // Check if the station has any units
        if (stations[stationIndex].getNumberOfUnits() != 0) {
            throw new IllegalStateException("Station owns at least 1 unit");
        }

        // Get the coordinates of the station in question
        int[] stationCoords = stations[stationIndex].getCoordinates();

        // Remove the station from the map
        cityMap.removeObstacle(stationCoords[0], stationCoords[1]);

        // Remove the station from the list
        while (stations[stationIndex] != null && stationIndex < stations.length-1) {
            // Shift next station down in the list
            stations[stationIndex] = stations[stationIndex+1];

            stationIndex++;

            // Delete last station
            if (stationIndex == stations.length-1) {
                stations[stationIndex] = null;
            }
        }
    }

    // Linear search through all stations, and returns the index of the station matching argument stationId
    public int findStationIndex(int stationId) throws IDNotRecognisedException {
        // Initial value of stationIndex
        int stationIndex = 0;
        // Variable to see if station is found
        boolean isFound = false;
        // Try to find the station with the specified id
        while (!isFound && stationIndex < stations.length) {
            if (stations[stationIndex].getStationId() == stationId) {
                isFound = true;
            } else {
                stationIndex++;
            }
        }

        // If the station is not found, throw an exception
        if (!isFound) {
            throw new IDNotRecognisedException("Station with "+stationId+" id is not found");
        }

        return stationIndex;
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        if (maxUnits > 0) {
            throw new InvalidCapacityException("Station capacity cannot be under 0");
        }

        int stationIndex = findStationIndex((stationId));

        Station station = stations.get(stationIndex);

        if (maxUnits < station.getNumberOfUnits()) {
            throw new InvalidCapacityException("Station capacity cannot be lowered than current number of units");
        }

        station.setMaxUnits(stationIndex);
    }

    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getUnitIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public int[] getIncidentIds() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void dispatch() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void tick() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public String getStatus() {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
