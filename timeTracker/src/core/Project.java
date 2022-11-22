package core;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import visitor.Visitor;

public class Project extends Activity {
  static Logger loggerProject = LoggerFactory.getLogger("core.Activity.Project");
  protected final List<Activity> activities;
 

  /**
   * Constructor to create a Project choosing if it is root or not.
   *
   * @param isRoot - Bool that indicates us if the project is the root project
   */
  public Project(boolean isRoot) {
    this.name = "root";
    this.projectFather = null;
    this.activities = new LinkedList<>();

    this.isRoot = isRoot;
  }

  /**
   * Constructor to create a Project with params.
   *
   * @param name - Name of the project
   * @param father - Who is the father of the project
   * @param tags - Tags associated with the project
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
   * Add Activity object to a Project as a child.
   *
   * @param child - Actual child of this project
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
   * Calculate duration iterating through activities to sum every Activity duration.
   */
  public void calculateDuration() {
    loggerProject.info("Initial duration: ");
    this.duration = Duration.ofSeconds(0);
    for (Activity activity : activities) {
      this.duration = this.duration.plus(activity.getDuration());
    }
    if (this.duration.toSeconds() < 0) {
      loggerProject.warn("Duration incorrect");
    }
  }

  /**
   * Accept Visitor to visit the Project.
   *
   * @param visitor - Object that makes reference to the class visitor
   */
  @Override
  public void accept(Visitor visitor) {
    visitor.visitProject(this);
  }

  /**
   * @return JSONObject containing the info of the Activity (Project/Task) class object
   */
  //Generate JSONObject for core.Task and core.Project
  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
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
