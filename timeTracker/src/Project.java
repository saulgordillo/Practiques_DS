import org.json.JSONArray;
import org.json.JSONObject;


import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

public class Project extends Activity {
  protected final List<Activity> projects;

  public Project(boolean isRoot) {
    this.name = "root";
    this.projectFather = null;
    this.projects = new LinkedList<>();

    this.isRoot = isRoot;
  }

  public Project(String name, Project father) {
    this.name = name;
    this.projectFather = father;
    this.projects = new LinkedList<>();
    if (father != null) {
      father.addChild(this);
    }
  }

  public void addChild(Activity child) {
    this.projects.add(child);
  }

  public void calculateDuration() {
    this.duration = Duration.ofSeconds(0);
    for (Activity activity : projects) {
      //We are iterating through projects to sum every interval to get the duration of this task
      this.duration = this.duration.plus(activity.getDuration());
    }
  }

  //Generate JSONObject for Task and Project
  public JSONObject projectToJSON() {
    JSONArray list = new JSONArray();
    JSONObject task = new JSONObject();

    for (Activity activity : projects) {
      if (activity instanceof Project) {
        list.put(((Project) activity).projectToJSON());
      } else if (activity instanceof Task) {
        list.put(((Task) activity).taskToJSON());
      }
    }
    task.put("Projects", list);
    super.activityToJSON(task);
    return task;
  }
}
