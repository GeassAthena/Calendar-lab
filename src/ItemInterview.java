import java.util.Calendar;
import java.util.Date;

class ItemInterview extends ToDoItemNode {
    private String place;    //地点
    private String company;  //公司
    private String job;      //工作
    private String content;

    ItemInterview(Date dateFrom, Date dateEnd, String place, String company, String job, String content) throws ToItemException {
        super(dateFrom, dateEnd, content);
        super.types = 3;
        this.place = place;
        this.company = company;
        this.job = job;
        this.content = content;
        Calendar from = Calendar.getInstance();
        from.setTime(dateFrom);
        int fromMonth = from.get(Calendar.MONTH) + 1;
        int fromDay = from.get(Calendar.DAY_OF_MONTH);
        int fromHour = from.get(Calendar.HOUR_OF_DAY);
        int fromMinute = from.get(Calendar.MINUTE);

        Calendar end = Calendar.getInstance();
        end.setTime(dateEnd);
        int endMonth = end.get(Calendar.MONTH) + 1;
        int endDay = end.get(Calendar.DAY_OF_MONTH);
        int endHour = end.get(Calendar.HOUR_OF_DAY);
        int endMinute = end.get(Calendar.MINUTE);
        super.itemDetail = fromMonth + "月" + fromDay + "日" + fromHour + ":" + fromMinute + "到" + endMonth + "月" + endDay + "日" + endHour + ":" + endMinute + "去" + place + "在" + company + "应聘" + job + "工作。备注：" + content;
    }

    public void setId() {
        super.id = (int) (Math.random() * 100000);
    }

    //事件的父事件ID
    public void setParentID(int id) {
        super.parentId = id;
    }
}
