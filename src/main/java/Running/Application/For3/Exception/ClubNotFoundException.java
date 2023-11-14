package Running.Application.For3.Exception;

public class ClubNotFoundException extends RuntimeException{

    public ClubNotFoundException (){
        super("Club not found");
    }
}
