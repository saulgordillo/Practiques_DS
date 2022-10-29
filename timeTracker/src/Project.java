import java.util.ArrayList;

public class Project extends Activity {

  public Project() {
    this.name = "";
    this.projectFather = null;
    this.activities = new ArrayList<Activity>();
  }

  public Project(boolean isRoot) {
    this.name = "root";
    this.projectFather = null;
    this.activities = new ArrayList<Activity>();
    this.isRoot = true;
  }

  public Project(String name, Project father) {
    this.name = name;
    this.projectFather = father;
    this.activities = new ArrayList<Activity>();
    if (father != null) {
      father.addChild(this);
    }
  }

  public void addChild(Activity child) {
    this.activities.add(child);
  }

  public String getName() {
    return this.name;
  }

  public void calculateDuration() {
    for (int i = 0; i < activities.size(); i++) {
      //We are iterating through projects to sum every interval to get the duration of this task
      this.duration.plus(activities.get(i).getDuration());
    }
  }
}
