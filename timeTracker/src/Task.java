import java.util.ArrayList;
import java.util.List;

public class Task extends Activity {
  private List<Interval> intervals;

  public Task() {
    this.name = "";
    this.projectFather = null;
    this.intervals = new ArrayList<Interval>();
  }

  public Task(String name, Project father) {
    if (!father.isRoot) {
      this.name = name;
      this.projectFather = father;
      father.addChild(this);
      this.intervals = new ArrayList<Interval>();
    } else {
      System.out.print("Could not create task ");
      System.out.print(name);
      System.out.print(": Father is null\n");
    }
  }

  public void addInterval() {
    Interval interval = new Interval(this);
    intervals.add(interval);
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
    intervals.add(newInterval);
    Clock clockInstance = Clock.getInstance();
    clockInstance.addObserver(newInterval);
  }

  public void stop() {
    Clock clockInstance = Clock.getInstance();
    clockInstance.deleteObserver(intervals.get(intervals.size() - 1));
    //clockInstance.deleteTimer();
  }

  //Generate JSONObject for Intervals
  public JSONObject taskToJson() {
    JSONArray list = new JSONArray();
    JSONObject task = new JSONObject();


    for (int i = 0; i < intervals.size(); i++) {
      list.put(intervals.get(i).interval());
    }
    task.put("IntervalTask", list);
    return task;
  }
}
