import java.util.ArrayList;
import java.util.List;

public class Task extends Activity {
  private List<Interval> intervals;


    public Task()
    {
        this.name="";
        this.projectFather=null;
        this.intervals = new ArrayList<Interval>();
    }
    public Task(String name, Project father)
    {
        this.name = name;
        this.projectFather = father;
        father.addChild(this);
        this.intervals = new ArrayList<Interval>();
    }

    public void addInterval()
    {
      Interval interval = new Interval(this);
      intervals.add(interval);
    }

  public void duration() {
    for (int i = 0; i < intervals.size(); i++) {
      //We are iterating through intervals to sum every interval  to get the duration of this task
      this.duration.plus(intervals.get(i).calculateInterval());
    }
  }

  public void initHours() {
    this.initialHour = intervals.get(0).getInitialDate();
    this.finalHour = intervals.get(intervals.size() - 1).getFinalDate();
  }

  //methods serves to test the abstract system
  public void whoAmI() {
    System.out.print("I am a Task");
  }

  //WARNING
  public void start() {
    Interval newInterval = new Interval(this);
    intervals.add(newInterval);
    TimeController timeControllerInstance = TimeController.getInstance();
    timeControllerInstance.addObserver(newInterval);
  }

  public void stop() {
    TimeController timeControllerInstance = TimeController.getInstance();
    timeControllerInstance.deleteObserver(intervals.get(intervals.size() - 1));
  }
}
