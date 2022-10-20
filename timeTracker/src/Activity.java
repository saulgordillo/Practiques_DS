import java.time.LocalDateTime;

abstract public class Activity {
    String name;
    LocalDateTime initialHour;
    LocalDateTime finalHour;
    LocalDateTime duration; //Problem! what happens if as we do the iteration calculating durations we arrive to a Project leaf? Will the duration be 0?

    Project projectFather;


    public abstract void duration();
}
