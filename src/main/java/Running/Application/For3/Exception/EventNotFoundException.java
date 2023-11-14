package Running.Application.For3.Exception;

public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(){
        super("Event doesn't exist");
    }
}
