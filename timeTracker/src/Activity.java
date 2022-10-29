import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Activity {

  protected String name;
  protected LocalDateTime initialDate = null;
  protected LocalDateTime finalDate = null;
  protected Duration duration = null; //Problem! what happens if as we do the iteration calculating durations we arrive to a Project leaf? Will the duration be 0?
  protected Project projectFather;
  protected boolean isRoot = false;


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

  public void addTime(Duration duration) {
    if (this.projectFather != null) {
      this.projectFather.addTime(duration);
    }

    if (this.duration == null) {
      this.duration = duration;
    } else {
      this.duration = this.duration.plus(duration);
    }
  }

  public void updateDates(LocalDateTime initialDate, LocalDateTime finalDate, Duration duration) {
    if (!this.isRoot) {
      this.projectFather.updateDates(initialDate, finalDate, duration);
    }

    if (this.initialDate == null) {
      this.initialDate = initialDate;
    }
    this.finalDate = finalDate;
    this.duration = this.duration.plus(duration.minus(this.duration));
  }

  //Methods unneeded
  public abstract void whoAmI();

  public abstract void calculateDuration();

  public Duration getDuration() {
    return duration;
  }

  public void printName() {
    System.out.print("Activity name: ");
    System.out.print(this.name);
    System.out.print("\tChild of ");
    if (this.projectFather != null) {
      System.out.print(this.projectFather.getName());
    } else {
      System.out.print("null");
    }
    System.out.print("\tInitial date: ");
    if (this.initialDate != null) {
      System.out.print(this.initialDate);
    } else {
      System.out.print("null");
    }
    System.out.print("\tFinal date: ");
    System.out.print(this.finalDate);
    System.out.print("\tDuration: ");
    if (this.duration != null) {
      System.out.print(Math.round(this.duration.getSeconds() + ((double)this.duration.getNano()/1000000000)));
    } else {
      System.out.print("null");
    }
    //String d = this.duration.format(ISO_LOCAL_TIME);
    System.out.print("\n");
  }
}
