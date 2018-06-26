
public class ToItemException extends Exception {
    @Override
    public String getMessage() {
        return "To-Do item is illegal";
    }
}
