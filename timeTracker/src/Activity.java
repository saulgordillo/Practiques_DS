import java.time.LocalDateTime;

abstract public class Activity {
    String name;
    LocalDateTime initialHour;
    LocalDateTime finalHour;
    LocalDateTime duration; //Problem! what happens if as we do the iteration calculating durations we arrive to a Project leaf? Will the duration be 0?

    Project projectFather;

    public Activity()
    {
        this.name="";
        this.projectFather=null;
    }
    public Activity(Activity aux)
    {
        this.name= aux.name;
        this.projectFather=aux.projectFather;
    }
    public Activity(String name, Project father)
    {
        this.name = name;
        this.projectFather = father;
    }
    //abstract void addChild(Activity child);
    public void printThree(Activity node)
    {
        if(node.projectFather != null)
        {
            printThree(node.projectFather);
        }
        node.printName();
    }



    //Methods unneeded

    abstract void whoAmI();
    public abstract void duration();

    public Project getProjectFather(){ return this.projectFather; }
    public void printName()
    {
        System.out.print(this.name); System.out.print("\n");
    }

    public Activity myFather()
    {
        return this.projectFather;
    }
}
