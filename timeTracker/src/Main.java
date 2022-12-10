import core.Activity;
import core.Clock;
import core.Project;
import org.json.JSONObject;
import test.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {

  public static void main(String[] args) {
    // Start clock
    Clock clockInstance = Clock.getInstance();
    try {
      Thread.sleep(1500);
    } catch (Exception e) {
      System.out.print("Thread.sleep()");
    }

    // Test timeTracker implementation
    Project projectRoot = Test.countingTimeAndCreateTree();

    // Test search by tag implementation
    // Project projectRoot = Test.createTree();
    String tagToSearch = "Java";
    System.out.print("\n\nTag to search: " + tagToSearch + "\n");
    List<Activity> activitiesWithTag = Test.searchingByTag(projectRoot, tagToSearch);
    System.out.print("Activities with tag '" + tagToSearch
        + "': [" + activitiesWithTag.size() + "]");

    for (Activity activity : activitiesWithTag) {
      System.out.print("\n-> " + activity.getName());
    }
    System.out.print("\n");

    // Stop clock
    Clock myClock = Clock.getInstance();
    myClock.deleteTimer();

    //Save data in JSONObject
    JSONObject dataFiles = projectRoot.toJson(2);

    //Create json file and add data
    try (FileWriter file = new FileWriter("data.json")) {
      file.write(dataFiles.toString());
      file.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}