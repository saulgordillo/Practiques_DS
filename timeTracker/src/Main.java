public class Main {
  public static void main(String[] args) throws InterruptedException {
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

    firstList.start();

    Thread.sleep(6000);

    firstList.stop();

    readHandout.start();

    Thread.sleep(4000);

    readHandout.stop();
  }
}