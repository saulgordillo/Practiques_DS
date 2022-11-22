package core;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import visitor.Visitor;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

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
    this.name = name;
    this.projectFather = father;
    father.addChild(this);
    this.intervals = new LinkedList<>();
    this.tags = tags;
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
    this.duration = Duration.ofSeconds(0);
    loggerTask.debug("Calculate duration");

    for (Interval interval : intervals) {
      this.duration = this.duration.plus(interval.getDuration());
    }

    if (this.duration.toSeconds() < 0) {
      loggerTask.error("Duration calculation incorrect");
    }
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
    loggerTask.info("New Interval starts");
    Interval newInterval = new Interval(this);
    this.intervals.add(newInterval);
    Clock clockInstance = Clock.getInstance();
    clockInstance.addObserver(newInterval);
  }

  /**
   * Stop Task by getting the unique instance and deleting the Interval from the Observer list.
   */
  public void stop() {
    Clock clockInstance = Clock.getInstance();
    clockInstance.deleteObserver(intervals.get(intervals.size() - 1));
    loggerTask.info("Interval stops");
  }

  /**
   * @return JSONObject containing the info of the Task class object
   */
  public JSONObject taskToJSON() {
    loggerTask.debug("Task to JSONObject");

    JSONArray list = new JSONArray();
    JSONObject task = new JSONObject();

    for (Interval interval : intervals) {
      list.put(interval.intervalToJSON());
    }

    task.put("IntervalTask", list);
    return task;
  }
}
