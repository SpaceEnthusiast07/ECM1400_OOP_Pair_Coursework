package cityrescue;

/**
 * TODO: Describe this class.
 */
public class CityMap {
    // Declare a 2-dimensional array to hold the city grid
    private boolean[][] cityGrid;

    // Declare a variable to hold the grid width and height
    private final int width;
    private final int height;

    // Keeps track of the number of obstacles in the city
    private int numberOfObstacles;

    /**
     * Sets up a new city map.
     * @param width The width of the new city map.
     * @param height The height of the new city map.
     */
    public CityMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Allows the client to obtain the grid size of this city map.
     * @return An integer array representing the city's size.
     */
    public int[] getGridSize() {
        return new int[]{width, height};
    }

    /**
     * Adds an obstacle to the city by marking the provided coordinate as 'true'.
     * @param x The x-coordinate of the obstacle.
     * @param y The y-coordinate of the obstacle.
     */
    public void addObstacle(int x, int y) {
        // Set respective coordinates of the city grid to indicate an obstacle is present
        // I.e. true represents and obstacle is present
        cityGrid[y][x] = true;

        // Increment the tracker for the number of obstacles in the city
        numberOfObstacles++;
    }

    /**
     * Removes an obstacle from the city by marking the provided coordinate as 'false'.
     * @param x The x-coordinate of the obstacle.
     * @param y The y-coordinate of the obstacle.
     */
    public void removeObstacle(int x, int y) {
        cityGrid[y][x] = false;

        // Decrement the tracker for the number of obstacles in the city
        numberOfObstacles--;
    }

    /**
     * Allows the client to know if an obstacle is present at the specified location.
     * @param x The x-coordinate of the potential obstacle.
     * @param y The x-coordinate of the potential obstacle.
     * @return True meaning and obstacle is present, otherwise false.
     */
    public boolean isObstaclePresent(int x, int y) {
        return cityGrid[y][x];
    }

    /**
     * Allows the client to obtain the number of obstacles in the city.
     * @return An integer representing the number of obstacles in the city.
     */
    public int getNumberOfObstacles() {
        return numberOfObstacles;
    }
}
