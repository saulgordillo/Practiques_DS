import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
  private LocalDateTime initialDate = null;
  private LocalDateTime finalDate = null;
  private Duration duration;
  private final Task myTask;
  //Change DateTimeFormatter
  final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public Interval(Task father) {
    this.myTask = father;
    this.duration = Duration.ofSeconds(0);
  }

  public Duration getDuration() {
    return duration;
  }

  public void printInterval() {
    System.out.println("Interval: " + "                  " + this.initialDate.format(formatter) + "   " + this.finalDate.format(formatter) + "            " + Math.round(this.duration.getSeconds() + ((double) this.duration.getNano() / 1000000000)));

    myTask.printActivity();

  }

  // Update dates when the Observable has changed, in this case when the Clock changes
  public void update(Observable observable, Object object) {
    if (initialDate == null) {
      initialDate = (LocalDateTime) object;
      initialDate = initialDate.minus(2, ChronoUnit.SECONDS);
    }
    finalDate = (LocalDateTime) object;
    duration = Duration.between(initialDate, finalDate);
    myTask.updateDatesAndDuration(initialDate, finalDate);
    this.printInterval();
    //myTask.printTree(myTask);
  }

  //Create JSONObject
  public JSONObject intervalToJSON() {
    JSONObject interval = new JSONObject();
    interval.put("initialDate", initialDate.format(formatter));
    interval.put("finalDate", finalDate.format(formatter));
    interval.put("task", myTask.name);
    interval.put("duration", Math.round(this.duration.getSeconds() + ((double) this.duration.getNano() / 1000000000)));
    return interval;
  }
}
