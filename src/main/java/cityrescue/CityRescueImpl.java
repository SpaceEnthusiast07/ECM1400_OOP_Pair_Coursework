package cityrescue;

import cityrescue.enums.*;
import cityrescue.exceptions.*;

import java.util.Arrays;

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

    // Variables used to keep track of the number of stations/units/incidents in the simulation
    // These variables will be incremented/decremented every time a station/unit/incident is created/deleted
    private int stationsInSimulation = 0;
    private int unitsInSimulation = 0;
    private int incidentsInSimulation = 0;

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
        throw new UnsupportedOperationException("Not implemented yet");
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

        // Increment the tracker for the number of stations in this simulation
        stationsInSimulation++;

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
        int[] stationCoordinates = stations[stationIndex].getCoordinates();

        // Remove the station from the map
        cityMap.removeObstacle(stationCoordinates[0], stationCoordinates[1]);

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

        // Decrement the tracker for the number of stations in this simulation
        stationsInSimulation--;
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
        int[] allStationIds = new int[stationsInSimulation];

        // Extract the station ids
        int i = 0;
        while (stations[i] != null && i < stationsInSimulation) {
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
        // Check to see if the station exists
        // and find the index of the specified station
        int stationIndex = findStationIndex(stationId);    // Exception is thrown from within findStationIndex()

        // Check if the station is full
        if (stations[stationIndex].getNumberOfUnits() == MAX_UNITS) {
            throw new IllegalStateException("Station is full");
        }

        // Check if the specified unit type is null or not recognised
        if (!isKnownUnitType(type)) {
            throw new InvalidUnitException("Unit type is not recognised");
        }

        // Extract the location of the station
        int[] coordinates = stations[stationIndex].getCoordinates();
        // Create a new unit of the specified type
        Unit newUnit = switch (type) {
            case AMBULANCE -> new Ambulance(coordinates[0], coordinates[1], stationId, UnitStatus.IDLE);
            case FIRE_ENGINE -> new Fire_Engine(coordinates[0], coordinates[1], stationId, UnitStatus.IDLE);
            case POLICE_CAR -> new Police_Car(coordinates[0], coordinates[1], stationId, UnitStatus.IDLE);
        };

        // Add this new unit to the array of units
        units[newUnit.getUnitId()] = newUnit;

        // Increment the tracker for the number of units in this simulation
        unitsInSimulation++;

        // Return this unit's ID
        return newUnit.getUnitId();
    }

    /**
     * Determines if the specified unit type is correct.
     * @param unitType Unit type in question.
     * @return True if the specified unit type is correct, otherwise false.
     */
    boolean isKnownUnitType(UnitType unitType) {
        // Generate an array of all unit types
        UnitType[] unitTypes = UnitType.values();

        // Initialise some variables used in the loop
        boolean isKnownType = false;
        int i = 0;
        // Check if the specified type is known
        while (!isKnownType && i < unitTypes.length) {
            if (unitType == unitTypes[i]) {
                isKnownType = true;
            }
            i++;
        }

        return isKnownType;
    }

    /**
     * Retires (deletes) a unit if it exists and is free.
     * @param unitId The unit's ID to decommission.
     * @throws IDNotRecognisedException Thrown when the specified unit ID doesn't exist.
     * @throws IllegalStateException Thrown when the unit is not free.
     */
    @Override
    public void decommissionUnit(int unitId) throws IDNotRecognisedException, IllegalStateException {
        // Check if the unit exists
        int unitIndex = findUnitIndex(unitId);

        // Check if the unit is busy
        if (units[unitIndex].getStatus() == UnitStatus.EN_ROUTE  ||  units[unitIndex].getStatus() == UnitStatus.AT_SCENE) {
            throw new IllegalStateException("Unit is not free");
        }

        // Get the location of the unit
        int[] unitLocation = units[unitIndex].getUnitCoordinates();
        // Get the location of the station
        int[] stationLocation = stations[findStationIndex(units[unitIndex].getHomeStationId())].getCoordinates();

        // Only remove the obstacle at the unit's location if it is not at the station
        if (!Arrays.equals(stationLocation, unitLocation)) {
            cityMap.removeObstacle(unitLocation[0], unitLocation[1]);
        }

        // Now, remove the unit from the array of units
        // This is done by shifting all subsequent units down the unit array
        while (units[unitIndex] != null && unitIndex < units.length-1) {
            // Shift next unit down to the current position in the unit array
            units[unitIndex] = units[unitIndex+1];

            unitIndex++;

            // Delete last station
            if (unitIndex == units.length-1) {
                units[unitIndex] = null;
            }
        }

        // Decrement the tracker for the number of units in this simulation
        unitsInSimulation--;
    }

    /**
     * Uses linear search to search through the array of units to find which index
     * the specified unit is at.
     * @param unitId The unit's ID to find.
     * @return The index which the specified unit is at.
     * @throws IDNotRecognisedException Thrown when no unit exists with the provided ID.
     */
    public int findUnitIndex(int unitId) throws IDNotRecognisedException {
        // Initial value of unitIndex
        int unitIndex = 0;
        // Variable to see if unit is found
        boolean isFound = false;
        // Try to find the unit with the specified ID
        while (!isFound && unitIndex < units.length) {
            if (units[unitIndex].getUnitId() == unitId) {
                isFound = true;
            } else {
                unitIndex++;
            }
        }

        // If the unit is not found, throw an exception
        if (!isFound) {
            throw new IDNotRecognisedException("Unit with "+unitId+" ID is not found");
        }

        return unitIndex;
    }

    /**
     * Transfer a unit to a new home station.
     * @param unitId The unit to transfer.
     * @param newStationId The destination station's ID.
     * @throws IDNotRecognisedException Thrown when either the unit or station doesn't exist.
     * @throws IllegalStateException Thrown when either the unit is not busy or the destination station is full.
     */
    @Override
    public void transferUnit(int unitId, int newStationId) throws IDNotRecognisedException, IllegalStateException {
        // Throw exception if the unit to move doesn't exist
        int unitIndex = findUnitIndex(unitId);
        // Throw exception if the destination station doesn't exist
        int newStationIndex = findStationIndex(newStationId);

        // Store the unit and station to make the code easier to read
        Unit unit = units[unitIndex];
        Station destinationStation = stations[newStationIndex];

        // Check if the unit is busy
        if (unit.getStatus() != UnitStatus.IDLE) {
            throw new IllegalStateException("Unit is not idle");
        }

        // Check if the destination station is full
        if (destinationStation.getNumberOfUnits() == MAX_UNITS) {
            throw new IllegalStateException("Station is full");
        }

        // Decrement the number of units for the old station
        stations[findStationIndex(unit.getHomeStationId())].decrementNumberOfUnits();

        // Now, update the units home station ID
        unit.setHomeStationId(newStationId);

        // Move the unit to the destination station
        unit.setUnitCoordinates(destinationStation.getCoordinates());

        // Increment the number of units for the new station
        destinationStation.incrementNumberOfUnits();
    }

    /**
     * Toggles a unit's status between OUT_OF_SERVICE and IDLE.
     * @param unitId The unit of which to toggle its status between OUT_OF_SERVICE and IDLE.
     * @param outOfService Whether to set to OUT_OF_SERVICE or IDLE.
     * @throws IDNotRecognisedException Thrown when the unit with the specified ID doesn't exist.
     * @throws IllegalStateException Thrown when the client requests this unit to be
     * OUT_OF_SERVICE, but this unit is not currently IDLE.
     */
    @Override
    public void setUnitOutOfService(int unitId, boolean outOfService) throws IDNotRecognisedException, IllegalStateException {
        // Check units exists
        int unitIndex = findUnitIndex(unitId);
        Unit unit = units[unitIndex];

        // Check if the unit is not idle
        if (outOfService && (unit.getStatus() != UnitStatus.IDLE)) {
            throw new IllegalStateException("Unit is not idle");
        }

        // Now, set this unit's status to OUT_OF_SERVICE if outOfService is true
        if (outOfService) {
            unit.setStatus(UnitStatus.OUT_OF_SERVICE);
        }
        // Else, set this unit's status to IDLE
        else {
            unit.setStatus(UnitStatus.IDLE);
        }
    }

    /**
     * Produces a list of all unit IDs in ascending order.
     * @return An integer array of all unit IDs.
     */
    @Override
    public int[] getUnitIds() {
        // Initialise an array to hold all the unit IDs
        int[] unitIds = new int[unitsInSimulation];

        // Iterate through the array of units, adding each ID to the array of IDs
        int i = 0;
        while (units[i] != null && i < unitsInSimulation) {
            unitIds[i] = units[i].getUnitId();
        }

        // Return the array of unit IDs
        return unitIds;
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
