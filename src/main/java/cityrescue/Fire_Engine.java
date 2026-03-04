package cityrescue;

import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitStatus;
import cityrescue.enums.UnitType;

/**
 * Fire_Engine is a subclass of Unit, inheriting all its attributes (basic information such
 * as ID, coordinates, the ID of its home station, its status, number of ticks currently
 * spent at scene, and its assigned incident ID) and methods (getters, setters, tick control).
 * It contains specific attributes for the fire engine's specific details (how much time it takes to
 * resolve an incident, and what type of vehicle it is). It also contains specific methods for the
 * fire engine's specific behaviour (what incident types it can handle, and getters for specific attributes)
 * @author Cameron Russell
 * @author Jake Fordy
 */
public class Fire_Engine extends Unit {
    // Time taken to resolve an incident
    private final int TICKS_AT_SCENE = 4;
    // Represents this unit's type
    private static final UnitType unitType = UnitType.FIRE_ENGINE;

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

    /**
     * Allows the client to obtain the type of unit this unit is.
     * @return A string representing the unit type.
     */
    @Override
    public UnitType getUnitType() {
        return unitType;
    }

    /**
     * Allows the client to obtain the total number of ticks a Fire_Engine must spend AT_SCENE.
     * @return An integer representing the number of required ticks a Fire_Engine must stay AT_SCENE.
     */
    @Override
    public int getTicksAtScene() {
        return TICKS_AT_SCENE;
    }
}
