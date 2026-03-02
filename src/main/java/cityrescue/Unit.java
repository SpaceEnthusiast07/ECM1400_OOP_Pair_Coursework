package cityrescue;

import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitStatus;
import cityrescue.enums.UnitType;

/**
 * TODO: Describe this class.
 */
public abstract class Unit {
    // Declare a variable to hold the unit id
    private final int unitId;
    // Location for unit
    private int[] coordinate;
    // Number of units currently in existence
    private static int numberOfUnits;
    // Indicates which station this unit belongs to
    private int homeStationId;
    // Status indicates what the unit is currently doing
    private UnitStatus status;
    // The ID of the incident that this unit has been assigned to
    private int assignedIncidentId;
    // Represents the number of ticks currently spent at scene
    private int ticksSpentAtScene;

    /**
     * Sets up a new unit by giving it a unique ID, location, assigning it to a
     * station and initialising its status.
     * @param x x-coordinate of location.
     * @param y y-coordinate of location.
     * @param homeStationId Which station owns this unit.
     * @param status The current state of the unit.
     */
    public Unit(int x, int y, int homeStationId, UnitStatus status) {
        // Give this new station an ID
        this.unitId = ++numberOfUnits;
        // Set the new units location
        this.coordinate = new int[] {x, y};
        // Indicate which station it belongs to
        this.homeStationId = homeStationId;
        // Set the initial state of the units status
        this.status = status;
        // Initialise the number of completed ticks at scene
        ticksSpentAtScene = 0;
    }

    /**
     * Allows the client to obtain the ID for this unit.
     * @return This unit's ID.
     */
    public int getUnitId() {
        return unitId;
    }

    /**
     * Allows the client to obtain the current location of this unit.
     * @return The location of this unit as an array of two integers.
     */
    public int[] getUnitCoordinates() {
        return coordinate;
    }

    /**
     * Allows the client to see what state this unit is currently in.
     * @return The unit's current state.
     */
    public UnitStatus getStatus() {
        return this.status;
    }

    /**
     * Updates the status of the unit.
     * @param newStatus The new status for the unit.
     */
    public void setStatus(UnitStatus newStatus) {
        this.status = newStatus;
    }

    /**
     * Updates the units location.
     * @param coordinate The new location of the unit.
     */
    public void setUnitCoordinates(int[] coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Determines if the specified incident type can be handled by this unit.
     * @param incidentType The incident type in question.
     * @return True if this unit can respond to the specified incident, otherwise false.
     */
    boolean canHandle(IncidentType incidentType) {
        return false;
    }

    /**
     * Allows the client to obtain the ID of the station that owns this unit.
     * @return The station ID that owns this unit.
     */
    public int getHomeStationId() {
        return homeStationId;
    }

    /**
     * Changes this unit's home station.
     * @param newHomeStationId The new station's id.
     */
    public void setHomeStationId(int newHomeStationId) {
        this.homeStationId = newHomeStationId;
    }

    /**
     * Allows the client to obtain the type of unit this unit is.
     * @return A string representing the unit type.
     */
    public abstract UnitType getUnitType();

    /**
     * Allows the client to obtain the ID for the incident this unit has been assigned to.
     * @return An integer representing the ID for the assigned incident.
     */
    public int getAssignedIncidentId() {
        return this.assignedIncidentId;
    }

    /**
     * Allows the client to set which incident this unit has been assigned to.
     * @param incidentId The ID of the incident that this unit has been assigned to.
     */
    public void setAssignedIncidentId(int incidentId) {
        this.assignedIncidentId = incidentId;
    }

    /**
     * Allows the client to obtain the number of ticks this unit has spent at the scene.
     * @return An integer representing the number of ticks currently spent at scene.
     */
    public int getTicksSpentAtScene() {
        return ticksSpentAtScene;
    }

    /**
     * Allows the client to increment the number of ticks this unit has currently spent at scene.
     */
    public void incrementTicksSpentAtScene() {
        this.ticksSpentAtScene++;
    }

    /**
     * Allows the client to obtain the total number of ticks this unit must spend AT_SCENE.
     * @return An integer representing the number of required ticks this unit must stay AT_SCENE.
     */
    public abstract int getTicksAtScene();
}
