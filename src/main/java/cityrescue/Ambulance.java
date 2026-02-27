package cityrescue;

import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitStatus;

/**
 * TODO: Describe this class.
 */
public class Ambulance extends Unit {
    // Time taken to resolve an incident
    private final int TICKS_AT_SCENE = 2;

    /**
     * Calls the Unit class constructor to initialise this new unit.
     * @param x x-coordinate of location.
     * @param y y-coordinate of location.
     * @param belongsToStation Which station owns this unit.
     * @param status The current state of the unit.
     */
    public Ambulance(int x, int y, int belongsToStation, UnitStatus status) {
        super(x, y, belongsToStation, status);
    }

    /**
     * Determines if the specified incident type can be handled by the Ambulance unit.
     * @param incidentType The incident type in question.
     * @return True if the incident is of type MEDICAL, otherwise false.
     */
    @Override
    boolean canHandle(IncidentType incidentType) {
        return incidentType == IncidentType.MEDICAL;
    }
}
