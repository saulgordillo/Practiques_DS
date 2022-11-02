import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Task extends Activity {
  private List<Interval> intervals;

  public Task() {
    this.name = "";
    this.projectFather = null;
    this.intervals = new LinkedList<Interval>();
  }

  public Task(String name, Project father) {
    this.name = name;
    this.projectFather = father;
    father.addChild(this);
    this.intervals = new LinkedList<Interval>();
  }

  public void calculateDuration() {
    this.duration = duration.ofSeconds(0);
    for (int i = 0; i < intervals.size(); i++) {
      //We are iterating through intervals to sum every interval to get the duration of this task
      this.duration = this.duration.plus(intervals.get(i).getDuration());
    }
  }

  //methods serves to test the abstract system
  public void whoAmI() {
    System.out.print("I am a Task");
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


    for (int i = 0; i < intervals.size(); i++) {
      list.put(intervals.get(i).intervalToJSON());
    }
    task.put("IntervalTask", list);
    return task;
  }
}
