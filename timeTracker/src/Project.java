import java.util.ArrayList;
import java.util.List;

public class Project extends Activity {

  public List<Activity> projects;

  public Project() {
    this.name = "";
    this.projectFather = null;
    this.projects = new ArrayList<Activity>();
  }

  public Project(String name, Project father) {
    this.name = name;
    this.projectFather = father;
    this.projects = new ArrayList<Activity>();
    if (father != null) {
      father.addChild(this);
    }
  }

  public void addChild(Activity child) {
    this.projects.add(child);
  }

  public String getName() {
    return this.name;
  }

  public void duration() {
    for (int i = 0; i < projects.size(); i++) {
      //We are iterating through projects to sum every interval to get the duration of this task
      this.duration.plus(projects.get(i).getDuration());
    }
  }

  //this methods test all the methods and abstract system.
  public void whoAmI() {
    System.out.println("I am a project");
  }

  public void drawSons() {
    int i = 0;
    while (i < projects.size()) {
      System.out.print(projects.get(i).name + "\n");
      i = i + 1;
    }
  }

  @Override
  public void printName() {
    System.out.print("Project name: ");
    System.out.print(this.name);
    System.out.print("\tChild of ");
    System.out.print(this.projectFather);
    System.out.print("\tInitial time: ");
    System.out.print(this.initialHour);
    System.out.print("\tFinal time: ");
    System.out.print(this.finalHour);
    System.out.print("\tDuration: ");
    System.out.print(this.duration);
  }
}
