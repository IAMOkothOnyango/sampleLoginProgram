import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SampleLogIn {

    // A constant for the name of the file that stores the user credentials
    public static final String FILE_NAME = "users.txt";

    public static void main(String[] args) {
        // Check if the users.txt file exists, create it if it doesn't
        createFileIfNotExists();

        // Create a scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Display a menu with two options: register or login
        System.out.println("Welcome to the LogIn program!");
        System.out.println("Please choose an option:");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");

        // Read the user's choice
        int choice = scanner.nextInt();

        // Perform the corresponding action based on the user's choice
        switch (choice) {
            case 1:
                // Register a new user
                register(scanner);
                break;
            case 2:
                // Login an existing user
                login(scanner);
                break;
            case 3:
                // Exit the program
                System.out.println("Thank you for using the LogIn program!");
                break;
            default:
                // Invalid choice
                System.out.println("Invalid option. Please try again.");
                break;
        }

        // Close the scanner object
        scanner.close();
    }

    // A method that registers a new user by asking for a username and a password, and storing them in a file
    public static void register(Scanner scanner) {
        // Ask the user for a username
        System.out.println("Please enter a username:");
        String username = scanner.next();

        // Check if the username already exists in the file
        if (checkUsername(username)) {
            // Username already exists, ask the user to choose another one
            System.out.println("Sorry, this username is already taken. Please choose another one.");
            register(scanner);
        } else {
            // Username is available, ask the user for a password
            System.out.println("Please enter a password:");
            String password = scanner.next();

            // Store the username and the password in the file, separated by a comma
            try {
                // Create a file writer object to append to the file
                FileWriter writer = new FileWriter(FILE_NAME, true);

                // Write the username and the password to the file, followed by a new line
                writer.write(username + "," + password + "\n");

                // Close the file writer object
                writer.close();

                // Display a success message
                System.out.println("You have successfully registered!");
            } catch (IOException e) {
                // Print the details of the exception
                e.printStackTrace();
                // An error occurred while writing to the file
                System.out.println("An error occurred while registering. Please try again.");
            }
        }
    }

    // A method that logs in an existing user by asking for a username and a password, and verifying them with the file
    public static void login(Scanner scanner) {
        // Ask the user for a username
        System.out.println("Please enter your username:");
        String username = scanner.next();

        // Check if the username exists in the file
        if (checkUsername(username)) {
            // Username exists, ask the user for a password
            System.out.println("Please enter your password:");
            String password = scanner.next();

            // Check if the password matches the username in the file
            if (checkPassword(username, password)) {
                // Password matches, display a success message
                System.out.println("You have successfully logged in!");
            } else {
                // Password does not match, display an error message
                System.out.println("Incorrect password. Please try again.");
            }
        } else {
            // Username does not exist, display an error message
            System.out.println("This username does not exist. Please register first.");
        }
    }

    // A method that checks if a given username exists in the file
    public static boolean checkUsername(String username) {
        // Create a boolean variable to store the result
        boolean exists = false;

        try {
            // Create a file reader object to read from the file
            FileReader reader = new FileReader(FILE_NAME);

            // Create a buffered reader object to read line by line
            BufferedReader buffer = new BufferedReader(reader);

            // Read the first line from the file
            String line = buffer.readLine();

            // Loop through the file until the end or the username is found
            while (line != null && !exists) {
                // Split the line by the comma to get the username and the password
                String[] tokens = line.split(",");

                // Check if the username matches the given username
                if (tokens[0].equals(username)) {
                    // Username matches, set the result to true
                    exists = true;
                }

                // Read the next line from the file
                line = buffer.readLine();
            }

            // Close the buffered reader and the file reader objects
            buffer.close();
            reader.close();
        } catch (IOException e) {
            // Print the details of the exception
            e.printStackTrace();
            // An error occurred while reading from the file
            System.out.println("An error occurred while checking the username. Please try again.");
        }

        // Return the result
        return exists;
    }

    // A method that checks if a given password matches a given username in the file
    public static boolean checkPassword(String username, String password) {
        // Create a boolean variable to store the result
        boolean matches = false;

        try {
            // Create a file reader object to read from the file
            FileReader reader = new FileReader(FILE_NAME);

            // Create a buffered reader object to read line by line
            BufferedReader buffer = new BufferedReader(reader);

            // Read the first line from the file
            String line = buffer.readLine();

            // Loop through the file until the end
            while (line != null) {
                // Split the line by the comma to get the username and the password
                String[] tokens = line.split(",");

                // Check if the username and the password match the given username and password
                if (tokens[0].equals(username) && tokens[1].equals(password)) {
                    // Username and password match, set the result to true
                    matches = true;
                    break; // No need to continue searching
                }

                // Read the next line from the file
                line = buffer.readLine();
            }

            // Close the buffered reader and the file reader objects
            buffer.close();
            reader.close();
        } catch (IOException e) {
            // Print the details of the exception
            e.printStackTrace();
            // An error occurred while reading from the file
            System.out.println("An error occurred while checking the password. Please try again.");
        }

        // Return the result
        return matches;
    }

    // A method that creates the users.txt file if it doesn't exist
    public static void createFileIfNotExists() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File 'users.txt' created.");
            }
        } catch (IOException e) {
            // Print the details of the exception
            e.printStackTrace();
            System.out.println("An error occurred while creating the file 'users.txt'.");
        }
    }
}
