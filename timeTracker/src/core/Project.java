package core;


import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import visitor.Visitor;

/**
 * Class which extends Activity and implements
 * the specific methods and attributes of a Project,
 * uses Composite pattern together with Activity and Task.
 */
public class Project extends Activity {
  static final Logger loggerProject = LoggerFactory.getLogger("core.Activity.Project");

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

    loggerProject.info("Create Project root");
    this.isRoot = isRoot;
  }

  /**
   * Constructor to create a Project with params.
   *
   * @param name   - Name of the project
   * @param father - Who is the father of the project
   * @param tags   - Tags associated with the project
   */
  public Project(String name, Project father, List<String> tags) {
    //Pre-conditions
    assert (!name.isEmpty()) : "Error: empty name";
    assert (father.getName() != null) : "Error: empty father name";

    this.name = name;
    this.projectFather = father;
    this.activities = new LinkedList<>();
    loggerProject.info("Add Project '" + this.getName() + "' as child of Project father '"
            + father.getName() + "'");
    father.addChild(this);
    this.tags = tags;

    //Post-conditions
    assert invariant() : "Error: duration < 0";
    assert (!this.name.isEmpty()) : "Error: empty name";
    assert (this.projectFather.getName() != null) : "Error: empty father name";
    assert (this.tags.size() == tags.size()) :
            "Error: this.tags is not equal (size) to parameter tags";
  }

  /**
   * @return Boolean, true if duration >= 0, false otherwise
   */
  private boolean invariant() {
    return (projectFather.duration.toSeconds() >= 0);
  }

  /**
   * Add Activity object to a Project as a child.
   *
   * @param child - Actual child of this project
   */
  public void addChild(Activity child) {
    //Pre-conditions
    assert (this.activities != null) : "Error: attribute activities equals null (not initialized)";
    assert (child != null) : "Error: cannot add null as child";

    this.activities.add(child);

    //Post-conditions
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
    //Pre-condition
    assert invariant() : "Error: duration < 0";

    this.duration = Duration.ofSeconds(0);
    loggerProject.debug("Calculate duration");

    for (Activity activity : activities) {
      this.duration = this.duration.plus(activity.getDuration());
    }

    if (this.duration.toSeconds() < 0) {
      loggerProject.error("Duration calculation incorrect");
    }

    //Post-condition
    assert invariant() : "Error: duration < 0";
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
  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
  public JSONObject projectToJSON() {
    loggerProject.info("Create JSON from Project, Task and Interval");

    loggerProject.debug("Project to JSONObject");
    JSONArray list = new JSONArray();
    JSONObject task = new JSONObject();

    //Pre-condition
    assert (this.activities != null) : "Error: activities is null";

    for (Activity activity : this.activities) {
      if (activity instanceof Project) {
        loggerProject.debug("Activity is a Project");
        list.put(((Project) activity).projectToJSON());
      } else if (activity instanceof Task) {
        loggerProject.debug("Activity is a Task");
        list.put(((Task) activity).taskToJSON());
      }
    }

    task.put("Projects", list);
    super.activityToJSON(task);

    return task;
  }
}
