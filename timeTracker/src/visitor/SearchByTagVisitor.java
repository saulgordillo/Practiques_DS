package visitor;

import core.Activity;
import core.Interval;
import core.Project;
import core.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Class which implements Visitor,
 * it's objective is to search for an asked tag
 * inside the different activities
 * and return the ones who have it.
 * Uses Visitor pattern to do the job.
 */
public class SearchByTagVisitor implements Visitor {
  static final Logger loggerSearchByTagVisitor =
      LoggerFactory.getLogger("visitor.Visitor.SearchByTagVisitor");

  private final List<Activity> activitiesWithTag = new ArrayList<>();
  private String tagToSearch;

  /**
   * Visit Project and see if the searching tag is contained in the List with the tags of a Project.
   *
   * @param p - Project which we are visiting
   */
  @Override
  public void visitProject(Project p) {
    loggerSearchByTagVisitor.debug("Tags inside Project '" + p.getName() + "' : " + p.getTags());

    boolean found = false;
    int i = 0;
    while (i < p.getTags().size() && !found) {
      if (p.getTags().get(i).equalsIgnoreCase(this.tagToSearch)) {
        found = true;
      } else {
        i++;
      }
    }

    if (found) {
      this.activitiesWithTag.add(p);
    }

    for (Activity activity : p.getActivities()) {
      activity.accept(this);
    }
  }

  /**
   * Visit Task and see if the searching tag is contained in the List with the tags of a Task.
   *
   * @param t - Task which we are visiting
   */
  @Override
  public void visitTask(Task t) {
    loggerSearchByTagVisitor.debug("Tags inside Task '" + t.getName() + "' : " + t.getTags());

    boolean found = false;
    int i = 0;
    while (i < t.getTags().size() && !found) {
      if (t.getTags().get(i).equalsIgnoreCase(this.tagToSearch)) {
        found = true;
      } else {
        i++;
      }
    }

    if (found) {
      this.activitiesWithTag.add(t);
    }
  }

  /**
   * Unused in SearchByTagVisitor because an Interval cannot have tags.
   *
   * @param inter - Interval which we are visiting
   */
  @Override
  public void visitInterval(Interval inter) {
    // Interval does not have tags, so this visitor does not implement this
  }

  /**
   * Starts in root and searches for the tag implementing Visitor.
   *
   * @param root - Project root of all projects.
   * @param tag  - Tag associated with the activities.
   * @return List of Activity containing all activities with the tag searched
   */
  public List<Activity> searchByTag(Activity root, String tag) {
    this.tagToSearch = tag;
    loggerSearchByTagVisitor.info("Tag to search: '" + this.tagToSearch + "'");

    root.accept(this);

    loggerSearchByTagVisitor.info("Activities with tag '" + this.tagToSearch + "': "
        + this.activitiesWithTag);
    return activitiesWithTag;
  }
}
