package cityrescue;

public class Station {
    // Declared variable to hold station name
    private String name;
    // Variable to hold the maximum number of units at this station
    private int maxUnits = 50;
    // Variable represents the location of this station on the city map
    private int[] coordinates = new int[2];
    // Constant used to keep track of the number of stations
    private static int numberOfStations = 0;
    // Unique id for each station
    private final int stationId;

    // Constructor
    public Station(String name, int x, int y) {
        this.name = name;
        this.coordinates = new int[] {x,y};
        this.stationId = ++numberOfStations;
    }

    // Sets the maximum number of units this station can hold
    public void setStationCapacity(int maxUnits) {
        this.maxUnits = maxUnits;
    }

    // Returns this station's id
    public int getStationId() {
        return this.stationId;
    }

    // Getter function for this station's location
    public int[] getCoordinates() {
        return coordinates;
    }
}
