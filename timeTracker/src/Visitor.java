public interface Visitor {
  void visitProject(Project p);
  void visitTask(Task t);
  void visitInterval(Interval inter);
}
