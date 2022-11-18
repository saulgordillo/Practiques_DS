package visitor;

import core.Activity;
import core.Interval;
import core.Project;
import core.Task;

import java.util.ArrayList;
import java.util.List;


public class SearchByTagVisitor implements Visitor {
  private final List<Activity> activitiesWithTag = new ArrayList<>();
  private String tagToSearch;

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

  @Override
  public void visitInterval(Interval inter) {
    // core.Interval does not have tags, so this visitor does not implement this
  }

  public List<Activity> searchByTag(Activity root, String tag) {
    tagToSearch = tag;
    root.accept(this);
    return activitiesWithTag;
  }
}
