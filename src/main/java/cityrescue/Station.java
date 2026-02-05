package cityrescue;

public class Station {
    private String name;
    final private int maxUnits = 50;
    private int[] coords = new int[2];

    private static int numberOfStations = 0;
    private int stationId;



    public Station(String name, int x, int y) {
        this.name = name;
        this.coords = new int[] {x,y};
        this.stationId = ++numberOfStations;
    }

    public void setStationCapacity(int stationId, int maxUnits) {

    }

    public int getStationId() {
        return this.stationId;
    }
}
