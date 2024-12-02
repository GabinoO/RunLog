import java.io.FileNotFoundException;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
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
   * Page (1) This method displays the main menu options which dictates whether the user would like to:
   * (1A) Display information about their log
   * (1B) Access / update information about their log
   */
  public void mainMenuOptions() {
    System.out.println();
    System.out.println("--------------------- Main Menu ---------------------");
    System.out.println("What would you like to do to your Running Log");
    System.out.println("[Load]: Load Your Running File");
    System.out.println("[Display]: Display Information from your Running Log");
    System.out.println("[Edit]: Edit your Running Log");
    System.out.println("[Quit]: Quit the program");
    System.out.println();
  }

  /**
   * Page (1A) 
   * This method displays commands for displaying information from their Running Log
   */
  public void DisplayLogInfo() {
    System.out.println();
    System.out.println("--------------------- Display Log ---------------------");
    System.out.println("Enter any of the following commands to display key information");
    System.out.println("[Fast]: Get Your Fastest Run");
    System.out.println("[Long]: Get Your Longest Run");
    System.out.println("[Recent]: Display your 7 most recent Runs");
    System.out.println("[Quit]: Go back to main menu");
    System.out.println();
  }

  /**
   * Page (1B)
   * This method displays commands for editing information from their Running Log
   */
  public void DisplayRunEdits() {
    System.out.println();
    System.out.println("--------------------- Edit Log ---------------------");
    System.out.println("Enter any of the following commands to edit your Running Log");
    System.out.println("[Add]: Add a Run in Log");
    System.out.println("[Delete]: Delete a Run in Log");
    System.out.println("[Set Title]: Set the title of a run");
    System.out.println("[Quit]: Go back to main menu");
    System.out.println();
  }



  /**
   * (1)
   * This method takes in commands when a user from the main menu options
   */
  public void loopMainMenu() {
    boolean done = false;
    // start the loop for user interaction
    while (!done) {
    // will display the menu
    this.mainMenuOptions();
      String command = input.nextLine().trim().toUpperCase();
      switch(command) {
        case "LOAD":
          this.loadUserFile();
          break;

        case "DISPLAY":
          this.loopDisplayInfo();
          break;

        case "EDIT" :
          this.loopEditLog();
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
   * (1A) 
   * This method takes in commands from Display List Options
   */
  public void loopDisplayInfo() {
    boolean done = false;
    // start the loop for user interaction
    while (!done) {
    // will display the menu
    this.DisplayLogInfo();
      String command = input.nextLine().trim().toUpperCase();
      switch(command) {
        case "FAST":
          this.fastestRun();
          break;

        case "LONG":
          this.longestRun();
          break;

        case "RECENT": 
          this.displayTop7();
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
   * (1B)
   * This method takes in commands from the user to edit their Running Log
   */
  public void loopEditLog() {
    boolean done = false;
    // start the loop for user interaction
    while (!done) {
    // will display the menu
    this.DisplayRunEdits();
      String command = input.nextLine().trim().toUpperCase();
      switch(command) {
        case "ADD":
        this.addUserRun();
          break;

        case "DELETE":
          this.removeRun();
          break;

        case "SET TITLE":
          this.setTitle();
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
   * (1A)
   * This method displays the users fastest run
   */
  public void fastestRun() {
    if (this.logIsEmpty()) {
      System.out.println("You have no runs stored in this log. " + 
      "Please store runs before accessing information from your running log");
      return;
    }
    System.out.println("Your Fastest run in your Running Log is:");
    Run temp = this.runCollection.getFastest();
    if (temp == null) {
      System.out.println("This collection is empty");
    }
    else {
      System.out.println(this.runCollection.getFastest() + "\n");
    }
  }

  /**
   * (1A)
   * This method displays the users longest run
   */
  public void longestRun() {
    if (this.logIsEmpty()) {
      System.out.println("You have no runs stored in this log. " + 
      "Please store runs before accessing information from your running log");
      return;
    }
    System.out.println("Your Longest run in your Running Log is:");
    Run temp = this.runCollection.getLongest();
    if (temp == null) {
      System.out.println("This collection is empty");
    }
    else {
      System.out.println(this.runCollection.getLongest());
    } 
  }

  /**
   * (1A)
   * This method displays the 7 most recent Runs for the user
   */
  public void displayTop7() {
    if (this.logIsEmpty()) {
      System.out.println("You have no runs stored in this log. " + 
      "Please store runs before accessing information from your running log");
      return;
    }
    RunStorage display = new RunStorage();
    // save top 7 songs into variable and print them
    display = this.runCollection.getLatest7();
    System.out.println(display);
    System.out.println("\n");

  }

  /**
   * This private method helps all the 1B pages by finding the run based on the passed in date and returns the matching run 
   * so it can be altered
   * @return the desired run or null is no run was found
   */
  private Run findRun() {
    if (this.logIsEmpty()) {
      System.out.println("You have no runs stored in this log. " + 
      "Please store runs before accessing information from your running log");
      return null;
    }
    System.out.println("provide the date of the run you would like to edit");
    String date = "";
    if (input.hasNextLine()) {
      date = input.nextLine().trim();
      try {
        Run toReturn = runCollection.getRun(date);
        return toReturn;
      }
      catch(NoSuchElementException e)  {
        System.out.println("Run was not found, Please try again.");
        return null;
      }
    }
    return null;
  }

  /**
   * (1B)
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
   * (1B)
   * This method removes a specified run from the users collection / log
   */
  public void removeRun() {
    Run toRemove = this.findRun(); // private method to prompt for a run and finds the run 
    if (toRemove == null) {
      return;
    }
    Run runRemoved = this.runCollection.removeRun(toRemove);
    System.out.println("The following run was removed\n" + runRemoved);
  }

  /**
   * (1B)
   * This method sets the title of the passed in run. The title will be set
   * to the Run that matches the passed in time and sets the title to the input provided by the user
   */
  public void setTitle() {
    Run toEdit = this.findRun();
    if (toEdit == null) {
      return;
    }
    // update the description
    System.out.println("Enter the updated title of the run:");
    if (input.hasNextLine()) {
      String title = input.nextLine();
      toEdit.setTitle(title);
      System.out.println("The following Run has been edited" + toEdit + "\n");
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

  /**
   * This method tells us wether the running log has any runs present or not
   * @return true if log is empty, false otherwise
   */
  private boolean logIsEmpty() {
    if (this.runCollection.getSize() == 0) {
      return true;
    }
    return false;
  }

}
