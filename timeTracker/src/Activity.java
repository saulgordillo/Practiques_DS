import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class Activity {
  protected String name;
  protected LocalDateTime initialDate = null;
  protected LocalDateTime finalDate = null;
  protected Duration duration = null;
  protected Project projectFather;
  protected boolean isRoot = false;

  //Change DateTimeFormatter
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


  public Activity() {
    this.name = "";
    this.projectFather = null;
    this.duration = duration.ofSeconds(0);
  }

  public Activity(Activity aux) {
    this.name = aux.name;
    this.projectFather = aux.projectFather;
    this.duration = duration.ofSeconds(0);
  }

  public Activity(String name, Project father) {
    this.name = name;
    this.projectFather = father;
    this.duration = duration.ofSeconds(0);
  }

  //abstract void addChild(Activity child);
  public void printTree(Activity node) {
    if (node.projectFather != null) {
      printTree(node.projectFather);
    }
    node.printName();
  }

  public String getName() {
    return this.name;

  }

  //Amb aquesta funció recorrem tot l'arbre recursivament per poder ver update de les dates i duracions.
  public void updateDatesAndDuration(LocalDateTime initialDate, LocalDateTime finalDate) {
    if (!this.isRoot) {
      this.calculateDuration();
      this.projectFather.updateDatesAndDuration(initialDate, finalDate);
    } else if (this.isRoot) {
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
  public JSONObject activity(JSONObject act) {
    act.put("duration", Math.round(this.duration.getSeconds() + ((double) this.duration.getNano() / 1000000000)));
        if (initialDate != null) {
            act.put("initialDate", initialDate.format(formatter));
        }
        if (finalDate != null) {
            act.put("finalDate", finalDate.format(formatter));
        }

        act.put("name", name.toString());

    return act;
  }

  public void printActivity() {
    System.out.println("Activity: " + this.name + "      " + this.initialDate.format(formatter) + "       " + this.finalDate.format(formatter) + "     " + Math.round(this.duration.getSeconds() + ((double) this.duration.getNano() / 1000000000)));
    if (this.projectFather != null) {
      this.projectFather.printActivity();
    }

  }

  public void printName() {
    System.out.print("Activity: \t");
    System.out.print(this.name);
    //System.out.print("\tChild of ");
    //if (this.projectFather != null) {
    //System.out.print(this.projectFather.getName());
    //} else {
    //System.out.print("null");
    //}
    System.out.print("\tInitial date: \t");
    System.out.print(this.initialDate);
    System.out.print("\tFinal date: \t");
    System.out.print(this.finalDate);
    System.out.print("\tDuration: \t");
    System.out.print(Math.round(this.duration.getSeconds() + ((double) this.duration.getNano() / 1000000000)));
    //String d = this.duration.format(ISO_LOCAL_TIME);
    System.out.print("\n");
  }
}
