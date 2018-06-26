import java.util.Calendar;
import java.util.Date;


 class ItemMeeting extends ToDoItemNode {
//    private Date dateFrom;
//    private Date dateEnd;
//    private String itemDetail;//概述
////    private String place;//地点
////    private String title;//题目
////    private String content;//内容
//    ItemMeeting(Date dateFrom, Date dateEnd, String itemDetail) throws ToItemException {
//        super(dateFrom, dateEnd, itemDetail);
//    }
    ItemMeeting(Date dateFrom, Date dateEnd, String place, String title, String content) throws ToItemException{
        if((dateFrom != null) && (dateEnd != null)&&(place!=null)&&(title!=null)&&(content!=null)){
            super.dateFrom=dateFrom;
            super.dateEnd=dateEnd;
            super.types = 4;
//        this.place=place;
//        this.title=title;
//        this.content=content;

            Calendar from=Calendar.getInstance();
            from.setTime(dateFrom);
            int fromMonth=from.get(Calendar.MONTH)+1;
            int fromDay=from.get(Calendar.DAY_OF_MONTH);
            int fromHour = from.get(Calendar.HOUR_OF_DAY);
            int fromMinute = from.get(Calendar.MINUTE);

            Calendar end=Calendar.getInstance();
            end.setTime(dateEnd);
            int endMonth=end.get(Calendar.MONTH)+1;
            int endDay=end.get(Calendar.DAY_OF_MONTH);
            int endHour = end.get(Calendar.HOUR_OF_DAY);
            int endMinute = end.get(Calendar.MINUTE);
            super.itemDetail=fromMonth+"月"+fromDay+"日"+fromHour+":"+fromMinute+"到"+endMonth+"月"+endDay+"日"+endHour+":"+endMinute+"在"+place+"参加会议，会议主题为"+title+"，内容为"+content;
        }else {
            System.out.println("不能有空");
        }
     }
//    public Date getDateFrom(){
//        return dateFrom;
//    }
//    public Date getDateEnd(){
//        return dateEnd;
//    }
////    public String getPlace(){
////        return place;
////    }
////    public String getTitle(){
////        return title;
////    }
////    public String getContent(){
////        return content;
////    }
//    public String getItemDetail(){
//        return itemDetail;
//    }
//事件Id
public void setId(){
    super.id = (int)(Math.random()*100000);
}
     //事件的父事件ID
     public void setParentID(int id){
         super.parentId = id;
     }
}
