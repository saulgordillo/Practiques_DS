import java.util.List;

public class Task extends Activity{
    private List<Interval> intervals;

    public void duration() {
        for (int i=0; i<intervals.size(); i++)
        {
            //We are interating through intervals to sum every interval  to get the duration of this task
            this.duration.plusHours(intervals.get(i).getIntervalHour());
            this.duration.plusMinutes(intervals.get(i).getIntervalMinute());
            this.duration.plusSeconds(intervals.get(i).getIntervalSecond());
        }
    }
    public void initHours() {
        this.initialHour = intervals.get(0).getInferior();
        this.finalHour = intervals.get(intervals.size()-1).getSuperior();
    }
}
