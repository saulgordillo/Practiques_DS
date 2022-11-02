import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
  private static Project testCountingTime() throws InterruptedException {
    Project projectRoot = new Project(true);
    // top level
    Project softwareDesign = new Project("software design", projectRoot);
    Project softwareTesting = new Project("software testing", projectRoot);
    Project databases = new Project("database", projectRoot);
    Task transportation = new Task("transportation", projectRoot);

    // under software design
    Project problems = new Project("problems", softwareDesign);
    Project projectTimeTracker = new Project("project time tracker", softwareDesign);

    // under problems
    Task firstList = new Task("first list", problems);
    Task secondList = new Task("second list", problems);

    // under project time tracker
    Task readHandout = new Task("read handout", projectTimeTracker);
    Task firstMilestone = new Task("first milestone", projectTimeTracker);

    transportation.start();
    System.out.println("transportation starts");

    try {
      Thread.sleep(6000);
    } catch (Exception e) {
      System.out.print("Thread.sleep()");
    }

    transportation.stop();
    System.out.println("transportation stops");

    try {
      Thread.sleep(2000);
    } catch (Exception e) {
      System.out.print("Thread.sleep()");
    }

    System.out.println("firstList starts");
    firstList.start();

    try {
      Thread.sleep(6000);
    } catch (Exception e) {
      System.out.print("Thread.sleep()");
    }

    System.out.println("secondList starts");
    secondList.start();

    try {
      Thread.sleep(4000);
    } catch (Exception e) {
      System.out.print("Thread.sleep()");
    }

    firstList.stop();
    System.out.println("firstList stop");

    try {
      Thread.sleep(2000);
    } catch (Exception e) {
      System.out.print("Thread.sleep()");
    }

    secondList.stop();
    System.out.println("secondList stop");


    try {
      Thread.sleep(2000);
    } catch (Exception e) {
      System.out.print("Thread.sleep()");
    }

    System.out.println("transportation starts");
    transportation.start();
    try {
      Thread.sleep(4000);
    } catch (Exception e) {
      System.out.print("Thread.sleep()");
    }
    transportation.stop();
    System.out.println("transportation stop");

    return projectRoot;
  }

  public static void main(String[] args) throws InterruptedException {
    // Start clock
    Clock clockInstance = Clock.getInstance();
    try {
      Thread.sleep(1500);
    } catch (Exception e) {
      System.out.print("Thread.sleep()");
    }

    Project projectRoot = testCountingTime();

    // Stop clock
    Clock myClock = Clock.getInstance();
    myClock.deleteTimer();

    //Save data in JSONObject
    JSONObject dataFiles = projectRoot.projectToJSON();

    //Create json file and add data
    try (FileWriter file = new FileWriter("test.json")) {
      file.write(dataFiles.toString());
      file.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}