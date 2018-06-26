import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


//事件存储处理的工具类，实现增删查操作，并存储事件
public class ToDoListUtil {
    private static ArrayList<ToDoItemNode> toDoItemList = new ArrayList<>();
    private static ArrayList<String> remindList = new ArrayList<>();

    //    插入事件，根据参数插入
    public static boolean insert(ToDoItemNode toDoItemNode) {
        if (toDoItemNode == null) {
            return false;
        }
        if (0 == toDoItemList.size()) {
            toDoItemList.add(toDoItemNode);
        } else {
            insertByOrder(toDoItemNode);
        }
        return true;
    }

    //    查询事件，根据输入参数，返回查询结果
    public static ArrayList<ToDoItemNode> search(Date dateFrom, Date dateEnd) {
        if (null == dateFrom || null == dateEnd || DateUtil.after(dateFrom, dateEnd)) {
            return null;
        }
        ArrayList<ToDoItemNode> arrayList = new ArrayList<>();
        int length = toDoItemList.size();
        ToDoItemNode temp;
        for (int i = 0; i < length; i++) {
            temp = toDoItemList.get(i);
            //搜索补充
            //完成了的没有时间的待办事项
            Date date = new Date();
            if (!temp.hasTime && temp.getState() && temp.getCompleteTime().after(dateFrom) && temp.getCompleteTime().before(dateEnd)) {
                    arrayList.add(temp);
            } else if (!temp.hasTime && date.after(dateFrom) && date.before(dateEnd)) {
                //进行中的没有时间的待办事项
                    arrayList.add(temp);
            } else {
                boolean isFromValid = ((!(DateUtil.before(temp.getDateFrom(), dateFrom))) && (!(DateUtil.after(temp.getDateFrom(), dateEnd))));
                boolean isEndValid = ((!(DateUtil.before(temp.getDateEnd(), dateFrom))) && (!(DateUtil.after(temp.getDateEnd(), dateEnd))));
                boolean isBetween = (DateUtil.before(temp.getDateFrom(), dateFrom)) && DateUtil.after(temp.getDateEnd(), dateEnd);
                if (isFromValid || isEndValid || isBetween) {
                    arrayList.add(temp);
                }
            }
        }
        return arrayList.size()==0?null:arrayList;
}

    //    删除事件
    public static ToDoItemNode delete(ToDoItemNode toDoItemNode) {
        if (toDoItemList.size() == 0) {
            return null;
        }
        return toDoItemList.remove(toDoItemNode) ? toDoItemNode : null;
    }

    private static void insertByOrder(ToDoItemNode toDoItemNode) {
        if (!DateUtil.after(toDoItemNode.getDateFrom(), toDoItemList.get(0).getDateFrom())) {
            toDoItemList.add(0, toDoItemNode);
        } else if (!DateUtil.before(toDoItemNode.getDateFrom(), toDoItemList.get(toDoItemList.size() - 1).getDateFrom())) {
            toDoItemList.add(toDoItemNode);
        } else {
            for (int i = 1; i < toDoItemList.size(); i++) {
                boolean isAfterOrEqual = !(DateUtil.before(toDoItemNode.getDateFrom(), toDoItemList.get(i - 1).getDateFrom()));
                boolean isBeforeOrEqual = !(DateUtil.after(toDoItemNode.getDateFrom(), toDoItemList.get(i).getDateFrom()));
                if (isAfterOrEqual && isBeforeOrEqual) {
                    toDoItemList.add(i, toDoItemNode);
                    break;
                }
            }
        }
    }

    public static ArrayList<ToDoItemNode> getToDoItemList() {
        return toDoItemList;
    }

    public static void setToDoItemList() {
        toDoItemList = new ArrayList<>();
    }

    public static ArrayList<String> getRemindList() {
        return remindList;
    }

    public static void setRemindList(String string) {
        remindList.add(string);
    }

}
