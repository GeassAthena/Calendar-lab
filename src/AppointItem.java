import java.util.Calendar;
import java.util.Date;

public class AppointItem extends ToDoItemNode{
    private Date dateFrom;
    private Date dateEnd;
    private String itemDetail;//概述
    private String place;//地点
    private String people;//成员
    private String content;//内容
    AppointItem(Date dateFrom, Date dateEnd, String itemDetail) throws ToItemException {
        super(dateFrom, dateEnd, itemDetail);
    }
    AppointItem(Date dateFrom, Date dateEnd, String place,String people,String content){
        this.dateFrom=dateFrom;
        this.dateEnd=dateEnd;
        this.place=place;
        this.people=people;
        this.content=content;

        Calendar from=Calendar.getInstance();
        from.setTime(dateFrom);
        int fromMonth=from.get(Calendar.MONTH)+1;
        int fromDay=from.get(Calendar.DAY_OF_MONTH);
        int fromHour = from.get(Calendar.HOUR);
        int fromMinute = from.get(Calendar.MINUTE);

        Calendar end=Calendar.getInstance();
        from.setTime(dateEnd);
        int endMonth=end.get(Calendar.MONTH)+1;
        int endDay=end.get(Calendar.DAY_OF_MONTH);
        int endHour = end.get(Calendar.HOUR);
        int endMinute = end.get(Calendar.MINUTE);
        this.itemDetail=fromMonth+"月"+fromDay+"日"+fromHour+":"+fromMinute+"到"+endMonth+"月"+endDay+"日"+endHour+":"+endMinute+"与"+people+"在"+place+"约会，内容为"+content;
    }
    public Date getDateFrom(){
        return dateFrom;
    }
    public Date getDateEnd(){
        return dateEnd;
    }
    public String getPlace(){
        return place;
    }
    public String getPeople(){
        return people;
    }
    public String getContent(){
        return content;
    }
    public String getItemDetail(){
        return itemDetail;
    }
}
