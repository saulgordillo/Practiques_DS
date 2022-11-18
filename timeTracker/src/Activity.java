import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Activity {
  protected String name;
  protected LocalDateTime initialDate = null;
  protected LocalDateTime finalDate = null;
  protected Duration duration;
  protected Project projectFather;
  protected boolean isRoot = false;

  //Change DateTimeFormatter
  final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


  public Activity() {
    this.name = "";
    this.projectFather = null;
    this.duration = Duration.ofSeconds(0);
  }

  //With this function we go through the entire tree recursively to be able to see updates of dates and durations
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

  //Methods unneeded
  public abstract void calculateDuration();

  public Duration getDuration() {
    return duration;
  }

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

  public void printActivity() {
    System.out.println("Activity: " + this.name + "      " + this.initialDate.format(formatter) + "       " + this.finalDate.format(formatter) + "     " + Math.round(this.duration.getSeconds() + ((double) this.duration.getNano() / 1000000000)));
    if (this.projectFather != null) {
      this.projectFather.printActivity();
    }

  }

}
