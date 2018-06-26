package exception;

/**
 * Created by 13568 on 2018/6/1.
 */
public class DataErrorException extends Exception {
    public DataErrorException(){
        super();
    }
    public DataErrorException(String message){
        super(message);
    }
}
