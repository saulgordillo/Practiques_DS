import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
  private LocalDateTime initialDate = null;
  private LocalDateTime finalDate = null;
  private Task myTask;

  public Interval(Task father) {
    this.myTask = father;
  }

  public LocalDateTime getInitialDate() {
    return initialDate;
  }

  public LocalDateTime getFinalDate() {
    return finalDate;
  }

  public Duration calculateInterval() {
    return Duration.between(initialDate, finalDate);
  }

  // Update dates when the Observable has changed, in this case when the Clock changes
  public void update(Observable observable, Object object) {
    if (initialDate == null) {
      initialDate = (LocalDateTime) object;
    }
    finalDate = (LocalDateTime) object;
    myTask.updateDates(initialDate, finalDate, calculateInterval());
    myTask.printTree(myTask);
  }
}
