import java.util.ArrayList;
import java.util.Collections;

public class RunStorage {

  ArrayList<Run> runStorage = new ArrayList<>(); // Sorted arraylist to store all Run objects 
  int size = 0;
  

  /**
   * adds the passed in run to the ArrayList field than sorts the Arraylist
   * @param toAdd the run to add to the sorted array
   */
  public void addRun(Run toAdd) {
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
    int indexOfRun = this.findRun(toRemove);
    if (indexOfRun == -1) {
      return null;
    }
    else {
      return this.runStorage.remove(indexOfRun);
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

  public Run getRun(Run key) {
    int indexOfRun = this.findRun(key);
    if (indexOfRun == -1) {
      return null;
    }
    else {
      return this.runStorage.get(indexOfRun);
    }
  }

  /**
   * This method returns an arrayList that contains the earliest 7 runs from (Earliest --> Latest) or if there are currently less
   * than 7 run objects in the arraylist than it will return all the Run Obejcts
   * @return the latest 7 runs
   */
  public RunStorage getLatest7() {
    ArrayList<Run> toReturn = new ArrayList<>();
    RunStorage topSeven = new RunStorage();

    // if there are less than 7 songs in the Run Collection
    if (topSeven.getSize() < 7) {
      for (int i = 0; i < runStorage.size(); ++i) {
        topSeven.addRun(this.runStorage.get(i));
      }
      return topSeven;
    }

    for (int i = 0; i < 7; ++i) {
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

  public int getSize() {
    return this.size;
  }

  
 

}