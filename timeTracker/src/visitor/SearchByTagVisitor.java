package visitor;

import core.Activity;
import core.Interval;
import core.Project;
import core.Task;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;


public class SearchByTagVisitor implements Visitor {
  private final List<Activity> activitiesWithTag = new ArrayList<>();
  static Logger loggerSearchByTagVisitor = LoggerFactory.getLogger("visitor.Visitor.SearchByTagVisitor");
  private String tagToSearch;
  

  /**
   * Visit Project and see if the searching tag is contained in the List of String with the tags of a Project
   *
   * @param p
   */
  @Override
  public void visitProject(Project p) {
    System.out.print("\n");
    System.out.print("Tags inside project " + p.getName() + " : " + p.getTags());

    boolean found = false;
    int i = 0;
    while (i < p.getTags().size() && !found) {
      if (p.getTags().get(i).equalsIgnoreCase(tagToSearch)) {
        found = true;
      } else {
        i++;
      }
    }

    if (found) {
      activitiesWithTag.add(p);
    }

    for (Activity activity : p.getActivities()) {
      activity.accept(this);
    }
  }

  /**
   * Visit Task and see if the searching tag is contained in the List of String with the tags of a Task
   *
   * @param t
   */
  @Override
  public void visitTask(Task t) {
    System.out.print("\n");
    System.out.print("Tags inside task " + t.getName() + " : " + t.getTags());

    boolean found = false;
    int i = 0;
    while (i < t.getTags().size() && !found) {
      if (t.getTags().get(i).equalsIgnoreCase(tagToSearch)) {
        found = true;
      } else {
        i++;
      }
    }

    if (found) {
      activitiesWithTag.add(t);
    }
  }

  /**
   * Unused in SearchByTagVisitor because an Interval cannot have tags
   *
   * @param inter
   */
  @Override
  public void visitInterval(Interval inter) {
    // core.Interval does not have tags, so this visitor does not implement this
  }

  /**
   * Starts in root and searches for the tag implementing Visitor
   *
   * @param root
   * @param tag
   * @return List of Activity containing all activities with the tag searched
   */
  public List<Activity> searchByTag(Activity root, String tag) {
    tagToSearch = tag;
    root.accept(this);
    return activitiesWithTag;
  }
}
