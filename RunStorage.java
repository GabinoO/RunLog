import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class defines a collection of Run objects in the form of a sorted ArrayList
 */
public class RunStorage {

  ArrayList<Run> runStorage = new ArrayList<>(); // Sorted arraylist to store all Run objects 
  int size = 0;
  Run fastestRun = null;
  Run longestRun = null;
  
  /**
   * This method reads in a CSV file of Run and adds each Run to the collection. The CSV file will be sorted as such:
   * Distance, Time, Date(If applicable)
   * @param fileName
   * @throws FileNotFoundException 
   */
  public void readFile(String fileName) throws FileNotFoundException {
    File aFile = new File(fileName);
    Scanner scnr = null; 
    scnr = new Scanner(aFile);

    while(scnr.hasNextLine()) {
      // reads one line and splits it by: ","
      String currLine = scnr.nextLine();
      String[] runToSave = currLine.split(","); // [Distance, Time, Date]
      // error checking
      if (runToSave.length > 3 || runToSave.length < 1) {
        System.out.println("Error: did not have nessasary data to log run");
        return;
      }
      // save currLine information into a Run Object
      double distance = Double.parseDouble(runToSave[0]);
      String time = runToSave[1];
      Run toAdd;
      // if the only provided information was distance and time
      if (runToSave.length == 2) {
        toAdd = new Run(distance,time);
      }
      else {
        // if the provided information was distance,time,date
        String date = runToSave[2];
        toAdd = new Run(distance,time,date);
      }

      this.addRun(toAdd);
    }

  }


  /**
   * adds the passed in run to the ArrayList field than sorts the Arraylist
   * @param toAdd the run to add to the sorted array
   */
  public void addRun(Run toAdd) {
    // saves the passed in run as longest AND fastest run if no elements are present in the collection yet
    if (this.getSize() == 0) {
      this.longestRun = toAdd;
      this.fastestRun = toAdd;
    }
    else {
      // will save passed in Run as "Longest Run" if it is longer than the current "Longest Run"
      if (Double.compare(longestRun.distance, toAdd.distance) < 0) {
        this.longestRun = toAdd;
      }
      // will save passed in Run as "Fastest Run" if it is Faster than the current "Fastest Run"
      if (this.comparePace(toAdd.paceOfRun, this.fastestRun.paceOfRun) > 0) {
        this.fastestRun = toAdd;
      }
    }

    // finally, adds run to the Collection
    runStorage.add(toAdd);
    Collections.sort(runStorage);
    ++size;

  }

  /**
   * Removes the passed in object from the arrayList using bubble sort since the array will stay sorted
   * @param toRemove the Run object that is going to be removed
   * @return the removed run, or null if it was not found
   */
  public Run removeRun(Run toRemove) {
    // makes a temp object to be able to compare this Run from another in this collection
    int indexOfRun = this.findRun(toRemove);
    if (indexOfRun == -1) {
      return null;
    }
    else {
      --this.size;
      if (toRemove.compareTo(this.getFastest()) == 0) { // if fastest was removed
        this.setFastest();
      } 
      if (toRemove.compareTo(this.longestRun) == 0) { // if longest was removed
        this.setLongest(); 
      }
      return this.runStorage.remove(indexOfRun); // removes and returns the removed run
    }
  }

  /**
   * This method finds the index of the passed in run 
   * @param toFind the Run object that we want to find
   * @return the index in which the Run is located, or -1 if not found
   */
  private int findRun(Run toFind) {
    int low = 0;
    int high = this.runStorage.size() - 1;
    // bubble sort to find the run and remove it
    while (low <= high) {
      int mid = low + (high - low) / 2;

      // if the object is found --> return the index that it was found in
      if (this.runStorage.get(mid).compareTo(toFind) == 0) {
        return mid;
        
      }

      // if current run is smaller than toRemove run --> look right
      else if (this.runStorage.get(mid).compareTo(toFind) < 0) {
        low = mid + 1;
      }

      // if current run is bigger than toRemove run --> look left
      else if (this.runStorage.get(mid).compareTo(toFind) > 0) {
        high = mid - 1;
      }
    }
    return -1; 
  }

  /**
   * This method finds the run that matches the passed in date. if two runs are on the same day,
   * this method will return the one that was entered first
   * @param date the date matching the target run
   * @return the run that matches the specified date
   */
  public Run getRun(String date) {
    if (date == null || date.trim().isBlank()) {
      throw new IllegalArgumentException();
    }

    Run toFind = new Run(1.00, "00:10:00" ,date);
    int index = this.findRun(toFind);
    if (index == -1) {
      throw new NoSuchElementException();
    }
    return this.runStorage.get(index); // returns the Run at the index returned from the findRun() method
  }

  /**
   * This method returns an arrayList that contains the earliest 7 runs from (Earliest --> Latest) or if there are currently less
   * than 7 run objects in the arraylist than it will return all the Run Obejcts
   * @return the latest 7 runs
   */
  public RunStorage getLatest7() {
    RunStorage topSeven = new RunStorage();

    // if there are less than 7 songs in the Run Collection
    if (this.getSize() < 7) {
      for (int i = 0; i < runStorage.size(); ++i) {
        topSeven.addRun(this.runStorage.get(i));
      }
      return topSeven;
    }

    // will return the latest 7 runs 0(Earliest) --> 6(Late)
    for (int i = this.getSize()-1; i >= this.getSize()-7; --i) {
      topSeven.addRun(this.runStorage.get(i));
    }
    return topSeven;
  }

  /**
   * This method creates a formatted way to view the Collection of Run's
   * @return a String representing the contents of this Run Collection, or "" if no Run's in this Collection
   */
  @Override
  public String toString() {
    String toReturn = "";
    for (int i = 0; i < this.runStorage.size(); ++i) {
      toReturn = toReturn + this.runStorage.get(i) + "\n" + "-----------------------------\n" ;
    }
    return toReturn;
  }

  /**
   * This method gets the amount of Run Objects in this collection
   * @return the size field variable
   */
  public int getSize() {
    return this.size;
  }

  /**
   * this method gets the longest run stored in this collection
   * @return The longestRun field variable
   */
  public Run getLongest() {
    return this.longestRun;
  }

  /**
   * This method gets the Fastest run stored in this collection
   * @return the fastestRun field variable
   */
  public Run getFastest() {
    return this.fastestRun;
  }

  /**
   * This private method compares two passed in Run Pace's in the form of String's and compares them to see
   * which one is faster. The pace will be formatted as such: ##:##
   * @param pace1 the pace of a Run Object
   * @param pace2 the pace of a second Run Object
   * @return a positive int if pace1 is faster than pace2, 
   * a negative int if pace1 is slower than pace2, and 0 if they are the same
   */
  private int comparePace(String pace1, String pace2) {
    // seperates the numbers in each pace: [Minutes], [Seconds]
    String[] paceOne = pace1.split(":");
    String[] paceTwo = pace2.split(":");

    // Storing the Minutes and seconds into variables of each pace
    Double pace1Minutes = Double.parseDouble(paceOne[0]);
    Double pace1Seconds = Double.parseDouble(paceOne[1]);
    Double pace2Minutes = Double.parseDouble(paceTwo[0]);
    Double pace2Seconds = Double.parseDouble(paceTwo[1]);

    // Compare the pace1 and pace2
    
    // if pace1 has a lower minute number (faster)
    if (Double.compare(pace1Minutes, pace2Minutes) < 0) {
      return 1;
    }
    // if pace1 has a higher minutes number (slower)
    if (Double.compare(pace1Minutes, pace2Minutes) > 0) {
      return -1;
    }
    
    // this section is if the numbers are equal

    // if pace1 has a lower seconds number (faster)
    if (Double.compare(pace1Seconds,pace2Seconds) < 0) {
      return 1;
    }
    // if pace1 has a higher seconds number (slower)
    if (Double.compare(pace1Seconds, pace2Seconds) > 0) {
      return -1;
    }

    return 0; // if code reaches here than both paces are equal
  }

  /**
   * This re-sets the fastestRun variable with the fastest run in the collection
   */
  private void setFastest() {
    if (this.getSize() == 0) {
      this.fastestRun = null;
      return;
    }
    // finds fastest run
    Run max = this.runStorage.get(0);
    for (int i = 0; i < this.getSize(); ++i) {
      Run currRun = this.runStorage.get(i);
      if (this.comparePace(max.paceOfRun, currRun.paceOfRun) < 0) {
        max = runStorage.get(i);
      }
    }
    this.fastestRun = max;
  }

  /**
   * This re-sets the longestRun variable with the fastest run in the collection
   */
  private void setLongest() {
    if (this.getSize() == 0) {
      this.fastestRun = null;
      return;
    }
    // finds longest run 
    Run max = this.runStorage.get(0);
    for (int i = 0; i < this.getSize(); ++i) {
      Run currRun = this.runStorage.get(i);
      if (currRun.distance > max.distance) {
        max = runStorage.get(i);
      }
    }
    this.longestRun = max;

  }


} 