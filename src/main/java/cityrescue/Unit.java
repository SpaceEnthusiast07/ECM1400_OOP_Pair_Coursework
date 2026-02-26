package cityrescue;

import cityrescue.enums.IncidentType;

public abstract class Unit {
    // Declare a variable to hold the unit id
    private final int unitId;
    // Location for unit
    private int[] coordinate;
    // Number of units currently in existence
    private static int numberOfUnits;

    // Constructor
    public Unit() {
        this.unitId = ++numberOfUnits;
    }

    // Getter method for unit id
    public int getUnitId() {
        return unitId;
    }

    // Getter method for unit location
    public int[] getUnitCoordinates() {
        return coordinate;
    }

    // Checks whether this unit type can handle this type of incident
    boolean canHandle(IncidentType incidentType) {
        return false;
    }
}
