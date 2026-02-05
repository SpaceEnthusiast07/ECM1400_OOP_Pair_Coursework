package cityrescue;

import java.util.ArrayList;
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
    final private int MAX_STATIONS = 20;
    final private int MAX_UNITS = 50;
    final private int MAX_INCIDENTS = 200;

    CityMap cityMap;
    ArrayList<Station> stations = new ArrayList<Station>();

    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        // Throw exception if width and height are invalid
        if (width <= 0 || height <= 0) {
            throw new InvalidGridException("Invalid width or height.");
        } else {
            // ...
            //TODO: Initialise a new CitMap
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
        if (name.isBlank()) {
            throw new InvalidNameException("Name is invalid");
        }

        int[] gridSize = getGridSize();

        if (x < 0 || y < 0 || x >= gridSize[0] || y >= gridSize[1]) {
            throw new InvalidLocationException("Invalid location");
        }

        if (cityMap.isObstaclePresent(x,y)) {
            throw new InvalidLocationException("Obstacle exists in location");
        }

        Station newStation = new Station(name, x, y);

        stations.add(newStation);
        cityMap.addObstacle(x, y);

        return newStation.getStationId();
    }

    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {

        int stationIndex = findStationIndex((stationId));

        if (stationIndex == -1) {   //station id not found
            throw new IDNotRecognisedException("Station with ID not found");
        }

        // TODO: CHECK OWNS UNITS

        int[] stationCoords = stations.get(stationIndex).getCoordinates();

        cityMap.removeObstacle(stationCoords[0], stationCoords[1]);

        stations.remove(stationIndex);
    }

    // Linear search through all stations, and returns the arraylist index of the station matching argument Id
    public int findStationIndex(int stationId) {
        int[] allStationIds = getStationIds();

        int stationIndex = 0;
        boolean found = false;
        while (stationIndex < allStationIds.length && !found) {
            if (stations.get(stationIndex).getStationId() == stationId) {
                found = true;
            } else {
                stationIndex++;
            }
        }

        return stationIndex;
    }

    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        if (maxUnits > 0) {
            throw new InvalidCapacityException("Station capacity cannot be under 0");
        }

        int stationIndex = findStationIndex((stationId));

        if (stationIndex == -1) {   //station id not found
            throw new IDNotRecognisedException("Station with ID not found");
        }

        Station station = stations.get(stationIndex);

        if (maxUnits < station.getNumberOfUnits()) {
            throw new InvalidCapacityException("Station capacity cannot be lowered than current number of units");
        }

        station.setMaxUnits(stationIndex);
    }

    @Override
    public int[] getStationIds() {
        int[] allStationIds = new int[stations.size()];

        for (int i=0; i < allStationIds.length; i++) {
            allStationIds[i] = stations.get(i).getStationId();
        }

        return allStationIds;
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
