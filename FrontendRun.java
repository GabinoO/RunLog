import java.io.FileNotFoundException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * This class acts as an interactive interface so a user can start their Running Log
 */
public class FrontendRun {
  Scanner input; 
  RunStorage runCollection;

  FrontendRun(Scanner scnr, RunStorage myCollection) {
    input = scnr;
    runCollection = myCollection;
  }

  /**
   * This method displays the main menu options for the user
   */
  public void DisplayOptions() {
    System.out.println();
    System.out.println("Enter any of the following commands to begin your Running Log");
    System.out.println("[Load]: Load Your Running File");
    System.out.println("[Fast]: Get Your Fastest Run");
    System.out.println("[Long]: Get Your Longest Run");
    System.out.println("[Display]: Display your 7 most recent Runs");
    System.out.println("[Add]: Add a Run in Log");
    System.out.println("[Delete]: Delete a Run in Log");
    System.out.println("[Quit]: Quit the program");
    System.out.println();
  }

  /**
   * This method takes in user input and calls the appropriate method
   */
  public void loopUI() {

    boolean done = false;
    // start the loop for user interaction
    while (!done) {
    // will display the menu
    this.DisplayOptions();
      String command = input.nextLine().trim().toUpperCase();
      switch(command) {
        case "LOAD":
          this.loadUserFile();
          break;

        case "FAST":
          this.fastestRun();
          break;

        case "LONG":
          this.longestRun();
          break;

        case "DISPLAY":
          this.displayTop7();
          break;
        
        case "ADD":
        this.addUserRun();
          break;

        case "DELETE":
          break;

        case "QUIT":
          done = true;
          break;

        default:
          System.out.println("Invalid command. Please try again.");
      }
    }
  }

  /**
   * This method calls the runStorage class to read in the user's file of Run data
   */
  public void loadUserFile() {
    System.out.println("Please provide the file path for the desired file or \"Quit\" to exit and go to main menu ");
    boolean done = false;

    // loop to read in user's file
    while (!done) {
      String fileName = input.nextLine().trim();

      // if user wants to quit
      if (fileName.equalsIgnoreCase("Quit")) {
        done = true;
        continue;
      }
      // read in file
      try {
        runCollection.readFile(fileName);
        System.out.println("File has been successfully loaded!");
        done = true;
        continue;
      }
      // will re-prompt to put in file again or quit
      catch(FileNotFoundException e) {
        System.out.println("Invalid file path. Please re-enter another file name or \"Quit\" to exit to main menu");
      }
    }
  }

  /**
   * This method displays the users fastest run
   */
  public void fastestRun() {
    System.out.println("Your Fastest run in your Running Log is:");
    System.out.println(this.runCollection.getFastest() + "\n");
  }

  /**
   * This method displays the users longest run
   */
  public void longestRun() {
    System.out.println("Your Longest run in your Running Log is:");
    System.out.println(this.runCollection.getLongest());
  }

  /**
   * This method displays the 7 most recent Runs for the user
   */
  public void displayTop7() {
    RunStorage display = new RunStorage();
    // save top 7 songs into variable and print them
    display = this.runCollection.getLatest7();
    System.out.println(display);
    System.out.println("\n");

  }

  /**
   * this method adds a run to the users running log / collection 
   */
  public void addUserRun() {
    Double distance = 0.00;
    String time = "";
    String date = "";
    System.out.println("Please provide the required information to add a run to your log");

    // prompts for distance verifies
    System.out.println("Provide distance of the run: Ex. 4.13");
    if (input.hasNextLine()) {
      String potentialNumber = input.nextLine().trim();
      // calls private method to verify the input is a type double
      if (this.numIsValid(potentialNumber)) {
        distance  = Double.valueOf(potentialNumber);
      }
      else {
        System.out.println("Not a valid distance");
        return; // returns to main menu
      }
    }
    
    // prompts for time
    System.out.println("Provide time of the run: Ex. 00:42:14");
    if (input.hasNextLine()) {
      time = input.nextLine().trim();
    }
    else {
      System.out.println("Not a valid time");
      return; // returns to main menu
    }

    // prompts for date if they want to, if not it will default to current date
    System.out.println("OPTIONAL: Provide a specific date. If not press S for \"Skip\" and the run will " + 
    "default to todays date. Ex. 2024-11-26");

    // checks if input is "skip" --> make a Run object with todays' date
    if (input.hasNextLine()) {
      date = input.nextLine().trim();
      if (date.equalsIgnoreCase("s")) { // it will make a run with just distance and time
        Run toAdd = null;
        try {
           toAdd = new Run(distance, time);
        }
        catch(IllegalArgumentException e) {
          e.getMessage();
          System.out.println("Invalid arguement. Run was not added to log");
          return;
        }
        catch(DateTimeParseException e) {
          e.getMessage();
          System.out.println("Invalid arguement. Run was not added to log");
          return;
        }
        this.runCollection.addRun(toAdd);
        System.out.println("Run has been added to your log!");
        return; // run added, now return to menu
      }

      // else --> make a Run object with passed in date
      else {
        Run toAdd = null;
        try {
          toAdd = new Run(distance, time, date);
        }
        catch(IllegalArgumentException e) {
          e.getMessage();
          System.out.println("Invalid arguement. Run was not added to log");
          return;
        }
        catch(DateTimeParseException e) {
          e.getMessage();
          System.out.println("Invalid arguement. Run was not added to log");
          return;
        }
        this.runCollection.addRun(toAdd);
        System.out.println("Run has been added to your log!");
        return; // run added, now return to menu
      }
    }
    
  }

  /**
   *  This method verifys the passed in string is a double variable
   * @param num the string to verify
   * @return true if it is a double, false otherwise
   */
  private boolean numIsValid(String num) {
    try {
      Double.parseDouble(num);
    }
    catch(NumberFormatException e) { // is this exception was thrown than the string cannot be a double
      return false;
    }
    return true;
  }


}
