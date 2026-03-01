package cityrescue;

import cityrescue.enums.IncidentStatus;
import cityrescue.enums.IncidentType;

/**
 * TODO: Describe this class.
 */
public class Incident {
    // Location of the incident
    private int[] incidentLocation;
    // Severity of the incident, 1-5
    private int incidentSeverity;
    // Status of the incident
    private IncidentStatus incidentStatus;
    // Type of incident
    private IncidentType incidentType;
    // ID of this incident
    private int incidentId;
    // Number of incidents reported since the first simulation start up
    private static int numberOfIncidents = 0;
    // The ID of the unit assigned to this incident
    private int assignedUnitId;

    /**
     * Sets up a new incident.
     * @param incidentType The type of incident reported.
     * @param incidentSeverity How severe an incident is.
     * @param incidentStatus The progress status of this incident.
     * @param x x-coordinate of the incident.
     * @param y y-coordinate of the incident.
     */
    public Incident(IncidentType incidentType, int incidentSeverity, IncidentStatus incidentStatus, int x, int y) {
        this.incidentType = incidentType;
        this.incidentSeverity = incidentSeverity;
        this.incidentStatus = incidentStatus;
        this.incidentLocation = new int[]{x,y};
        this.incidentId = ++numberOfIncidents;
    }

    /**
     * Allows the client to obtain the location of this incident.
     * @return An integer array representing the incident location.
     */
    public int[] getIncidentLocation() {
        return this.incidentLocation;
    }

    /**
     * Allows the client to update this incident's location.
     * @param x New x-coordinate of this incident.
     * @param y New y-coordinate of this incident.
     */
    public void setIncidentLocation(int x, int y) {
        this.incidentLocation[0] = x;
        this.incidentLocation[1] = y;
    }

    /**
     * Allows the client to obtain this incident's severity.
     * @return An integer representing the severity.
     */
    public int getIncidentSeverity() {
        return this.incidentSeverity;
    }

    /**
     * Allows the client to update this incident's severity.
     * @param newSeverity The updated severity of this incident.
     */
    public void setIncidentSeverity(int newSeverity) {
        this.incidentSeverity = newSeverity;
    }

    /**
     * Allows the client to obtain this incident's status.
     * @return IncidentStatus enum constant representing this incident's status.
     */
    public IncidentStatus getIncidentStatus() {
        return this.incidentStatus;
    }

    /**
     * Allows the client to update this incident's status.
     * @param newStatus The new IncidentStatus enum constant for this incident.
     */
    public void setIncidentStatus(IncidentStatus newStatus) {
        this.incidentStatus = newStatus;
    }

    /**
     * Allows the client to obtain this incident's type.
     * @return An IncidentType enum constant representing this incident's type.
     */
    public IncidentType getIncidentType() {
        return this.incidentType;
    }

    /**
     * Allows the client to change this incident's type.
     * @param newIncidentType A new IncidentType enum constant.
     */
    public void setIncidentType(IncidentType newIncidentType) {
        this.incidentType = newIncidentType;
    }

    /**
     * Allows the client to obtain this incident's ID.
     * @return An integer representing this incident's ID.
     */
    public int getIncidentId() {
        return this.incidentId;
    }

    /**
     * Allows the client to obtain the total number of incidents created since the
     * simulation first started.
     * @return An integer representing the total number of incidents ever created.
     */
    public int getNumberOfIncidents() {
        return numberOfIncidents;
    }

    /**
     * Allows the client to obtain the unit's ID that is assigned to this incident.
     * @return An integer representing the unit's ID.
     */
    public int getAssignedUnitId() {
        return this.assignedUnitId;
    }

    /**
     * Allows the client to set which unit is assigned to this incident.
     * @param unitId The unit to assign to this incident.
     */
    public void setAssignedUnitId(int unitId) {
        this.assignedUnitId = unitId;
    }
}
