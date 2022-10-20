import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;

public class Interval implements Observer {
  private LocalDateTime inferior;
  private LocalDateTime superior;

  public LocalDateTime getInferior() {
    return inferior;
  }

  public LocalDateTime getSuperior() {
    return superior;
  }

  public LocalDateTime getInterval() {
    LocalDateTime result = superior;
    result.minusHours(inferior.getHour());
    result.minusMinutes(inferior.getMinute());
    result.minusSeconds(inferior.getSecond());
    return result;
  }

  public int getIntervalHour() {
    LocalDateTime res = getInterval();

    return res.getHour();
  }

  public int getIntervalMinute() {
    LocalDateTime res = getInterval();

    return res.getMinute();
  }

  public int getIntervalSecond() {
    LocalDateTime res = getInterval();

    return res.getSecond();
  }

  public void update(Observable observable, Object object) {

  }
}
