import java.util.List;

public class Task extends Activity {
  private List<Interval> intervals;

  public Task() {
    this.name = "";
    this.projectFather = null;
  }

  public Task(String name, Project father) {
    this.name = name;
    this.projectFather = father;
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

}
