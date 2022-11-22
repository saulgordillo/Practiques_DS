package core;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import visitor.Visitor;

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
   * @param finalDate - Final date of the activity
   */
  public void updateDatesAndDuration(LocalDateTime initialDate, LocalDateTime finalDate) {
    if (!this.isRoot) {
	  loggerActivity.info("update duration from projects below project root");
      this.calculateDuration();
      this.projectFather.updateDatesAndDuration(initialDate, finalDate);
    } else {
	  loggerActivity.info("update duration from project root");
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
  //Create JSONObject
  @SuppressWarnings("checkstyle:AbbreviationAsWordInName")
  public void activityToJSON(JSONObject act) {
	loggerActivity.info("Put information in correct format inside JSON");
    act.put("duration", Math.round(this.duration.getSeconds()
            + ((double) this.duration.getNano() / 1000000000)));
    if (initialDate != null) {
      act.put("initialDate", initialDate.format(formatter));
    } else {
      loggerActivity.warn("this task exists");
    }
    if (finalDate != null) {
      act.put("finalDate", finalDate.format(formatter));
    } else {
      loggerActivity.warn("this task has finished");
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
  
  
  public void printName() {
    loggerActivity.info("Activity: \t");
    loggerActivity.info(this.name);
    //System.out.print("\tChild of ");
    //if (this.projectFather != null) {
    //System.out.print(this.projectFather.getName());
    //} else {
    //System.out.print("null");
    //}
    loggerActivity.info("\tInitial date: \t");
    loggerActivity.info(this.initialDate.toString());
    loggerActivity.info("\tFinal date: \t");
    loggerActivity.info(this.finalDate.toString());
    loggerActivity.info("\tDuration: \t");
    System.out.print(Math.round(this.duration.getSeconds()
            + ((double) this.duration.getNano() / 1000000000)));
    //String d = this.duration.format(ISO_LOCAL_TIME);
    System.out.print("\n");
  }
}
