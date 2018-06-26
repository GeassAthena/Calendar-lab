import java.io.*;

/**
 * Created by 17637 on 2018/5/12.
 */
public class ItemPersistence {
    //将待办事项写入文件
    public static void outPutItems() {
        File file = new File("outPut.txt");
        try {
            if (!file.exists())
                file.createNewFile();
            FileOutputStream os = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(os);
            if (ToDoListUtil.getToDoItemList().size() != 0) {
                for (int i = 0; i < ToDoListUtil.getToDoItemList().size(); i++) {
                    oos.writeObject(ToDoListUtil.getToDoItemList().get(i));
                    oos.flush();
                }
                oos.close();
            }
        } catch (IOException e) {
            System.out.println();
        }
    }

    //从文件读取待办事项
    public static void inPutItems() throws ClassNotFoundException {
        File file = new File("outPut.txt");
        if (!file.exists()) {
            return;
        }
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            ObjectInputStream ios = new ObjectInputStream(is);
            ToDoItemNode node = null;
            while ((node = (ToDoItemNode) ios.readObject()) != null) {
                ToDoListUtil.getToDoItemList().add(node);
            }
        } catch (IOException e) {
            System.out.println();
        }
    }
}
