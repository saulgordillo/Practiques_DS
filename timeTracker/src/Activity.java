import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Activity {

  protected String name;
  protected LocalDateTime initialHour = null;
  protected LocalDateTime finalHour = null;
  protected Duration duration = null; //Problem! what happens if as we do the iteration calculating durations we arrive to a Project leaf? Will the duration be 0?
  protected Project projectFather;

  public Activity() {
    this.name = "";
    this.projectFather = null;
  }

  public Activity(Activity aux) {
    this.name = aux.name;
    this.projectFather = aux.projectFather;
  }

  public Activity(String name, Project father) {
    this.name = name;
    this.projectFather = father;
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
      this.duration.plus(duration);
    }
  }

  //Methods unneeded
  public abstract void whoAmI();

  public abstract void duration();

  public abstract void printName();
}
