public class Main {
  public static void main(String[] args) {

    //Project softeare_desgin = new Project("software desgin", null);
    //Project software_testing = new Project("software testing", null);
    //Project database = new Project("database", null);
    //Project transportation = new Project("software_desgin", null);


    Project p1 = new Project("p1", null);
    Task t1 = new Task("t1", p1);
    Project p2 = new Project("p2", p1);
    Task t2 = new Task("t2", p2);
    Task t3 = new Task("t3", p2);

    //Let's look if all the connexions are ok
    //p1.addChild(t1);
    //p1.addChild(p2);

    //p2.addChild(t2);
    //p2.addChild(t3);

    p1.drawSons();
    p2.drawSons();

    t3.printTree(t3);


  }
}