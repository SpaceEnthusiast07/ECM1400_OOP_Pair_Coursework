package cityrescue;

import cityrescue.enums.IncidentType;

public class Police_Car extends Unit {
    // Time taken to resolve an incident
    private final int TICKS_AT_SCENE = 3;

    // Constructor
    public Police_Car(int x, int y, int belongsToStation) {
        super(x, y, belongsToStation);
    }

    // Checks whether this unit type can handle is type of incident
    @Override
    boolean canHandle(IncidentType incidentType) {
        return incidentType == IncidentType.CRIME;
    }
}
