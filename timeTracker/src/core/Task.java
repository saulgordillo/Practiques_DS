package core;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  static Logger loggerTask = LoggerFactory.getLogger("core.Activity.Task");

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
    assert (!name.isEmpty()) : "Error, empty name";
    assert (father.getName() != null);
    assert (tags != null);

    this.name = name;
    this.projectFather = father;
    father.addChild(this);
    this.intervals = new LinkedList<>();
    this.tags = tags;

    //Post-condition
    assert invariant();
    assert (!this.name.isEmpty()) : "Error, empty name";
    assert (this.projectFather.getName() != null);
    assert (this.tags != null);
  }

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
    assert invariant();

    this.duration = Duration.ofSeconds(0);
    loggerTask.debug("Calculate duration");

    for (Interval interval : intervals) {
      this.duration = this.duration.plus(interval.getDuration());
    }

    if (this.duration.toSeconds() < 0) {
      loggerTask.error("Duration calculation incorrect");
    }

    //Post-condition
    assert invariant();
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
    assert invariant();

    loggerTask.info("New Interval starts");
    Interval newInterval = new Interval(this);
    this.intervals.add(newInterval);
    Clock clockInstance = Clock.getInstance();
    clockInstance.addObserver(newInterval);

    //Post-condition
    assert invariant();
  }

  /**
   * Stop Task by getting the unique instance and deleting the Interval from the Observer list.
   */
  public void stop() {
    int contClocks = Clock.getInstance().countObservers();
    //Pre-condition
    assert invariant();
    assert (!intervals.isEmpty());
    assert (contClocks != 0);

    Clock clockInstance = Clock.getInstance();
    clockInstance.deleteObserver(intervals.get(intervals.size() - 1));
    loggerTask.info("Interval stops");

    //Post-condition
    assert invariant();
    assert (!intervals.isEmpty());
    assert (contClocks == clockInstance.countObservers() - 1);
  }

  /**
   * @return JSONObject containing the info of the Task class object
   */
  public JSONObject taskToJSON() {
    //Pre-condition
    assert invariant();

    loggerTask.debug("Task to JSONObject");

    JSONArray list = new JSONArray();
    JSONObject task = new JSONObject();

    for (Interval interval : intervals) {
      list.put(interval.intervalToJSON());
    }

    task.put("IntervalTask", list);

    //Post-condition
    assert invariant();
    assert (list != null);
    assert (task != null);

    return task;
  }
}
