import java.util.List;

public class Project extends Activity {

    public List<Activity> projects;

    public Project()
    {
        this.name = "";
        this.projectFather = null;
    }

    public Project(String name, Project father)
    {
        this.name = name;
        this.projectFather = father;
    }
    public void addChild(Activity child)
    {
        this.projects.add(child);
    }
    public String getName()
    {
        return this.name;
    }
    public void duration() {
        //Can a project have a duration if there is no tasks in it?
        //Yes -Lucas-

    }
    //this methods test all the methods and abstract system.
    public void printName()
    {
        System.out.print(this.name);
    }

    public void whoAmI()
    {
        System.out.println("I am a project");
    }
}
