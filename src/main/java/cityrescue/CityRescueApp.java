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
            System.out.println("Started simulation with a city map of size "+x+"x"+y+".\n");
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

        pauseDemonstration();

        //region Add 3 Random Obstacles
        // Add obstacle 1
        try {
            sim.addObstacle(2,6);
            System.out.println("Added: Obstacle at location (2,6).\n");
        } catch (InvalidLocationException e) {
            System.out.println("ERROR: Invalid location to place obstacle.");
        }

        // Add obstacle 2
        try {
            sim.addObstacle(5,6);
            System.out.println("Added: Obstacle at location (5,6).\n");
        } catch (InvalidLocationException e) {
            System.out.println("ERROR: Invalid location to place obstacle.");
        }

        // Add obstacle 3
        try {
            sim.addObstacle(1,1);
            System.out.println("Added: Obstacle at location (1,1).\n");
        } catch (InvalidLocationException e) {
            System.out.println("ERROR: Invalid location to place obstacle.");
        }
        //endregion

        pauseDemonstration();

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
                int x = 3;    // TODO: Implement console input from user.
                int y = 4;    // TODO: Implement console input from user.

                // Add a station to the sim at coordinates (3,4)
                station1Id = sim.addStation(stationName, 3, 4);

                // Indicate that the user has input a valid station name and location
                validStation = true;

                // Output this station
                System.out.println("Added: Station called '"+stationName+"' at location ("+x+","+y+") with ID "+station1Id+".\n");
            } catch (InvalidNameException e) {
                System.out.println("ERROR: Station name is invalid.");
            } catch (InvalidLocationException e) {
                System.out.println("ERROR: Location is either missing or out of bounds.");
            }
        }

        // Terminate program if number of attempts is 10
        if (numberOfAttempts == 10) System.exit(1);
        //endregion

        pauseDemonstration();

        //region Add 3 Units to Sim
        // Add 3 units to the simulation
        try {
            // Add an Ambulance to the simulation
            int ambulanceId = sim.addUnit(station1Id, UnitType.AMBULANCE);
            // Output this new unit
            System.out.println("Added: Ambulance unit with a home station ID of "+station1Id+".\n");

            // Add a Fire_Engine to the simulation
            int fireEngineId = sim.addUnit(station1Id, UnitType.FIRE_ENGINE);
            // Output this new unit
            System.out.println("Added: Fire engine unit with a home station ID of "+station1Id+".\n");

            // Add a Police_Car to the simulation
            int policeCarId = sim.addUnit(station1Id, UnitType.POLICE_CAR);
            // Output this new unit
            System.out.println("Added: Police car unit with a home station ID of "+station1Id+".\n");
        } catch (IDNotRecognisedException e) {
            System.out.println("ERROR: Provided station ID doesn't exist.");
        } catch (InvalidUnitException e) {
            System.out.println("ERROR: Unit type is not recognised.");
        } catch (IllegalStateException e) {
            System.out.println("ERROR: Station is at capacity.");
        }
        //endregion

        pauseDemonstration();

        //region Add 3 Incidents to Sim
        // Add a medical incident
        try {
            // Add an incident of type medical
            int medicalId = sim.reportIncident(IncidentType.MEDICAL, 3, 5, 8);
            // Output this incident
            System.out.println("Added: Medical incident with severity 3, location (5,8) and ID "+medicalId+".\n");
        } catch (InvalidSeverityException e) {
            System.out.println("ERROR: Medical severity must be between 1 and 5.");
        } catch (InvalidLocationException e) {
            System.out.println("ERROR: Medical location is out of bounds or an obstacle is present at this location.");
        }

        // Add a fire incident
        try {
            // Add an incident of type fire
            int fireId = sim.reportIncident(IncidentType.FIRE, 5, 1, 3);
            // Output this incident
            System.out.println("Added: Fire incident with severity 5, location (1,3) and ID "+fireId+".\n");
        } catch (InvalidSeverityException e) {
            System.out.println("ERROR: Fire severity must be between 1 and 5.");
        } catch (InvalidLocationException e) {
            System.out.println("ERROR: Fire location is out of bounds or an obstacle is present at this location.");
        }

        // Add a crime incident
        try {
            // Add an incident of type crime
            int crimeId = sim.reportIncident(IncidentType.CRIME, 2, 6, 2);
            // Output this incident
            System.out.println("Added: Crime incident with severity 2, location (6,2) and ID "+crimeId+".\n");
        } catch (InvalidSeverityException e) {
            System.out.println("ERROR: Crime severity must be between 1 and 5.");
        } catch (InvalidLocationException e) {
            System.out.println("ERROR: Crime location is out of bounds or an obstacle is present at this location.");
        }
        //endregion

        pauseDemonstration();

        outputSimStatus();

        pauseDemonstration();

        //region Dispatch Units
        System.out.println("Dispatch Units:");
        sim.dispatch();
        System.out.println("Units Dispatched.\n");
        //endregion

        pauseDemonstration();

        outputSimStatus();

        pauseDemonstration();

        //region Advance Simulation by 1 Tick
        System.out.println("Advance Simulation by 1 Tick:");
        sim.tick();
        System.out.println("Simulation Advanced.\n");
        //endregion

        pauseDemonstration();

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
        System.out.println("Simulation Status:");
        System.out.println(sim.getStatus());
    }

    /**
     * Waits for the user to press the space bar, allowing them to explain part of the demonstration.
     */
    static void pauseDemonstration() {
        // Pauses demonstration for explanation
        System.out.print("Press enter to continue... ");
        scanner.nextLine();
        System.out.println();
        System.out.println();
    }
}
