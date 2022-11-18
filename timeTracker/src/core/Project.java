package core;

import org.json.JSONArray;
import org.json.JSONObject;
import visitor.Visitor;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

public class Project extends Activity {
  protected final List<Activity> activities;

  /**
   * Constructor to create a Project choosing if it is root or not
   *
   * @param isRoot
   */
  public Project(boolean isRoot) {
    this.name = "root";
    this.projectFather = null;
    this.activities = new LinkedList<>();

    this.isRoot = isRoot;
  }

  /**
   * Constructor to create a Project with params
   *
   * @param name
   * @param father
   * @param tags
   */
  public Project(String name, Project father, List<String> tags) {
    this.name = name;
    this.projectFather = father;
    this.activities = new LinkedList<>();
    if (father != null) {
      father.addChild(this);
    }
    this.tags = tags;
  }

  /**
   * Add Activity object to a Project as a child
   *
   * @param child
   */
  public void addChild(Activity child) {
    this.activities.add(child);
  }

  /**
   * @return List of String containing the tags of a Project
   */
  public List<String> getTags() {
    return this.tags;
  }

  /**
   * @return List of Activity containing the children of a Project
   */
  public List<Activity> getActivities() {
    return this.activities;
  }

  /**
   * Calculate duration iterating through activities to sum every Activity duration
   */
  public void calculateDuration() {
    this.duration = Duration.ofSeconds(0);
    for (Activity activity : activities) {
      this.duration = this.duration.plus(activity.getDuration());
    }
  }

  /**
   * Accept Visitor to visit the Project
   *
   * @param visitor
   */
  @Override
  public void accept(Visitor visitor) {
    visitor.visitProject(this);
  }

  /**
   * @return JSONObject containing the info of the Activity (Project/Task) class object
   */
  //Generate JSONObject for core.Task and core.Project
  public JSONObject projectToJSON() {
    JSONArray list = new JSONArray();
    JSONObject task = new JSONObject();

    for (Activity activity : activities) {
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
