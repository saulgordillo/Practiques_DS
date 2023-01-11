package core;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import visitor.Visitor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that contains the shared
 * attributes and methods between Project and Task,
 * also implements Composite pattern.
 */
public abstract class Activity {
  static final Logger loggerActivity = LoggerFactory.getLogger("core.Activity");

  // Change DateTimeFormatter
  final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  protected String name;
  protected LocalDateTime initialDate = null;
  protected LocalDateTime finalDate = null;
  protected Duration duration;
  protected Project projectFather;
  protected boolean isRoot = false;
  protected List<String> tags = new ArrayList<>();
  protected int id;

  /**
   * Default Activity constructor.
   */
  public Activity() {
    this.duration = Duration.ofSeconds(0);
    this.initialDate = LocalDateTime.now();
  }

  public abstract JSONObject toJson(int depth);

  /**
   * Getter that returns the name of the Activity.
   *
   * @return Name of the Activity
   */
  public String getName() {
    return this.name;
  }

  /**
   * Getter that returns the name of the father of this Activity.
   *
   * @return Name of the father of the Activity
   */
  public String getFatherName() {
    return this.projectFather.getName();
  }

  /**
   * Updates dates and durations through the entire tree.
   *
   * @param initialDate - Initial date of the activity
   * @param finalDate   - Final date of the activity
   */
  public void updateDatesAndDuration(LocalDateTime initialDate, LocalDateTime finalDate) {
    loggerActivity.debug("Update Duration from Projects upwards until Project 'root'");
    if (!this.isRoot) {
      this.calculateDuration();
      this.projectFather.updateDatesAndDuration(initialDate, finalDate);
    } else {
      this.calculateDuration();
    }

    if (this.initialDate == null) {
      this.initialDate = initialDate;
    }
    this.finalDate = finalDate;
  }

  public abstract void calculateDuration();

  /**
   * Getter that returns the duration of the activity.
   *
   * @return Duration of the activity
   */
  public Duration getDuration() {
    return duration;
  }

  /**
   * Print Activity info (Name, Initial date, Final date and Duration).
   */
  public void printActivity() {
    System.out.println("Activity: " + this.name + "      "
        + this.initialDate.format(formatter) + "       "
        + this.finalDate.format(formatter) + "     "
        + Math.round(this.duration.getSeconds()
        + ((double) this.duration.getNano() / 1000000000)));

    if (this.projectFather != null) {
      this.projectFather.printActivity();
    }

    this.logInfo();
  }

  public abstract void accept(Visitor visitor);

  /**
   * Print Activity info.
   */
  public void logInfo() {
    loggerActivity.trace("Activity: " + this.getName());
    if (this.projectFather != null) {
      loggerActivity.trace("Child of " + this.getFatherName());
    } else {
      loggerActivity.trace("Child of null");
    }
    loggerActivity.trace("Initial date: " + this.initialDate.format(formatter));
    loggerActivity.trace("Final date: " + this.finalDate.format(formatter));
    loggerActivity.trace("Duration: " + Math.round(this.duration.getSeconds()
        + ((double) this.duration.getNano() / 1000000000)));
  }

  protected void toJson(JSONObject json) {
    json.put("id", this.id);
    json.put("name", this.name);
    json.put("initialDate", this.initialDate == null ? JSONObject.NULL : this.formatter.format(this.initialDate));
    json.put("finalDate", this.finalDate == null ? JSONObject.NULL : this.formatter.format(this.initialDate));
    json.put("duration", Math.round(this.duration.getSeconds() + ((double) this.duration.getNano() / 1000000000)));
  }

  public abstract Activity findActivityById(int id);
}
