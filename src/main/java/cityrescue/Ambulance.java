package cityrescue;

import cityrescue.enums.IncidentType;

public class Ambulance extends Unit{
    // Time taken to resolve an incident
    private final int TICKS_AT_SCENE = 2;

    // Checks whether this unit type can handle is type of incident
    @Override
    boolean canHandle(IncidentType incidentType) {
        return incidentType == IncidentType.MEDICAL;
    }
}
