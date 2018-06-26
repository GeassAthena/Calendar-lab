import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
//约会1，课程2，面试3，会议4，旅程5，纪念日6，其他7；

//存储事件单元类，用于存储单个事件的所有信息
public class ToDoItemNode implements Serializable {
    public int types;
    public Date dateFrom;
    public Date dateEnd;
    public int id;// 事件的ID
    public int parentId; //事件的父事件ID；
    public Date completeTime;
    public String itemDetail;
    public boolean important = false;//是否重要
    public boolean urgent = false;//是否紧急
    public boolean state = false;//是否完成
    public int condition;//-1未开始；0进行中；1过期；
    public Date remindTime;//提醒开始时间
    public long remindInterval;//每次提醒间隔
    public boolean parent = true;//是否父事件,父事件为true，子事件false
    public int remindWay;//提醒方式
    public boolean hasTime = true;//是否设置时间
    private ArrayList<Integer> childrenIds;

    //    根据输入参数构造对象，对象不合法时无法构造
    public ToDoItemNode(Date dateFrom, Date dateEnd, String itemDetail) throws ToItemException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1800, Calendar.JANUARY, 1, 0, 0, 0);
        Date dateStart = calendar.getTime();
        calendar.set(2300, Calendar.DECEMBER, 31, 23, 59, 0);
        Date dateTo = calendar.getTime();
        if (dateFrom == null || dateEnd == null || DateUtil.after(dateFrom, dateEnd) || DateUtil.before(dateFrom, dateStart) || DateUtil.after(dateEnd, dateTo) || itemDetail == null || itemDetail.equals("")) {
            throw new ToItemException();
        } else {
            this.dateFrom = dateFrom;
            this.dateEnd = dateEnd;
            this.itemDetail = itemDetail;
        }
        childrenIds = new ArrayList<>();
        judgeCondition();//判断状态
    }

    public ToDoItemNode() {

    }

    public Date getCompleteTime() {
        return getCompleteTime();
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public String getItemDetail() {
        return itemDetail;
    }


    public int getRemindWay() {
        return remindWay;
    }

    public void setRemindWay(int remindWay) {
        this.remindWay = remindWay;
    }


    public boolean getHasTime() {
        return hasTime;
    }


    public void setImportant(boolean important) {
        this.important = important;
    }

    public boolean isImportant() {
        return important;
    }

    public void setUrgent(boolean urgent) {
        this.urgent = urgent;
    }

    public boolean isUrgent() {
        return urgent;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean getState() {
        return state;
    }

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }

    public Date getRemindTime() {
        return remindTime;
    }

    public void setRemindInteral(long remindInteral) {
        this.remindInterval = remindInteral;
    }

    public long getRemindInterval() {
        return remindInterval;
    }

    public int getCondition() {
        judgeCondition();
        return condition;

    }

    private void judgeCondition() {
        Date date = new Date();
        if (date.before(dateFrom)) {
            condition = -1;
        } else if (date.after(dateEnd)) {
            condition = 1;
        } else {
            condition = 0;
        }
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }

    public boolean isParent() {
        return parent;
    }

    public int getFatherID() {
        return parentId;
    }

    public void setParentId(int id) {
        this.parentId = id;
    }

    public void addChild(int childId) {
        childrenIds.add(childId);
    }
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
