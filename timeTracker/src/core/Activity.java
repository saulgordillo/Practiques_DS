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

public abstract class Activity {
  static Logger loggerActivity = LoggerFactory.getLogger("core.Activity");

  //Change DateTimeFormatter
  final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  protected String name;
  protected LocalDateTime initialDate = null;
  protected LocalDateTime finalDate = null;
  protected Duration duration;
  protected Project projectFather;
  protected boolean isRoot = false;
  protected List<String> tags = new ArrayList<>();

  /**
   * Default Activity constructor.
   */
  public Activity() {
    this.name = "";
    this.projectFather = null;
    this.duration = Duration.ofSeconds(0);
  }

  /**
   * Getter that returns the name of the activity.
   *
   * @return Name of the activity
   */
  public String getName() {
    return this.name;
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
   * Creates JSON item from the activity JSONObject.
   *
   * @param act - Objecte JSON
   */
  public void activityToJSON(JSONObject act) {
    loggerActivity.debug("Activity to JSONObject");
    act.put("duration", Math.round(this.duration.getSeconds()
        + ((double) this.duration.getNano() / 1000000000)));

    if (initialDate != null) {
      act.put("initialDate", initialDate.format(formatter));
    } else {
      loggerActivity.debug("Already exists in JSON: Not adding");
    }
    if (finalDate != null) {
      act.put("finalDate", finalDate.format(formatter));
    } else {
      loggerActivity.debug("Already exists in JSON: Not adding");
    }

    act.put("name", name);
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
  }

  public abstract void accept(Visitor visitor);

  /**
   * Print Activity info
   */
  public void printInfo() {
    loggerActivity.info("Activity: \t");
    loggerActivity.info(this.name);
    loggerActivity.info("\tChild of ");
    if (this.projectFather != null) {
      loggerActivity.info(this.projectFather.getName());
    } else {
      loggerActivity.info("null");
    }
    loggerActivity.info("\tInitial date: \t");
    loggerActivity.info(this.initialDate.toString());
    loggerActivity.info("\tFinal date: \t");
    loggerActivity.info(this.finalDate.toString());
    loggerActivity.info("\tDuration: \t");
    loggerActivity.info("" + Math.round(this.duration.getSeconds() + ((double) this.duration.getNano() / 1000000000)));
  }
}
