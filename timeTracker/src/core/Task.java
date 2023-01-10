package core;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import provider.IdProvider;
import visitor.Visitor;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

/**
 * Class which extends Activity and implements
 * the specific methods and attributes of a Task,
 * uses Composite pattern together with Activity and Project.
 */
public class Task extends Activity {
  static final Logger loggerTask = LoggerFactory.getLogger("core.Activity.Task");

  private final List<Interval> intervals;

  private boolean active = false;

  /**
   * Constructor to create Task from params.
   *
   * @param name   - Name of the task
   * @param father - Father of the task
   * @param tags   - Tags that may be associated with the task
   */
  public Task(String name, Project father) {//, List<String> tags) {
    //Pre-condition
    assert (!name.isEmpty()) : "Error: empty name";
    assert (father.getName() != null) : "Error: empty father name";

    this.name = name;
    this.projectFather = father;
    father.addChild(this);
    this.intervals = new LinkedList<>();
    //this.tags = tags;

    IdProvider uniqueIdProviderInstance = IdProvider.getInstance();
    this.id = uniqueIdProviderInstance.getId();

    //Post-condition
    assert invariant() : "Error: duration < 0 || intervals is null";
    assert (!this.name.isEmpty()) : "Error: empty name";
    assert (this.projectFather.getName() != null) : "Error: empty father name";
    assert (this.intervals != null) : "Error: attribute intervals equals null (not initialized)";
    assert (this.tags.size() == tags.size()) :
        "Error: this.tags is not equal (size) to parameter tags";
  }

  /**
   * @return Boolean, true if duration >= 0 and intervals != null, false otherwise
   */
  private boolean invariant() {
    return ((duration.getSeconds() >= 0) && (intervals != null));
  }

  /**
   * @return List of String containing the tags of a Task
   */
  public List<String> getTags() {
    return this.tags;
  }

  public boolean getActive() {
    return this.active;
  }

  /**
   * Calculate duration iterating through intervals to sum every Interval duration.
   */
  public void calculateDuration() {
    //Pre-condition
    assert invariant() : "Error: duration < 0 || intervals is null";

    this.duration = Duration.ofSeconds(0);
    loggerTask.debug("Calculate duration");

    for (Interval interval : intervals) {
      this.duration = this.duration.plus(interval.getDuration());
    }

    if (this.duration.toSeconds() < 0) {
      loggerTask.error("Duration calculation incorrect");
    }

    //Post-condition
    assert invariant() : "Error: duration < 0 || intervals is null";
  }

  /**
   * Accept Visitor to visit the Task.
   *
   * @param visitor - Object that makes reference to the Visitor class
   */
  @Override
  public void accept(Visitor visitor) {
    visitor.visitTask(this);
  }

  /**
   * Start Task by creating a new Interval, adding it to the List of Interval,
   * getting the unique Clock instance and adding the new Interval as an Observer.
   */
  public void start() {
    //Pre-condition
    assert invariant() : "Error: duration < 0 || intervals is null";

    loggerTask.info("New Interval starts");
    Interval newInterval = new Interval(this);
    this.intervals.add(newInterval);
    newInterval.setActive(true);
    Clock clockInstance = Clock.getInstance();
    clockInstance.addObserver(newInterval);
    this.active = true;

    //Post-condition
    assert invariant() : "Error: duration < 0 || intervals is null";
  }

  /**
   * Stop Task by getting the unique instance and deleting the Interval from the Observer list.
   */
  public void stop() {
    int contClocks = Clock.getInstance().countObservers();
    //Pre-condition
    assert invariant() : "Error: duration < 0 || intervals is null";
    assert (!intervals.isEmpty()) : "Error: intervals empty";
    assert (contClocks != 0) : "Error: not any Observer to delete";

    Clock clockInstance = Clock.getInstance();
    Interval intervalToDelete = intervals.get(intervals.size() - 1);
    clockInstance.deleteObserver(intervalToDelete);
    intervals.get(intervals.size() - 1).setActive(false);
    loggerTask.info("Interval stops");
    this.active = false;

    contClocks = Clock.getInstance().countObservers();

    //Post-condition
    assert invariant() : "Error: duration < 0 || intervals is null";
    assert (!intervals.isEmpty()) : "Error: intervals empty";
    assert (contClocks == clockInstance.countObservers()) :
        "Error: Observer not removed properly";
  }

  /**
   * @return JSONObject containing the info of the Task class object
   */
  public JSONObject toJson(int depth) {
    // depth not used here
    JSONObject json = new JSONObject();
    json.put("class", "task");
    super.toJson(json);
    json.put("active", active);

    if (depth > 0) {
      JSONArray jsonIntervals = new JSONArray();
      for (Interval interval : intervals) {
        jsonIntervals.put(interval.toJson());
      }
      json.put("intervals", jsonIntervals);
    } else {
      json.put("intervals", new JSONArray());
    }

    return json;
  }

  public Activity findActivityById(int id) {
    if (this.id == id) {
      return this;
    }

    return null;
  }
}
