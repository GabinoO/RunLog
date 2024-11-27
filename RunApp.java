import java.util.Scanner;

public class RunApp {
  public static void main (String[] args) {
    RunStorage runCollection = new RunStorage();
    Scanner input = new Scanner(System.in);
    FrontendRun myFront = new FrontendRun(input, runCollection);
    System.out.println("Welcome to your Virtual Running Log!");
    System.out.println("------------------------------------");

    myFront.loopUI();

    System.out.println("Thank you for using this Running Log!");
    System.out.println("Be back soon!");
  }
}
