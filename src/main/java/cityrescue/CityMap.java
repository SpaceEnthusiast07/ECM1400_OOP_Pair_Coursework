package cityrescue;

/**
 * CityMap contains the obstacle map used in CityRescueImpl to check which
 * locations have an obstacle and thus cannot be moved through (affecting the movement
 * logic of units). Obstacles consist of:
 * <ul>
 *     <li>Stations</li>
 *     <li>Manual obstacles called from the interface</li>
 * </ul>
 * Units are not counted as obstacles.
 * @author Cameron Russell
 * @author Jake Fordy
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
        cityGrid = new boolean[height][width];
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
     * @param x The x-coordinate of the obstacle (0-based).
     * @param y The y-coordinate of the obstacle (0-based).
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
     * @param x The x-coordinate of the obstacle (0-based).
     * @param y The y-coordinate of the obstacle (0-based).
     */
    public void removeObstacle(int x, int y) {
        cityGrid[y][x] = false;

        // Decrement the tracker for the number of obstacles in the city
        numberOfObstacles--;
    }

    /**
     * Allows the client to know if an obstacle is present at the specified location.
     * @param x The x-coordinate of the potential obstacle (0-based).
     * @param y The x-coordinate of the potential obstacle (0-based).
     * @return True meaning and obstacle is present, otherwise false.
     */
    public boolean isObstaclePresent(int x, int y) {
        return this.cityGrid[y][x];
    }

    /**
     * Allows the client to obtain the number of obstacles in the city.
     * @return An integer representing the number of obstacles in the city.
     */
    public int getNumberOfObstacles() {
        return numberOfObstacles;
    }
}
