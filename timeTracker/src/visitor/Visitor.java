package visitor;

import core.Interval;
import core.Project;
import core.Task;

public interface Visitor {
  void visitProject(Project p);
  void visitTask(Task t);
  void visitInterval(Interval inter);
}
