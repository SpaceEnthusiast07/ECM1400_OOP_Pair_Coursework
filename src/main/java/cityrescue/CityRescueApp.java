package cityrescue;

import cityrescue.enums.IncidentType;
import cityrescue.enums.UnitType;
import cityrescue.exceptions.*;

import java.util.Scanner;

/**
 * CityRescueApp is designed to demonstrate the major functionality implemented in the CityRescueImpl class.
 */
public class CityRescueApp {
    // Declare scanner object for user input
    private static Scanner scanner;

    // Declare the simulation so the outputSimStatus() function can access it
    private static CityRescueImpl sim;

    /**
     * Main entry point for the demonstration program.
     * @param args Any terminal arguments.
     */
    public static void main(String[] args) {
        // Initialise scanner object
        scanner = new Scanner(System.in);

        // Clear the terminal output
        clearTerminal();

        // Output simulation demonstration title
        System.out.println("\n=== SIMULATION DEMONSTRATION ===\n");

        // Create a new simulation
        sim = new CityRescueImpl();

        //region Initialise Simulation
        // Catch any errors at are caused by terminal arguments or the initialise function
        try {
            // Extract the terminal arguments
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);

            // Initialise the sim
            sim.initialise(8, 8);

            // Output the simulation
            System.out.println("Started simulation with a city map of size "+x+"x"+y+".");
        } catch (InvalidGridException e) {
            System.out.println("ERROR: Width or height are invalid.");
            System.exit(1);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("ERROR: Please enter a width and height to initialise the map.");
            System.exit(1);
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Width or height is not an integer.");
            System.exit(1);
        }
        //endregion

        pauseDemonstration("add 4 obstacles");

        //region Add 4 Random Obstacles
        // Add obstacle 1
        try {
            sim.addObstacle(1,5);
            System.out.println("Added: Obstacle at location (1,5).");
        } catch (InvalidLocationException e) {
            System.out.println("ERROR: Invalid location to place obstacle.");
            System.exit(1);
        }

        // Add obstacle 2
        try {
            sim.addObstacle(4,5);
            System.out.println("Added: Obstacle at location (4,5).");
        } catch (InvalidLocationException e) {
            System.out.println("ERROR: Invalid location to place obstacle.");
            System.exit(1);
        }

        // Add obstacle 3
        try {
            sim.addObstacle(0,0);
            System.out.println("Added: Obstacle at location (0,0).");
        } catch (InvalidLocationException e) {
            System.out.println("ERROR: Invalid location to place obstacle.");
            System.exit(1);
        }

        // Add obstacle 4
        try {
            sim.addObstacle(3,3);
            System.out.println("Added: Obstacle at location (3,3).");
        } catch (InvalidLocationException e) {
            System.out.println("ERROR: Invalid location to place obstacle.");
            System.exit(1);
        }
        //endregion

        pauseDemonstration("add 1 station");

        //region Add 1 Station to Sim
        // Terminates the loop once the user has input correct station information
        boolean validStation = false;
        int numberOfAttempts = 0;
        int station1Id = -1;

        // Allow the user to have more than one attempt to input the correct information
        while (!validStation && numberOfAttempts < 10) {
            // Add a station to the simulation
            try {
                numberOfAttempts++;

                // Ask user for a name for the station
                String stationName = "Station_1";    // TODO: Implement console input from the user.

                // Ask the user for the coordinates of the station
                int x = 2;    // TODO: Implement console input from user.
                int y = 3;    // TODO: Implement console input from user.

                // Add a station to the sim at coordinates (2,3)
                station1Id = sim.addStation(stationName, x, y);

                // Indicate that the user has input a valid station name and location
                validStation = true;

                // Output this station
                System.out.println("Added: Station called '"+stationName+"' at location ("+x+","+y+") with ID "+station1Id+".");
            } catch (InvalidNameException e) {
                System.out.println("ERROR: Station name is invalid.");
                System.exit(1);
            } catch (InvalidLocationException e) {
                System.out.println("ERROR: Location is either missing or out of bounds.");
                System.exit(1);
            }
        }

        // Terminate program if number of attempts is 10
        if (numberOfAttempts == 10) System.exit(1);
        //endregion

        pauseDemonstration("add 3 units");

        //region Add 3 Units to Sim
        // Add 3 units to the simulation
        try {
            // Add an Ambulance to the simulation
            int ambulanceId = sim.addUnit(station1Id, UnitType.AMBULANCE);
            // Output this new unit
            System.out.println("Added: Ambulance unit with a home station ID of "+station1Id+".");

            // Add a Police_Car to the simulation
            int policeCarId = sim.addUnit(station1Id, UnitType.POLICE_CAR);
            // Output this new unit
            System.out.println("Added: Police car unit with a home station ID of "+station1Id+".");

            // Add a Fire_Engine to the simulation
            int fireEngineId = sim.addUnit(station1Id, UnitType.FIRE_ENGINE);
            // Output this new unit
            System.out.println("Added: Fire engine unit with a home station ID of "+station1Id+".");
        } catch (IDNotRecognisedException e) {
            System.out.println("ERROR: Provided station ID doesn't exist.");
            System.exit(1);
        } catch (InvalidUnitException e) {
            System.out.println("ERROR: Unit type is not recognised.");
            System.exit(1);
        } catch (IllegalStateException e) {
            System.out.println("ERROR: Station is at capacity.");
            System.exit(1);
        }
        //endregion

        pauseDemonstration("add 3 incidents");

        //region Add 3 Incidents to Sim
        // Add a medical incident
        try {
            // Add an incident of type medical
            int medicalId = sim.reportIncident(IncidentType.MEDICAL, 3, 4, 7);
            // Output this incident
            System.out.println("Added: Medical incident with severity 3, location (4,7) and ID "+medicalId+".");
        } catch (InvalidSeverityException e) {
            System.out.println("ERROR: Medical severity must be between 1 and 5.");
        } catch (InvalidLocationException e) {
            System.out.println("ERROR: Medical location is out of bounds or an obstacle is present at this location.");
        }

        // Add a fire incident
        try {
            // Add an incident of type fire
            int fireId = sim.reportIncident(IncidentType.FIRE, 5, 0, 2);
            // Output this incident
            System.out.println("Added: Fire incident with severity 5, location (0,2) and ID "+fireId+".");
        } catch (InvalidSeverityException e) {
            System.out.println("ERROR: Fire severity must be between 1 and 5.");
            System.exit(1);
        } catch (InvalidLocationException e) {
            System.out.println("ERROR: Fire location is out of bounds or an obstacle is present at this location.");
            System.exit(1);
        }

        // Add a crime incident
        try {
            // Add an incident of type crime
            int crimeId = sim.reportIncident(IncidentType.CRIME, 2, 5, 1);
            // Output this incident
            System.out.println("Added: Crime incident with severity 2, location (5,1) and ID "+crimeId+".");
        } catch (InvalidSeverityException e) {
            System.out.println("ERROR: Crime severity must be between 1 and 5.");
            System.exit(1);
        } catch (InvalidLocationException e) {
            System.out.println("ERROR: Crime location is out of bounds or an obstacle is present at this location.");
            System.exit(1);
        }
        //endregion

        pauseDemonstration("output simulation status");

        outputSimStatus();

        pauseDemonstration("dispatch units");

        //region Dispatch Units
        sim.dispatch();
        System.out.println("Units dispatched.");
        //endregion

        pauseDemonstration("output simulation status");

        outputSimStatus();

        pauseDemonstration("advance the simulation by 1 tick");

        //region Advance Simulation by 1 Tick
        sim.tick();
        System.out.println("Simulation advanced.");
        //endregion

        pauseDemonstration("output simulation status");

        outputSimStatus();

        pauseDemonstration("skip to TICK=3 when I predict U3 to arrive at scene");

        //region Skip to TICK=3 for U3 to arrive at scene
        sim.tick();
        sim.tick();
        System.out.println("Advanced sim by 2 ticks.");
        //endregion

        pauseDemonstration("output simulation status");

        outputSimStatus();

        pauseDemonstration("skip to TICK=5 for when I predict U2 to arrive at scene");

        //region Skip to TICK=5 for U2 to arrive at scene
        sim.tick();
        sim.tick();
        System.out.println("Advanced sim by 2 ticks.");
        //endregion

        pauseDemonstration("output simulation status");

        outputSimStatus();

        pauseDemonstration("advance another tick for when I predict U1 to arrive at scene");

        //region Advance another tick for U1 to arrive at scene
        sim.tick();
        System.out.println("Advanced simulation by 1 tick.");
        //endregion

        pauseDemonstration("output simulation status");

        outputSimStatus();

        pauseDemonstration("advance sim by 1 tick for when I predict U3 to complete its incident");

        //region Advance sim by 1 tick
        sim.tick();
        System.out.println("Advanced sim by 1 tick.");
        //endregion

        pauseDemonstration("output simulation status");

        outputSimStatus();

        pauseDemonstration("advance sim by 1 tick for when I predict U1 and U2 to complete their incident");

        //region Advance sim by 1 tick
        sim.tick();
        System.out.println("Advanced sim by 1 tick.");
        //endregion

        pauseDemonstration("output simulation status");

        outputSimStatus();

        // Print a blank line
        System.out.println();
    }

    /**
     * Clears the terminal output for a clean, fresh slate.
     */
    static void clearTerminal() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Output the status of the simulation.
     */
    static void outputSimStatus() {
        System.out.println(sim.getStatus());
    }

    /**
     * Waits for the user to press enter, allowing them to explain part of the demonstration.
     */
    static void pauseDemonstration() {
        // Pauses demonstration for explanation
        System.out.print("\n\nPress enter to continue... ");
        scanner.nextLine();
    }

    /**
     * Waits for the user to press enter, allowing them to explain part of the demonstration.
     * @param message Allows the client to output a custom pause demonstration message.
     */
    static void pauseDemonstration(String message) {
        // Pauses demonstration for explanation
        System.out.print("\n\n--- Press enter to " + message + "... ");
        scanner.nextLine();
    }
}
