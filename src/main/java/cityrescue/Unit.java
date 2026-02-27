package cityrescue;

import cityrescue.enums.IncidentType;

public abstract class Unit {
    // Declare a variable to hold the unit id
    private final int unitId;
    // Location for unit
    private int[] coordinate;
    // Number of units currently in existence
    private static int numberOfUnits;
    // Indicates which station this unit belongs to
    private int belongsToStation;

    // Constructor
    public Unit(int x, int y, int belongsToStation) {
        // Give this new station an ID
        this.unitId = ++numberOfUnits;
        // Set the new units location
        this.coordinate = new int[] {x, y};
        // Indicate which station it belongs to
        this.belongsToStation = belongsToStation;
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

    // Getter method for the belongsToStation field
    public int getBelongsToStation() {
        return belongsToStation;
    }
}
