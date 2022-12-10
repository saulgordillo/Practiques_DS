package core;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import provider.IdProvider;
import visitor.Visitor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Observable;
import java.util.Observer;

/**
 * Class which implements Observer to update when Clock changes,
 * this class saves the info of an interval of time of a Task.
 */
public class Interval implements Observer {
  static final Logger loggerInterval = LoggerFactory.getLogger("core.Observer.Interval");

  //Change DateTimeFormatter
  final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private final Task taskFather;
  private LocalDateTime initialDate = null;
  private LocalDateTime finalDate = null;
  private Duration duration;
  private boolean active = false;
  private int id;

  /**
   * Constructor to create an Interval with a Task father.
   *
   * @param father - Father activity of the interval.
   */
  public Interval(Task father) {
    this.taskFather = father;
    this.duration = Duration.ofSeconds(0);
    IdProvider uniqueIdProviderInstance = IdProvider.getInstance();
    this.id = uniqueIdProviderInstance.getId();
  }

  /**
   * @return Duration of the Interval class object
   */
  public Duration getDuration() {
    return duration;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Print Interval info (Initial date, Final date and Duration).
   */
  public void printInterval() {
    System.out.println("Interval: " + "                  "
        + this.initialDate.format(formatter) + "   "
        + this.finalDate.format(formatter) + "            "
        + Math.round(this.duration.getSeconds()
        + ((double) this.duration.getNano() / 1000000000)));

    taskFather.printActivity();

  }

  /**
   * Accept Visitor to visit the Interval.
   *
   * @param visitor - The object which makes reference to the visitor
   */
  public void accept(Visitor visitor) {
    visitor.visitInterval(this);
  }

  /**
   * Updates date when the Observable has changed, in this case when the Clock instance changes.
   *
   * @param observable - the observable object.
   * @param object     - an argument passed to the {@code notifyObservers}
   *                   method.
   */
  public void update(Observable observable, Object object) {
    if (initialDate == null) {
      loggerInterval.debug("Initialize Date");
      initialDate = (LocalDateTime) object;
      initialDate = initialDate.minus(2, ChronoUnit.SECONDS);
    }
    loggerInterval.debug("Update Date and Duration");
    finalDate = (LocalDateTime) object;
    duration = Duration.between(initialDate, finalDate);
    taskFather.updateDatesAndDuration(initialDate, finalDate);
    this.printInterval();
  }

  /**
   * @return JSONObject containing the info of the Interval class object
   */
  public JSONObject toJson() {
    JSONObject json = new JSONObject();
    json.put("class", "interval");
    json.put("id", this.id);
    json.put("initialDate", this.initialDate == null ? JSONObject.NULL : formatter.format(this.initialDate));
    json.put("finalDate", this.finalDate == null ? JSONObject.NULL : formatter.format(this.finalDate));
    json.put("duration", Math.round(this.duration.getSeconds() + ((double) this.duration.getNano() / 1000000000)));
    json.put("active", this.active);
    return json;
  }
}
