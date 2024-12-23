import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.time.LocalDate;


/**
 * This class defines the Run Object
 */
public class Run implements Comparable<Run>{
  
  String title; // title of run
  String location; // location of run
  String paceOfRun; // pace of the run
  String description; // description of the run
  double distance; // total distance of the run
  int rating; // total rating of the run
  LocalTime timeRan; // time spent running
  LocalDate runDate; // the date of the run
  
  /**
   * Constructor for Run Object. Will need the distance and time of the run. Will Use this information for some field varaibles:
   * distance 
   * timeRan
   * paceOfRun
   * @param distance the distance of this run
   * @param time the total time it took for this run
   */
  Run(Double distance, String time) {
    int[] arrayOfTimes = new int[3];
    
    // checks for any errors with arguments
    if (distance == null || (distance - 0.0) < 0.001 ) {
      throw new IllegalArgumentException("Invalid time, either null or negative");
    }

    this.verifyTime(time); // private method to verify the format of time is correct
    
    // saves the runs' distance, time of run, date of run, pace of run
    this.distance = distance;
    arrayOfTimes = this.stringToTime(time);
    this.timeRan = LocalTime.of(arrayOfTimes[0], arrayOfTimes[1], arrayOfTimes[2]);
    this.runDate = LocalDate.now();
    this.paceOfRun = this.calculatePace();

    // initalize remaining fields
    title = null;
    location = null;
    rating = 0;
    description = null;

  }

  /**
   * Constructor for Run Object. Will need the distance, time, date of the run. Will Use this information for some field varaibles:
   * distance 
   * timeRan
   * paceOfRun
   * runDate
   * 
   * @param distance the total distance of the run
   * @param time the total time of the run
   * @param runDate the date of the run 
   */
  Run(Double distance, String time, String dateOfRun) {
    int[] arrayOfTimes = new int[3];
    
    // checks for any errors with arguments
    if (distance == null || (distance - 0.0) < 0.001 ) {
      throw new IllegalArgumentException("Invalid time, either null or negative");
    }

    this.verifyTime(time); // private method to verify the format of time is correct
    
    // saves the runs' distance, time of run, pace of run
    this.distance = distance;
    arrayOfTimes = this.stringToTime(time); // private method to parse string
    timeRan = LocalTime.of(arrayOfTimes[0], arrayOfTimes[1], arrayOfTimes[2]);
    paceOfRun = this.calculatePace();

    // will convert the String dateOfRun into a LocalTime object 
    this.runDate = LocalDate.parse(dateOfRun);

    // initalize remaining fields
    title = null;
    location = null;
    rating = 0;
    description = null;

  }
  
  /**
   * this methods sets the title of the run with the passed in argument
   * @param title the title of run
   */
  public void setTitle(String title) {
    this.title = title;
  }
  
  /**
   * this methods sets the location of the run with the passed in argument
   * @param location the location of the run
   */
  public void setLocation(String location) {
    this.location = location;
  }
  
  /**
   * this methods sets the rating of the run with the passed in argument
   * should be from the range of 1-10 (inclusive)
   * @param rating the overall rating of the run
   */
  public void setRating(int rating) {
    // the rating should be between 1-10
    if (rating > 10 || rating < 1) {
      throw new IllegalArgumentException("Invalid range for rating");
    }
    this.rating = rating;
  }

  /**
   * Resets the total time for this run. Resetting this total run time will need to 
   * remake a LocalTime object and will need to recalculate pace
   * @param time the new time for the run
   */
  public void setRunTime(String time) {
    // a check for the string to not be null or empty
    if (time == null || time.trim().isBlank()) {
      throw new NoSuchElementException("Invalid time, either null or blank");
    }
    int[] arrayOfTimes = new int[3];
    this.verifyTime(time);
    arrayOfTimes = this.stringToTime(time);
    timeRan = LocalTime.of(arrayOfTimes[0], arrayOfTimes[1], arrayOfTimes[2]);
    this.calculatePace();
  }

  /**
   * This method sets the description of the run with the passed in String
   * @param description the description of the run
   */
  public void setDescription(String description) {
    this.description = description;
  }
  
  /**
   * This method will take a string for the time of run and parse it correctly so the returned array can be used 
   * to make a LocalTime object
   * @param time the string containing the time of the run. Should be formatted as so: (00:00:00) or (00:00) or (00)
   * @return an array where each index corresponds to [hour,minutes,seconds]
   */
  private int[] stringToTime(String time) {
    int hours = 0;
    int minutes = 0;
    int seconds = 0;

    // split the array on the ":" symbol
    String[] parsedTime = time.split(":");
    int[] toReturn = new int[3];

    // has only seconds (00)
    if (parsedTime.length == 1) {
      seconds = Integer.parseInt(parsedTime[0]);
      toReturn[0] = 0;
      toReturn[1] = 0;
      toReturn[2] = seconds;
    }
      
    // has only seconds,minutes (00:00)
    else if (parsedTime.length == 2) {
      minutes = Integer.parseInt(parsedTime[0]);
      seconds = Integer.parseInt(parsedTime[1]);
      toReturn[0] = seconds;
      toReturn[1] = minutes;
      toReturn[2] = 0;
      
    }
      
    // has seconds,minutes,hours (00:00:00)
    else if (parsedTime.length == 3) {
      hours = Integer.parseInt(parsedTime[0]);
      minutes = Integer.parseInt(parsedTime[1]);
      seconds = Integer.parseInt(parsedTime[2]);        
      toReturn[0] = hours;
      toReturn[1] = minutes;
      toReturn[2] = seconds;
      
    }
    return toReturn;
  }
  
  /**
   * returns the field varaible title
   * @return title of run or null if has not been set
   */
  public String getTitle() {
    return this.title;
  }
  
  /**
   * returns the field variable locations
   * @return location of run or null if has not been set
   */
  public String getLocation() {
    return this.location;
  }

  /**
   * returns the field varuable rating
   * @return rating of the run or 0 if has not been set
   */
  public int getRating() {
    return this.rating;
  }

  /**
   * returns the field variable paceOfRun
   * @return the pace of the run determined by the distance / time
   */
  public String getPace() {
    return this.paceOfRun;
  }

  /**
   * returns the field variable description
   * @return 
   */
  public String getDescription() {
    return this.description;
  }
  /**
   * this method calculates the average minutes/mile of this run based on the passed in 
   * distance and time of run
   * @return a string that has the average pace of the run, formatted as so (00:00) or (0:00)
   */
  private String calculatePace() {
    int wholeNum = 0;
    double pace = 0.0;
    int temp = 0;
    String wholeNumSave = "";
    String fractionNumSave = "";
    String toReturn = "";

    
    // calculates pace based on distance and time of run and stores into a double variable
    int hoursToMinutes = this.timeRan.getHour() * 60;
    Double totalMinutes = (double)hoursToMinutes + (double)this.timeRan.getMinute();
    totalMinutes = totalMinutes + ((double)this.timeRan.getSecond() / 60);
    pace = totalMinutes / this.distance;

    // convert pace to a formatted string
    wholeNum = (int)pace; // save whole num
    pace = pace - (int)pace; // save fraction part
    pace = 60 * pace;
    pace = Math.round(pace);
    temp = (int)pace;
    wholeNumSave = "" + wholeNum; // store whole num in a string
    fractionNumSave = "" + temp;  // store fraction part of num in a string

    // if the fraction part of the string is # --> convert to 0#
    if (fractionNumSave.length() == 1) {
      fractionNumSave = "0" + fractionNumSave;
    }
    toReturn = wholeNumSave + ":" + fractionNumSave;

    return toReturn;  
    
  }
  


  /**
   * Method to compare two runs based on the date of the run 
   * @return 0 if both runs are equal, negative int if this run is later than the passed in run, positive if this run is earlier 
   * than the passed in run
   */
  @Override
  public int compareTo(Run o) {
    return this.runDate.compareTo(o.runDate);
  }

  /**
   * This method makes a string containing the important parts about a variable 
   * @return a string the represents this run
   */
  @Override
  public String toString() {
    // format the return string from LocalTime.toString()
    String timeHour = "" + this.timeRan.getHour();
    String timeMinute = "" + this.timeRan.getMinute();
    String timeSeconds = "" + this.timeRan.getSecond();
    if (timeHour.length() == 1) {
      timeHour = "0" + timeHour;
    }
    if (timeMinute.length() == 1) {
      timeMinute = "0" + timeMinute;
    }
    if (timeSeconds.length() == 1) {
      timeSeconds = "0" + timeSeconds;
    }
    String runTime = "" + timeHour + ":" + timeMinute + ":" + timeSeconds;
    String toReturn = "";

    toReturn = "Date of Run: " + this.runDate.toString() + "\n" + "Time Ran: " + runTime + "\n" +
            "Distance Ran: " + this.distance + "\n" + "Pace of Run: " + this.paceOfRun; 
    return toReturn;
    
  }

  /**
   * This private method verifies the time is in the right format. 
   * (1) Characters in the string should either be a digit or ':'
   * (2) the sum of all the numbers should not be 0 (meaning the time is 0)
   * @param time the string representing time
   * @return true if it is propperly formatted, false otherwise 
   */
  private void verifyTime(String time) {
    if (time == null || time.trim().isBlank()) {
      throw new IllegalArgumentException("Time is empty");
    }
    // verify the appropriate charaters are found
    for (int i = 0; i < time.length(); ++ i) {
      if (Character.isDigit(time.charAt(i))) {
        continue;
      }
      else if (time.charAt(i) == ':') {
        continue;
      }
      // if code reaches here than character is not a digit or ':'
      throw new IllegalArgumentException("Time contains unexpected characters");
    }

    // verify the format of the characters are good
    String[] toVerify = time.split(":");
    // should only be 3 index's max 00 > Hour (00:00:00) , Minutes (00:00), Seconds (00)
    if (toVerify.length > 3 || toVerify.length == 0) { 
      throw new IllegalArgumentException("Time is not properly formatted. Ex. (00:00:00)");
    }
    int sum = 0;
    for (int i = 0; i < toVerify.length; ++i) {
      sum +=Integer.parseInt(toVerify[i]);
    }
    if (sum == 0) {
      throw new IllegalArgumentException("Time is 0, pick a valid time value that is greater than 0");
    }
  }



}
