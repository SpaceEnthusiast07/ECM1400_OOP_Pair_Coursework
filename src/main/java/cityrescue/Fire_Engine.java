package cityrescue;

import cityrescue.enums.IncidentType;

public class Fire_Engine extends Unit {
    // Time taken to resolve an incident
    private final int TICKS_AT_SCENE = 4;

    // Constructor
    public Fire_Engine(int x, int y, int belongsToStation) {
        super(x, y, belongsToStation);
    }

    // Checks whether this unit type can handle is type of incident
    @Override
    boolean canHandle(IncidentType incidentType) {
        return incidentType == IncidentType.FIRE;
    }
}
