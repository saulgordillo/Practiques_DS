import java.time.LocalDateTime;
import java.util.Observable;

public class TimeController extends Observable {
  public LocalDateTime dateTimeNow;

  private void tick() {
    dateTimeNow = LocalDateTime.now();
    setChanged();
    notifyObservers(dateTimeNow);
  }
}
