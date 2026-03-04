package cityrescue;

/**
 * Station contains the basic details of a specific station. It has simple
 * attributes including name, maxUnits, coordinates, id, station count and number of occupying units.
 * Its methods consist of getters/setters and unit count changers, as stations don't
 * have much behaviour on their own.
 * @author Cameron Russell
 * @author Jake Fordy
 */
public class Station {
    // Declared variable to hold station name
    private String name;
    // Variable to hold the maximum number of units at this station
    private int maxUnits;
    // Variable represents the location of this station on the city map
    private int[] coordinates;
    // Static attribute used to keep track of the number of stations
    private static int numberOfStations = 0;
    // Unique id for each station
    private final int stationId;
    // Number of units currently owned by this station
    private int numberOfUnits = 0;

    /**
     * Sets the name, location and ID for this station.
     * @param name The name for this station.
     * @param x The x-coordinate of this station.
     * @param y The y-coordinate of this station.
     */
    public Station(String name, int x, int y, int maxUnits) {
        this.name = name;
        this.coordinates = new int[]{x,y};
        this.stationId = ++numberOfStations;
        this.maxUnits = maxUnits;
    }

    /**
     * Allows client to know how many stations there are in the simulation.
     * @return The number of stations in the simulation.
     */
    public static int getNumberOfStations() {
        return numberOfStations;
    }

    /**
     * Adjusts the maximum number of units this station can own.
     * @param maxUnits The new maximum number of units. Must be greater than or equal to the current max.
     */
    public void setMaxUnits(int maxUnits) {
        this.maxUnits = maxUnits;
    }

    /**
     * Allows that client to obtain the maximum number of units this station can hold.
     * @return An integer representing the maximum number of units this station can hold.
     */
    public int getMaxUnits() {
        return this.maxUnits;
    }

    /**
     * Allows the client to obtain this station's ID.
     * @return This station's ID.
     */
    public int getStationId() {
        return this.stationId;
    }

    /**
     * Allows the client to obtain the location of this station.
     * @return The location of this station as an integer array.
     */
    public int[] getCoordinates() {
        return coordinates;
    }

    /**
     * Allows the client to obtain the current number of units this station owns.
     * @return The number of units in this station.
     */
    public int getNumberOfUnits() {
        return this.numberOfUnits;
    }

    /**
     * Allows the client to adjust the number of units this station owns.
     * @param newNumberOfUnits The new number of units this station owns.
     */
    public void setNumberOfUnits(int newNumberOfUnits) {
        this.numberOfUnits = newNumberOfUnits;
    }

    /**
     * Decrements the number of units this station owns. Used when transferring a unit
     * from one station to another.
     */
    public void decrementNumberOfUnits() {
        this.numberOfUnits--;
    }

    /**
     * Increments the number of units this station owns. Used when transferring a unit
     * from one station to another.
     */
    public void incrementNumberOfUnits() {
        this.numberOfUnits++;
    }
}
