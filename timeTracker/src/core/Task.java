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
 * the specific methods and attributes of a Task,
 * uses Composite pattern together with Activity and Project.
 */
public class Task extends Activity {
  static final Logger loggerTask = LoggerFactory.getLogger("core.Activity.Task");

  private final List<Interval> intervals;

  /**
   * Constructor to create Task from params.
   *
   * @param name   - Name of the task
   * @param father - Father of the task
   * @param tags   - Tags that may be associated with the task
   */
  public Task(String name, Project father, List<String> tags) {
    //Pre-condition
    assert (!name.isEmpty()) : "Error: empty name";
    assert (father.getName() != null) : "Error: empty father name";

    this.name = name;
    this.projectFather = father;
    father.addChild(this);
    this.intervals = new LinkedList<>();
    this.tags = tags;

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
    Clock clockInstance = Clock.getInstance();
    clockInstance.addObserver(newInterval);

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
    assert (contClocks != 0) : "Error: not any Observer";

    Clock clockInstance = Clock.getInstance();
    clockInstance.deleteObserver(intervals.get(intervals.size() - 1));
    loggerTask.info("Interval stops");

    //Post-condition
    assert invariant() : "Error: duration < 0 || intervals is null";
    assert (!intervals.isEmpty()) : "Error: intervals empty";
    assert (contClocks == clockInstance.countObservers() - 1) :
            "Error: Observer not removed properly";
  }

  /**
   * @return JSONObject containing the info of the Task class object
   */
  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
  public JSONObject taskToJSON() {
    //Pre-condition
    assert invariant() : "Error: duration < 0 || intervals is null";

    loggerTask.debug("Task to JSONObject");

    JSONArray list = new JSONArray();
    JSONObject task = new JSONObject();

    for (Interval interval : intervals) {
      list.put(interval.intervalToJSON());
    }

    task.put("IntervalTask", list);

    //Post-condition
    assert invariant() : "Error: duration < 0 || intervals is null";

    return task;
  }
}
