package cityrescue;

import cityrescue.enums.*;
import cityrescue.exceptions.*;

import java.util.Arrays;

/**
 * CityRescueImpl (Starter)
 * Your task is to implement the full specification.
 * You may add additional classes in any package(s) you like.
 */
public class CityRescueImpl implements CityRescue {
    // TODO: add fields (counters, etc.)
    // Array size constants:
    private final int MAX_UNITS = 50;         // Maximum number of units any station can own
    private final int MAX_STATIONS = 20;      // Max number of stations in the program
    private final int MAX_INCIDENTS = 200;    // Max number of incidents in the program

    // Variables used to keep track of the number of stations/units/incidents in the simulation
    // These variables will be incremented/decremented every time a station/unit/incident is created/deleted
    private int stationsInSimulation = 0;
    private int unitsInSimulation = 0;
    private int incidentsInSimulation = 0;

    // Universal time tracker
    int tick = 0;

    // Declare the city map
    private CityMap cityMap;
    // Initialise an array to hold all the stations
    private Station[] stations;
    // Initialise an array to hold all the units in the simulation
    private Unit[] units;
    // Initialise an array to hold all the incidents in the simulation
    private Incident[] incidents;

    /**
     * Starts a new, blank simulation.
     * @param width The width of the board.
     * @param height The height of the board.
     * @throws InvalidGridException Thrown when the specified width or height are less
     * than or equal to 0.
     */
    @Override
    public void initialise(int width, int height) throws InvalidGridException {
        // Throw exception if width and height are invalid
        if (width <= 0 || height <= 0) {
            throw new InvalidGridException("Invalid width or height.");
        }

        // Create a new city map with the specified width and height
        cityMap = new CityMap(width, height);

        // Clear all stations, units and incidents
        stations = new Station[MAX_STATIONS];
        units = new Unit[MAX_UNITS];
        incidents = new Incident[MAX_INCIDENTS];

        // Reset tick to 0
        tick = 0;
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

        // Check if the provided coordinates are outside the bounds of the map
        if (x < 0 || y < 0 || x >= gridSize[0] || y >= gridSize[1]) {
            throw new InvalidLocationException("Invalid location");
        }

        // Check if an obstacle is present at the provided coordinates
        if (cityMap.isObstaclePresent(x,y)) {
            throw new InvalidLocationException("Obstacle exists in location");
        }

        // Check if there is space to add another station
        if (stationsInSimulation == MAX_STATIONS) {
            throw new IllegalStateException("Reached maximum number of stations in simulation");
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
     * Creates and adds a new unit to the simulation.
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

        // Check if there is space to add another unit
        if (unitsInSimulation == MAX_UNITS) {
            throw new IllegalStateException("Reached maximum number of units in simulation");
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
        units[unitsInSimulation] = newUnit;

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
        UnitType[] allUnitTypes = UnitType.values();

        // Initialise some variables used in the loop
        boolean isKnownType = false;
        int i = 0;
        // Check if the specified type is known
        while (!isKnownType && i < allUnitTypes.length) {
            if (unitType == allUnitTypes[i]) {
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

    /**
     * Displays a human-readable output of the specified unit.
     * @param unitId The ID of the unit to output.
     * @return The string format of the unit's properties.
     * @throws IDNotRecognisedException Thrown when the unit with the specified ID doesn't exist.
     */
    @Override
    public String viewUnit(int unitId) throws IDNotRecognisedException {
        // Check the unit exists
        int unitIndex = findUnitIndex(unitId);
        Unit unit = units[unitIndex];

        // Retrieve the properties of this unit for formatting
        String unitType = unit.getUnitType().toString();
        int homeStationId = unit.getHomeStationId();
        int[] unitLocation = unit.getUnitCoordinates();
        String unitStatus = unit.getStatus().toString();
        String assignedIncidentId = (unit.getAssignedIncidentId() == -1) ? "-" : Integer.toString(unit.getAssignedIncidentId());
        int requiredTicksAtScene = unit.getTicksAtScene();


        // Construct the general string
        String viewUnitString = String.format("U#%d TYPE=%s HOME=%d LOC=(%d,%d) STATUS=%s INCIDENT=%s",
                unitId, unitType, homeStationId, unitLocation[0], unitLocation[1], unitStatus,
                assignedIncidentId);

        // If the unit is AT_SCENE, add the WORK attribute
        if (unit.getStatus() == UnitStatus.AT_SCENE) {
            viewUnitString = String.format(viewUnitString + " WORK=%d", requiredTicksAtScene);
        }

        return viewUnitString;
    }

    /**
     * Creates and adds another incident to the simulation.
     * @param type The type of incident to report.
     * @param severity The severity of this incident.
     * @param x x-coordinate of this incident.
     * @param y y-coordinate of this incident.
     * @return An integer representing the newly created incident.
     * @throws InvalidSeverityException Thrown when the specified severity is less
     * than 1 or greater than 5.
     * @throws InvalidLocationException Thrown when either the location provided is outside the
     * bounds of the map or there is an obstacle present at the location.
     */
    @Override
    public int reportIncident(IncidentType type, int severity, int x, int y) throws InvalidSeverityException, InvalidLocationException {
        // Check if the incident type is null
        if (type == null) {
            throw new IllegalStateException("Incident type cannot be null");
        }

        // Check if the severity is outside the predefined bounds
        if (severity < 1 || severity > 5) {
            throw new InvalidSeverityException("Severity must be between 1 and 5");
        }

        // Retrieve the grid size
        int[] gridSize = getGridSize();

        // Check if the provided coordinates are outside the bounds of the map
        if (x < 0 || y < 0 || x >= gridSize[0] || y >= gridSize[1]) {
            throw new InvalidLocationException("Invalid location");
        }

        // Check if the location is blocked
        if (cityMap.isObstaclePresent(x, y)) {
            throw new InvalidLocationException("Obstacle is present at the provided location");
        }

        // Check if there is space to add another incident
        if (incidentsInSimulation == MAX_INCIDENTS) {
            throw new IllegalStateException("Reached maximum number of incidents in simulation");
        }

        // Create a new incident
        Incident newIncident = new Incident(type, severity, IncidentStatus.REPORTED, x, y);

        // Add this new incident to the array of incidents
        incidents[incidentsInSimulation] = newIncident;

        // Increment the tracker for the number of incidents in the simulation
        incidentsInSimulation++;

        // Return this new incident's ID
        return newIncident.getIncidentId();
    }

    /**
     * Cancels an incident if it is currently reported or dispatched, and releases the assigned
     * unit if dispatched.
     * @param incidentId The incident's ID to cancel.
     * @throws IDNotRecognisedException Thrown when the incident with the specified ID does not exist.
     * @throws IllegalStateException Thrown when either the incident's status is not reported or dispatched.
     */
    @Override
    public void cancelIncident(int incidentId) throws IDNotRecognisedException, IllegalStateException {
        // Check the incident exists
        int incidentIndex = findIncidentIndex(incidentId);
        Incident incident = incidents[incidentIndex];

        // Check if the incident is not reported nor dispatched
        if (incident.getIncidentStatus() != IncidentStatus.REPORTED && incident.getIncidentStatus() != IncidentStatus.DISPATCHED) {
            throw new IllegalStateException("Incident must be reported or dispatched to be able to cancel it");
        }

        // If the incident is dispatched, release the unit assigned to it
        if (incident.getIncidentStatus() == IncidentStatus.DISPATCHED) {
            // Find the index of the unit assigned to this incident
            int unitIndex = findUnitIndex(incident.getAssignedUnitId());
            // Set this unit to IDLE
            units[unitIndex].setStatus(UnitStatus.IDLE);
            // Reset the assignedIncidentId attribute of the unit
            units[unitIndex].setAssignedIncidentId(-1);
        }

        // Update the status of the incident to CANCELLED
        incident.setIncidentStatus(IncidentStatus.CANCELLED);
        // Reset the assignedUnitId attribute of the incident
        incident.setAssignedUnitId(-1);
    }

    /**
     * Uses linear search to search through the array of incidents to find which index
     * the specified incident is at.
     * @param incidentId The incident's ID to find.
     * @return The index which the specified incident is at.
     * @throws IDNotRecognisedException Thrown when no incident exists with the provided ID.
     */
    public int findIncidentIndex(int incidentId) throws IDNotRecognisedException {
        // Initial value of incidentIndex
        int incidentIndex = 0;
        // Variable to see if incident is found
        boolean isFound = false;
        // Try to find the incident with the specified ID
        while (!isFound && incidentIndex < incidents.length) {
            if (incidents[incidentIndex].getIncidentId() == incidentId) {
                isFound = true;
            } else {
                incidentIndex++;
            }
        }

        // If the incident is not found, throw an exception
        if (!isFound) {
            throw new IDNotRecognisedException("Incident with "+incidentId+" ID is not found");
        }

        return incidentIndex;
    }

    /**
     * Updates the severity of the specified incident.
     * @param incidentId The incident's ID to change.
     * @param newSeverity The new severity for the incident.
     * @throws IDNotRecognisedException Thrown when the incident with the provided ID doesn't exist.
     * @throws InvalidSeverityException Thrown when either the severity is less than 1 or greater than 5.
     * @throws IllegalStateException Thrown when either the incident is resolved or cancelled.
     */
    @Override
    public void escalateIncident(int incidentId, int newSeverity) throws IDNotRecognisedException, InvalidSeverityException, IllegalStateException {
        // Check the incident exists
        int incidentIndex = findIncidentIndex(incidentId);
        Incident incident = incidents[incidentIndex];

        // Check if the new severity is outside the predefined bounds
        if (newSeverity < 1 || newSeverity > 5) {
            throw new InvalidSeverityException("Severity must be between 1 and 5");
        }

        // Check if the incident is resolved or cancelled
        if (incident.getIncidentStatus() == IncidentStatus.RESOLVED || incident.getIncidentStatus() == IncidentStatus.CANCELLED) {
            throw new IllegalStateException("Incident must not be resolved or cancelled");
        }

        // Update the severity of the incident
        incident.setIncidentSeverity(newSeverity);
    }

    /**
     * Produces a list of all the incident IDs in the simulation.
     * @return An array of integers representing all the incident's IDs.
     */
    @Override
    public int[] getIncidentIds() {
        // Initialise an array to hold all the incident IDs
        int[] allIncidentIds = new int[incidentsInSimulation];

        // Iterate through each incident and add its ID to the array above
        int i = 0;
        while (incidents[i] != null && i < incidentsInSimulation) {
            allIncidentIds[i] = incidents[i].getIncidentId();
            i++;
        }

        // Return the array of incident IDs
        return allIncidentIds;
    }

    /**
     * Formats a string with the properties of an incident in a human-readable way.
     * @param incidentId The incident's ID to output.
     * @return A string containing the properties of a specified incident formatted correctly.
     * @throws IDNotRecognisedException Thrown when the incident with the specified ID doesn't exist.
     */
    @Override
    public String viewIncident(int incidentId) throws IDNotRecognisedException {
        // Check the incident exists
        int incidentIndex = findIncidentIndex(incidentId);
        Incident incident = incidents[incidentIndex];

        // Extract the properties for formatting
        String incidentType = incident.getIncidentType().toString();
        int incidentSeverity = incident.getIncidentSeverity();
        int[] incidentLocation = incident.getIncidentLocation();
        String incidentStatus = incident.getIncidentStatus().toString();
        String assignedUnitID = (incident.getAssignedUnitId() == -1) ? "-" : Integer.toString(incident.getAssignedUnitId());

        // Return the formatted string
        return String.format("I#%d TYPE=%s SEV=%d LOC=(%d,%d) STATUS=%s UNIT=%s",
                incidentId, incidentType, incidentSeverity, incidentLocation[0], incidentLocation[1],
                incidentStatus, assignedUnitID);
    }

    /**
     * Assigns any IDLE units to any REPORTED incidents using the Manhattan distance between
     * units and incidents, and any tie-breaking rules that are required.
     */
    @Override
    public void dispatch() {
        // Declare variables that are used within these loops to make the code easier to read
        Incident currentIncident;
        Unit currentUnit = null;

        // Declare a variable to hold the most suitable unit for the current incident
        Unit bestUnit;
        // Declare a variable to hold the shortest distance between a unit and the current incident
        int bestUnitDistance;

        // Iterate through each REPORTED incident
        int incidentIndex = 0;
        while (incidents[incidentIndex] != null && incidentIndex < incidentsInSimulation) {
            // Check if the current incident has status REPORTED
            if (incidents[incidentIndex].getIncidentStatus() == IncidentStatus.REPORTED) {
                // Store the memory address of the current incident to make the code easier to read
                currentIncident = incidents[incidentIndex];

                // Reset the best, shortest Manhattan distance tracker
                bestUnitDistance = Integer.MAX_VALUE;
                // Reset the best unit variable
                bestUnit = null;

                // Iterate through each unit to find the best one to dispatch for this incident
                int unitIndex = 0;
                while (units[unitIndex] != null && unitIndex < unitsInSimulation) {
                    // Skip this unit if it is NOT IDLE and CANNOT handle this incident
                    if (!(units[unitIndex].getStatus() == UnitStatus.IDLE && units[unitIndex].canHandle(currentIncident.getIncidentType()))) {
                        // Point to the next unit in the array
                        unitIndex++;
                        // Move to the next unit in the array
                        continue;
                    }

                    // Store the memory address of the current unit to make the code easier to read
                    currentUnit = units[unitIndex];

                    // Calculate the Manhattan distance between this unit and the incident
                    int currentDistance = Math.abs(currentIncident.getIncidentLocation()[0] - currentUnit.getUnitCoordinates()[0])
                            + Math.abs(currentIncident.getIncidentLocation()[1] - currentUnit.getUnitCoordinates()[1]);

                    // Check if the current distance and best distance are equal
                    if (currentDistance == bestUnitDistance) {
                        // Check if the two units have the same ID
                        if (bestUnit.getUnitId() == currentUnit.getUnitId()) {
                            // Check which of the two units has the lowest home station ID
                            if (currentUnit.getHomeStationId() < bestUnit.getHomeStationId()) {
                                // Now, assign the new best unit
                                bestUnit = currentUnit;
                                bestUnitDistance = currentDistance;
                            }
                        }
                        else if (currentUnit.getUnitId() < bestUnit.getUnitId()) {
                            // Now, assign the new best unit
                            bestUnit = currentUnit;
                            bestUnitDistance = currentDistance;
                        }
                    }
                    // Check if this newly calculated distance is shorter
                    else if (currentDistance < bestUnitDistance) {
                        // Update the best unit and the best distance variables
                        bestUnit = currentUnit;
                        bestUnitDistance = currentDistance;
                    }

                    // Point to the next unit in the array
                    unitIndex++;
                }

                // Now that we have found the best unit for this incident,
                // set this incident to be assigned to this unit
                bestUnit.setAssignedIncidentId(currentIncident.getIncidentId());
                currentIncident.setAssignedUnitId(bestUnit.getUnitId());

                // Change the status of the current incident to DISPATCHED
                currentIncident.setIncidentStatus(IncidentStatus.DISPATCHED);

                // Change the status of the current unit to EN_ROUTE
                currentUnit.setStatus(UnitStatus.EN_ROUTE);
            }

            incidentIndex++;
        }
    }

    /**
     * Advances the simulation on by one tick. All EN_ROUTE units are moved one cell closer to
     * their assigned incident. If the unit has reached its assigned incident, it is marked as AT_SCENE.
     * Then, each AT_SCENE unit is checked if it has finished with its incident, if so, it is marked as
     * IDLE and the incident is marked as RESOLVED. Otherwise, the ticksSpentAtScene attribute of the
     * AT_SCENE unit is incremented.
     */
    @Override
    public void tick() {
        // Increment the tick
        tick++;

        // Iterate through each unit in the simulation and deal with it depending on its status
        int unitIndex = 0;
        try {
            while (units[unitIndex] != null && unitIndex < unitsInSimulation) {
                // Store the memory address of this unit to make the code easier to read
                Unit unit =  units[unitIndex];

                // For each EN_ROUTE unit, move it one cell closer to its assigned incident
                if (unit.getStatus() == UnitStatus.EN_ROUTE) {
                    // Store the memory address of the incident that this unit is assigned to
                    Incident incident = incidents[findIncidentIndex(unit.getAssignedIncidentId())];

                    // Call the move function to move this unit one cell closer to its assigned incident
                    moveUnit(unit, incident);

                    // Check if this unit has arrived at its assigned incident
                    if (Arrays.equals(unit.getUnitCoordinates(), incident.getIncidentLocation())) {
                        // Set the status of this unit to AT_SCENE
                        unit.setStatus(UnitStatus.AT_SCENE);
                    }
                }

                // For each AT_SCENE unit, check if it is finished on scene, or increment its ticksSpentAtScene attribute
                if (unit.getStatus() == UnitStatus.AT_SCENE) {
                    // Check if the unit has completed its time at the scene
                    // I.e. does ticksSpentAtScene == TICKS_AT_SCENE?
                    if (unit.getTicksSpentAtScene() == unit.getTicksAtScene()) {
                        // Therefore, the unit has completed the incident
                        // Mark the unit as IDLE and keep it in its current location
                        unit.setStatus(UnitStatus.IDLE);

                        // Reset this unit's ticksSpentAtScene attribute
                        unit.resetTicksSpentAtScene();

                        // Store the memory address of the incident that this unit is assigned to
                        Incident incident = incidents[findIncidentIndex(unit.getAssignedIncidentId())];

                        // Mark the incident as RESOLVED and leave it in the simulation
                        incident.setIncidentStatus(IncidentStatus.RESOLVED);

                        // Reset the assignedUnitId attribute of the incident
                        incident.setAssignedUnitId(-1);

                        // Reset the unit's assigned incident attribute
                        unit.setAssignedIncidentId(-1);
                    } else {
                        // Increment the number of ticks spent at scene
                        unit.incrementTicksSpentAtScene();
                    }
                }

                // Move to the next unit in the simulation
                unitIndex++;
            }
        } catch (IDNotRecognisedException e) {
            System.out.println("Could not find required unit or incident");
        }
    }

    /**
     * Move the specified unit one cell closer to its assigned incident.
     * @param unit The object reference of the unit to move.
     * @param assignedIncident The object reference to the assigned incident.
     */
    public void moveUnit(Unit unit, Incident assignedIncident) {
        // Extract the unit's current location
        int[] currentLocation = unit.getUnitCoordinates();

        // Get the grid size
        int[] gridSize = getGridSize();

        // Initialise a 2D array to hold the candidate moves
        int[][] candidateMoves = new int[4][2];

        // Array structure:
        // index | x | y
        // --------------
        //   0   | _ | _   <- north
        //   1   | _ | _   <- east
        //   2   | _ | _   <- south
        //   3   | _ | _   <- west

        // Store the candidate moves
        // Add the location once moved north
        candidateMoves[0][0] = currentLocation[0];
        candidateMoves[0][1] = currentLocation[1] + 1;

        // Add the location once moved east
        candidateMoves[1][0] = currentLocation[0] + 1;
        candidateMoves[1][1] = currentLocation[1];

        // Add the location once moved south
        candidateMoves[2][0] = currentLocation[0];
        candidateMoves[2][1] = currentLocation[1] - 1;

        // Add the location once moved west
        candidateMoves[3][0] = currentLocation[0] - 1;
        candidateMoves[3][1] = currentLocation[1];

        // Store the current distance from the unit to the incident
        int currentDistance = Math.abs(assignedIncident.getIncidentLocation()[0] - unit.getUnitCoordinates()[0])
                + Math.abs(assignedIncident.getIncidentLocation()[1] - unit.getUnitCoordinates()[1]);

        // Initialise the tracker for the best direction
        int bestDirection = -1;

        // Variable use to keep track of the first legal direction
        int firstLegalDirection = Integer.MAX_VALUE;

        // Iterate through and analyse each candidate move
        int currentMoveIndex = 0;
        while (bestDirection == -1 && currentMoveIndex < candidateMoves.length) {
            // Store the current move
            int[] currentMove = candidateMoves[currentMoveIndex];

            // Only consider this move if it stays within the bound of the map and the location is not blocked
            if (currentMove[0] >= 0 && currentMove[1] >= 0 && currentMove[0] < gridSize[0]
                    && currentMove[1] < gridSize[1] && !cityMap.isObstaclePresent(currentMove[0], currentMove[1])) {
                // Update the first legal direction
                if (currentMoveIndex < firstLegalDirection) {
                    firstLegalDirection = currentMoveIndex;
                }

                // Calculate the distance this location is from the assigned incident
                int distance = Math.abs(assignedIncident.getIncidentLocation()[0] - currentMove[0])
                        + Math.abs(assignedIncident.getIncidentLocation()[1] - currentMove[1]);

                // If this new distance is shorter than the unit's current, choose this direction
                if (distance < currentDistance) {
                    // Update the best direction
                    bestDirection = currentMoveIndex;
                }
            }

            // Move to the next possible direction/move
            currentMoveIndex++;
        }

        // If bestDirection is still -1, that means there is no legal move that reduces the distance
        // Therefore, just take the first legal move in the order N, E, S, W
        // Note: If firstLegalDirection is still Integer.MAX_VALUE, that means there are no legal moves available,
        // so stay the unit must stay put
        if (bestDirection == -1 && firstLegalDirection != Integer.MAX_VALUE) {
            bestDirection = firstLegalDirection;
        }

        // Now, make the move if bestDirection is not -1
        if (bestDirection != -1) {
            unit.setUnitCoordinates(candidateMoves[bestDirection]);
        }
    }

    /**
     * Constructs a string containing the status of the simulation. It lists the number of stations,
     * units, incidents, and obstacles. It also lists all the properties for each unit and each incident.
     * @return A string representing the current status of the simulation.
     */
    @Override
    public String getStatus() {
        // Initialise a string for output
        String simulationStatusString = new String();

        // Add the tick number to the string status
        simulationStatusString = simulationStatusString.concat("TICK=").concat(String.valueOf(tick)).concat("\n");

        // Construct a string containing the number of stations, units, incidents and obstacles
        String outputLine2 = String.format("STATIONS=%d UNITS=%d INCIDENTS=%d OBSTACLES=%d",
                stationsInSimulation, unitsInSimulation, incidentsInSimulation, cityMap.getNumberOfObstacles());

        // Add line 2 to the main string
        simulationStatusString = simulationStatusString.concat(outputLine2).concat("\n");

        // Add the INCIDENTS title
        simulationStatusString = simulationStatusString.concat("INCIDENTS\n");
        // Add each incident to the main string
        int incidentIndex = 0;
        // Try block to catch the IDNotRecognisedException
        try {
            while (incidents[incidentIndex] != null && incidentIndex < incidentsInSimulation) {
                simulationStatusString = simulationStatusString.concat(viewIncident(incidents[incidentIndex].getIncidentId())).concat("\n");
                incidentIndex++;
            }
        } catch (IDNotRecognisedException e) {
            return "Could not find the required incident";
        }

        // Add the UNITS title
        simulationStatusString = simulationStatusString.concat("UNITS\n");
        // Add each unit to the main string
        int unitIndex = 0;
        // Try block to catch the IDNotRecognisedException
        try {
            while (units[unitIndex] != null && unitIndex < unitsInSimulation) {
                simulationStatusString = simulationStatusString.concat(viewUnit(units[unitIndex].getUnitId())).concat("\n");
                unitIndex++;
            }
        } catch (IDNotRecognisedException e) {
            return "Could not find the required unit";
        }

        return simulationStatusString;
    }
}
