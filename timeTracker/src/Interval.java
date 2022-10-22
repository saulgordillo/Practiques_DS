import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
  private LocalDateTime initialDate = null;
  private LocalDateTime finalDate = null;
  private Task myTask;

  public Interval(Task father)
  {
    this.myTask = father;
  }

  public LocalDateTime getInitialDate() {
    return initialDate;
  }

  public LocalDateTime getFinalDate() {
    return finalDate;
  }

  public Duration calculateInterval() {
    Duration interval = Duration.between(initialDate, finalDate);
    return interval;
  }

  public void update(Observable observable, Object object) {
    if (initialDate == null) {
      initialDate = (LocalDateTime) object;
    }
    finalDate = (LocalDateTime) object;
    Duration intervalActual = this.calculateInterval();
    myTask.addTime(intervalActual);

  }
}
