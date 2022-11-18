import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

public class Task extends Activity {
  private final List<Interval> intervals;

  public Task(String name, Project father) {
    this.name = name;
    this.projectFather = father;
    father.addChild(this);
    this.intervals = new LinkedList<>();
  }

  public void calculateDuration() {
    this.duration = Duration.ofSeconds(0);
    for (Interval interval : intervals) {
      //We are iterating through intervals to sum every interval to get the duration of this task
      this.duration = this.duration.plus(interval.getDuration());
    }
  }

  public void start() {
    Interval newInterval = new Interval(this);
    this.intervals.add(newInterval);
    Clock clockInstance = Clock.getInstance();
    clockInstance.addObserver(newInterval);
  }

  public void stop() {
    Clock clockInstance = Clock.getInstance();
    clockInstance.deleteObserver(intervals.get(intervals.size() - 1));
  }

  //Generate JSONObject for Intervals
  public JSONObject taskToJSON() {
    JSONArray list = new JSONArray();
    JSONObject task = new JSONObject();


    for (Interval interval : intervals) {
      list.put(interval.intervalToJSON());
    }
    task.put("IntervalTask", list);
    return task;
  }
}
