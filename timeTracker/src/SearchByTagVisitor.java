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
      if (p.getTags().get(i).toLowerCase() == tagToSearch) {
        found = true;
      } else {
        i++;
      }
    }

    if (found) {
      activitiesWithTag.add(p);
    }

    /*
    if (Arrays.asList(p.getTags()).contains(tagToSearch)) {
      activitiesWithTag.add(p);
    }
    */

    for (Activity activity : p.activities) {
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
      if (t.getTags().get(i).toLowerCase() == tagToSearch) {
        found = true;
      } else {
        i++;
      }
    }

    if (found) {
      activitiesWithTag.add(t);
    }

    /*
    if (Arrays.asList(t.getTags()).contains(tagToSearch)) {
      activitiesWithTag.add(t);
    }
    */
  }

  @Override
  public void visitInterval(Interval inter) {
    // Interval does not have tags, so this visitor does not implement this
  }

  public List<Activity> searchByTag(Activity root, String tag) {
    tagToSearch = tag.toLowerCase();
    root.accept(this);
    return activitiesWithTag;
  }
}
