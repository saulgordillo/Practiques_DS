import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
  private LocalDateTime initialDate = null;
  private LocalDateTime finalDate = null;
  private Duration duration = null;
  private Task myTask;

  public Interval(Task father) {
    this.myTask = father;
    this.duration = duration.ofSeconds(0);
  }

  public Duration getDuration() {
    return duration;
  }

  public LocalDateTime getFinalDate() {
    return finalDate;
  }

  // Update dates when the Observable has changed, in this case when the Clock changes
  public void update(Observable observable, Object object) {
    if (initialDate == null) {
      initialDate = (LocalDateTime) object;
    }
    finalDate = (LocalDateTime) object;
    duration = Duration.between(initialDate, finalDate);
    myTask.updateDatesAndDuration(initialDate, finalDate);
    myTask.printTree(myTask);
  }
  
  //Create JSONObject
  public JSONObject interval() {
        JSONObject interval = new JSONObject();
        interval.put("initialDate", initialDate);
        interval.put("finalDate", finalDate);
        interval.put("task", myTask);
        return interval;
    }
}
