import java.util.Calendar;
import java.util.Date;

class ItemAppoint extends ToDoItemNode {
    private String place;//地点
    private String people;//成员
    private String content;//内容

    ItemAppoint(Date dateFrom, Date dateEnd, String place, String people, String content) throws ToItemException {
        super(dateFrom, dateEnd, content);
        if ((place != null) && (people != null) && (content != null)) {
            super.types = 1;
            this.place = place;
            this.people = people;
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
            super.itemDetail = fromMonth + "月" + fromDay + "日" + fromHour + ":" + fromMinute + "到" + endMonth + "月" + endDay + "日" + endHour + ":" + endMinute + "与" + people + "在" + place + "约会，内容为" + content;
        } else {
            System.out.println("不能有空");
        }
    }

    //事件Id
    public void setId() {
        super.id = (int) (Math.random() * 100000);
    }

    //事件的父事件ID
    public void setParentID(int id) {
        super.parentId = id;
    }

    public String getPlace() {
        return place;
    }

    public String getPeople() {
        return people;
    }

    public String getContent() {
        return content;
    }
}
