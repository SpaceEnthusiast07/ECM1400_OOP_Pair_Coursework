package cityrescue;

import cityrescue.exceptions.*;

/**
 * CityRescueApp is designed to demonstrate all the functionality implemented in the CityRescueImpl class.
 */
public class CityRescueApp {
    /**
     * Main entry point for the demonstration program.
     * @param args Any terminal arguments.
     */
    public static void main(String[] args) {
        // Create a new simulation
        CityRescueImpl sim = new CityRescueImpl();

        // Catch any errors at are caused by terminal arguments or the initialise function
        try {
            // Extract the terminal arguments
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            // Initialise the sim
            sim.initialise(8, 8);
        } catch (InvalidGridException e) {
            System.out.println("Width or height are invalid.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Please enter a width and height to initialise the map.");
        } catch (NumberFormatException e) {
            System.out.println("Width or height is not an integer.");
        }

        try {
            // Add a station to the sim at coordinates (3,4)
            int station1ID = sim.addStation("Station_1", 3, 4);
        } catch (InvalidNameException e) {
            System.out.println(...);
        }
    }
}
