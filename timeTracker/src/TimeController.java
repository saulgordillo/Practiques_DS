import java.time.LocalDateTime;
import java.util.Observable;

public class TimeController extends Observable {
  public LocalDateTime dateTimeNow;
  private static TimeController instance;
   
   
  private void tick() {
    dateTimeNow = LocalDateTime.now();
    setChanged();
    notifyObservers(dateTimeNow);
  }
  
  //Singleton Toogle pattern
  private TimeController() {

  }
  public static TimeController getInstance() {
        
		if (instance == null) {
			instance = new TimeController();
		}
		
		return instance;
    }
}
