package cityrescue;

import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitStatus;

/**
 * TODO: Describe this class.
 */
public class Fire_Engine extends Unit {
    // Time taken to resolve an incident
    private final int TICKS_AT_SCENE = 4;

    /**
     * Calls the Unit class constructor to initialise this new unit.
     * @param x x-coordinate of location.
     * @param y y-coordinate of location.
     * @param homeStationId Which station owns this unit.
     * @param status The current state of the unit.
     */
    public Fire_Engine(int x, int y, int homeStationId, UnitStatus status) {
        super(x, y, homeStationId, status);
    }

    /**
     * Determines if the specified incident type can be handled by the Fire_Engine unit.
     * @param incidentType The incident type in question.
     * @return True if the incident is of type FIRE, otherwise false.
     */
    @Override
    boolean canHandle(IncidentType incidentType) {
        return incidentType == IncidentType.FIRE;
    }
}
