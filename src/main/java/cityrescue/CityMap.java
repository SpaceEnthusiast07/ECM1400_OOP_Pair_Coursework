package cityrescue;

public class CityMap {
    // Declare a 2-dimensional array to hold the city grid
    boolean[][] cityGrid;

    // Declare a variable to hold the grid width and height
    private int width;
    private int height;

    // Constructor
    public CityMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    // Returns the grid size
    public int[] getGridSize() {
        return new int[]{width, height};
    }

    // Function that adds an obstacle to the city map
    public void addObstacle(int x, int y) {
        // Set respective coordinates of the city grid to indicate an obstacle is present
        // I.e. true represents and obstacle is present
        cityGrid[y][x] = true;
    }

    // Function that removes an obstacle from the city map
    public void removeObstacle(int x, int y) {
        cityGrid[y][x] = false;
    }
}
