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
   * Default Activity constructor
   */
  public Activity() {
    this.name = "";
    this.projectFather = null;
    this.duration = Duration.ofSeconds(0);
  }

  /**
   * @return Name of the Activity class object
   */
  public String getName() {
    return this.name;
  }

  /**
   * Updates dates and durations through the entire tree
   *
   * @param initialDate
   * @param finalDate
   */
  public void updateDatesAndDuration(LocalDateTime initialDate, LocalDateTime finalDate) {
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
   * @return Duration of the Activity class object
   */
  public Duration getDuration() {
    return duration;
  }

  /**
   * Creates JSON item from the activity JSONObject
   *
   * @param act
   */
  //Create JSONObject
  public void activityToJSON(JSONObject act) {
    act.put("duration", Math.round(this.duration.getSeconds() + ((double) this.duration.getNano() / 1000000000)));
    if (initialDate != null) {
      act.put("initialDate", initialDate.format(formatter));
    }
    if (finalDate != null) {
      act.put("finalDate", finalDate.format(formatter));
    }

    act.put("name", name);
  }

  /**
   * Print Activity info (Name, Initial date, Final date and Duration)
   */
  public void printActivity() {
    System.out.println("Activity: " + this.name + "      " + this.initialDate.format(formatter) + "       " + this.finalDate.format(formatter) + "     " + Math.round(this.duration.getSeconds() + ((double) this.duration.getNano() / 1000000000)));
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
    System.out.print(Math.round(this.duration.getSeconds() + ((double) this.duration.getNano() / 1000000000)));
    //String d = this.duration.format(ISO_LOCAL_TIME);
    System.out.print("\n");
  }
}
