/**
 * Created by 17637 on 2018/6/20.
 */
public class Config {
    private volatile static int id = 0;

    public static int getId() {
        return id++;
    }

}
