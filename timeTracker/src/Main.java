public class Main {
  private static void testCountingTime() throws InterruptedException {
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

    try {
      Thread.sleep(6000);
    } catch (Exception e) {
      System.out.print("Thread.sleep()");
    }

    firstList.stop();

    readHandout.start();

    try {
      Thread.sleep(4000);
    } catch (Exception e) {
      System.out.print("Thread.sleep()");
    }

    readHandout.stop();
  }

  private static void testTasksDuration() {

  }

  public static void main(String[] args) throws InterruptedException {
    testCountingTime();

    Clock myClock = Clock.getInstance();
    myClock.deleteTimer();
	
	//Save data in JSONObject
	JSONObject dataFiles = new JSONObject();
        dataFiles = projectRoot.project();

		//Create json file and add data 
       try (FileWriter file = new FileWriter("MyJSON.json")){
            file.write(dateFiles.toString());
            file.flush();
       }catch (IOException e) {
           e.printStackTrace();
       }
  }
}