import java.util.ArrayList;
import java.util.List;

public class Project extends Activity {

    public List<Activity> projects;

    public Project()
    {
        this.name = "";
        this.projectFather = null;
        this.projects = new ArrayList<Activity>();
    }

    public Project(String name, Project father)
    {
        this.name = name;
        this.projectFather = father;
        this.projects = new ArrayList<Activity>();
        if(father != null)
        {
            father.addChild(this);
        }


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
    public void whoAmI()
    {
        System.out.println("I am a project");
    }
    public void drawSons()
    {
        int i = 0;
        while(i < projects.size())
        {
            System.out.print(projects.get(i).name + "\n");
            i = i + 1;
        }
    }
}
