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
    this.duration = duration.ofSeconds(0);
    for (int i = 0; i < activities.size(); i++) {
      //We are iterating through projects to sum every interval to get the duration of this task
      this.duration = this.duration.plus(activities.get(i).getDuration());
    }
  }
  
  //Generate JSONObject for Task and Project
  public JSONObject project() {
        JSONArray list = new JSONArray();
        JSONObject task = new JSONObject();

        for(int i=0; i < projects.size(); i++) {
            if (projects.get(i) instanceof Project) {
                list.put(((Project) projects.get(i)).project());
            } else if(projects.get(i) instanceof Task) {
                list.put(((Task) projects.get(i)).taskToJson());
            }
        }
        task.put("Projects", list);
        super.activity(task);
        return task;
    }
}
