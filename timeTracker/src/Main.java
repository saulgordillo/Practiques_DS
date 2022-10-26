public class Main {
  public static void main(String[] args) throws InterruptedException {
    // top level
    Project softwareDesign = new Project("software design", null);
    Project softwareTesting = new Project("software testing", null);
    Project databases = new Project("database", null);
    Task transportation = new Task("transportation", null);

    // under software design
    Project problems = new Project("problems", softwareDesign);
    Project projectTimeTracker = new Project("project time tracker", softwareDesign);

    // under problems
    Task firstList = new Task("first list", problems);
    Task secondList = new Task("second list", problems);

    // under project time tracker
    Task readHandout = new Task("read handout", projectTimeTracker);
    Task firstMilestone = new Task("first milestone", projectTimeTracker);

    firstList.start();

    //softwareDesign.printTree(firstList);

    Thread.sleep(2000);

    softwareDesign.printTree(firstList);

    firstList.stop();
  }
}