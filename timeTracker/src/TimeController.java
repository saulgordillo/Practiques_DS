import java.time.LocalDateTime;
import java.util.Timer;

public class TimeController {
    public LocalDateTime dateTime;
    public Timer timer;


    private void tick()
    {
        dateTime = LocalDateTime.now();
        
    }
}
