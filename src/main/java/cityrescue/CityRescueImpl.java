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
    // Array size constants:
    private final int MAX_UNITS = 50;         // Maximum number of units any station can own
    private final int MAX_STATIONS = 20;      // Max number of stations in the program
    private final int MAX_INCIDENTS = 200;    // Max number of incidents in the program

    // Declare the city map
    private CityMap cityMap;
    // Initialise an array to hold all the stations
    private Station[] stations = new Station[MAX_STATIONS];
    // Initialise an array to hold all the units in the simulation
    private Unit[] units = new Unit[MAX_UNITS];
    // Initialise an array to hold all the incidents in the simulation
    private Incident[] incidents = new Incident[MAX_INCIDENTS];

    /**
     * TODO: Complete initialise function!
     * Starts a new, blank simulation.
     * @param width The width of the board.
     * @param height The height of the board.
     * @throws InvalidGridException Thrown when the specified width or height are 0 or less.
     */
    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        throw new UnsupportedOperationException("Not implemented yet");
        // Throw exception if width and height are invalid
        if (width <= 0 || height <= 0) {
            throw new InvalidGridException("Invalid width or height.");
        } else {
            // Clear all stations, units and incidents
            // TODO: Clear all stations, units and incidents

            // Create a new city map with the specified width and height
            cityMap = new CityMap(width, height);

            // Reset tick to 0
            // TODO: Reset tick to 0
        }
    }

    /**
     * Retrieves the size of the city map in the format {x,y}.
     * @return An integer array with the map size.
     */
    @Override
    public int[] getGridSize() {
        // Ask CityMap for the grid size
        return cityMap.getGridSize();
    }

    /**
     * Adds an obstacle to the map at the specified location. Coordinates are 1-based.
     * @param x x-coordinate of the location.
     * @param y y-coordinate of the location.
     * @throws InvalidLocationException Thrown when the provided location is outside the
     * bounds of the map.
     */
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

    /**
     * Removes the obstacle at the specified location.
     * @param x x-coordinate of the obstacle.
     * @param y y-coordinate of the obstacle.
     * @throws InvalidLocationException Thrown when the provided location is outside the
     * bounds of the map.
     */
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

    /**
     * Add a station to the simulation. Must specify a location to place the station at.
     * @param name The station name.
     * @param x x-coordinate of the new station's location.
     * @param y y-coordinate of the new station's location.
     * @return The new station's ID as an integer.
     * @throws InvalidNameException Thrown when the provided name is blank/empty.
     * @throws InvalidLocationException Thrown either when the provided location is outside the
     * bounds of the map or an obstacle is present at the specified location.
     */
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

    /**
     * Removes a station from the simulation.
     * @param stationId Used to identify the correct station to remove.
     * @throws IDNotRecognisedException Thrown when no station exists with the provided ID.
     * @throws IllegalStateException Thrown when the specified station owns at least one unit.
     */
    @Override
    public void removeStation(int stationId) throws IDNotRecognisedException, IllegalStateException {
        // Initial value of stationIndex
        int stationIndex = findStationIndex(stationId);

        // Check if the station has any units
        if (stations[stationIndex].getNumberOfUnits() != 0) {
            throw new IllegalStateException("Station owns "+stations[stationIndex].getNumberOfUnits()+" unit(s)");
        }

        // Get the coordinates of the station in question
        int[] stationCoords = stations[stationIndex].getCoordinates();

        // Remove the station from the map
        cityMap.removeObstacle(stationCoords[0], stationCoords[1]);

        // Remove the station from the list
        while (stations[stationIndex] != null && stationIndex < stations.length-1) {
            // Shift next station down to the current position in the station array
            stations[stationIndex] = stations[stationIndex+1];

            stationIndex++;

            // Delete last station
            if (stationIndex == stations.length-1) {
                stations[stationIndex] = null;
            }
        }
    }

    /**
     * Uses linear search to search through the array of stations to find which index
     * the specified station is at.
     * @param stationId The station's ID to find.
     * @return The index which the specified station is at.
     * @throws IDNotRecognisedException Thrown when no station exists with the provided ID.
     */
    public int findStationIndex(int stationId) throws IDNotRecognisedException {
        // Initial value of stationIndex
        int stationIndex = 0;
        // Variable to see if station is found
        boolean isFound = false;
        // Try to find the station with the specified ID
        while (!isFound && stationIndex < stations.length) {
            if (stations[stationIndex].getStationId() == stationId) {
                isFound = true;
            } else {
                stationIndex++;
            }
        }

        // If the station is not found, throw an exception
        if (!isFound) {
            throw new IDNotRecognisedException("Station with "+stationId+" ID is not found");
        }

        return stationIndex;
    }

    /**
     * Stets the maximum number of units a specified station can own.
     * @param stationId The station's ID to adjust.
     * @param maxUnits The new max number of units the station can own.
     * @throws IDNotRecognisedException Thrown when no station exists with the provided ID.
     * @throws InvalidCapacityException Thrown either when the specified capacity is less than 0 or
     * the new capacity is less than the current.
     */
    @Override
    public void setStationCapacity(int stationId, int maxUnits) throws IDNotRecognisedException, InvalidCapacityException {
        // Check if the capacity is less then 0
        if (maxUnits < 0) {
            throw new InvalidCapacityException("Station capacity cannot be under 0");
        }

        // Retrieve the index this station is at
        int stationIndex = findStationIndex(stationId);

        // Check if the new capacity is less than the current
        if (maxUnits < stations[stationIndex].getNumberOfUnits()) {
            throw new InvalidCapacityException("Station capacity cannot be lowered than current number of units");
        }

        // Set the new max units for this station
        stations[stationIndex].setMaxUnits(maxUnits);
    }

    /**
     * Produces a list of all station IDs in ascending order.
     * @return An array of integers representing station IDs.
     */
    @Override
    public int[] getStationIds() {
        // Initialise an array to hold the station ids
        int[] allStationIds = new int[stations.length];

        // Extract the station ids
        for (int i = 0; i < allStationIds.length; i++) {
            allStationIds[i] = stations[i].getStationId();
        }

        return allStationIds;
    }

    /**
     * TODO
     * @param stationId Identifies which station to add the unit to.
     * @param type The type of unit to add.
     * @return The unit's ID starting from 1.
     * @throws IDNotRecognisedException Thrown when a station with this ID doesn't exist.
     * @throws InvalidUnitException Thrown when the specified unit is null or not recognised.
     * @throws IllegalStateException Thrown when the specified station is at capacity (full).
     */
    @Override
    public int addUnit(int stationId, UnitType type) throws IDNotRecognisedException, InvalidUnitException, IllegalStateException {
        // TODO: implement
        throw new UnsupportedOperationException("Not implemented yet");
        // Check to see if the station exists
        // Find the index of the specified station
        int stationIndex = findStationIndex(stationId);    // Exception is thrown from within findStationIndex()

        // Check if the station is full
        if (stations[stationIndex].getNumberOfUnits() == MAX_UNITS) {
            throw new IllegalStateException("Station is full");
        }

        // Check if the specified unit type is null or not recognised
        if (type != UnitType.AMBULANCE || type != UnitType.FIRE_ENGINE || type != UnitType.POLICE_CAR) {
            throw new InvalidUnitException("Unit type is not recognised");
        }

        // Create a new unit of the specified type
        switch (type) {
            case AMBULANCE: Ambulance newUnit = new Ambulance(); break;
            case FIRE_ENGINE: Fire_Engine newUnit = new Fire_Engine(); break;
            case POLICE_CAR: Police_Car newUnit = new Police_Car(); break;
        }
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
