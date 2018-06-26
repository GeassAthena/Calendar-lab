import java.util.Calendar;
import java.util.Date;

public class ItemTravel extends ToDoItemNode {
         private Date dateFrom;
         private Date dateEnd;
         private String place;//地点
         private String way;//交通方式
         private String number;//车次/航班号
         private String content;
         private String itemDetail;
     public ItemTravel(Date dateFrom,Date dateEnd,String way,String place,String number,String content)throws ToItemException{
         super.dateFrom=dateFrom;
         super.dateEnd=dateEnd;
         super.types = 5;
         this.way = way;
         this.place = place;
         this.number = number;
         this.content = content;
         Calendar from=Calendar.getInstance();
         from.setTime(dateFrom);
         int fromMonth=from.get(Calendar.MONTH)+1;
         int fromDay=from.get(Calendar.DAY_OF_MONTH);
         int fromHour = from.get(Calendar.HOUR);
         int fromMinute = from.get(Calendar.MINUTE);

         Calendar end=Calendar.getInstance();
         end.setTime(dateEnd);
         int endMonth=end.get(Calendar.MONTH)+1;
         int endDay=end.get(Calendar.DAY_OF_MONTH);
         int endHour = end.get(Calendar.HOUR);
         int endMinute = end.get(Calendar.MINUTE);
         super.itemDetail=fromMonth+"月"+fromDay+"日"+fromHour+":"+fromMinute+"到"+endMonth+"月"+endDay+"日"+endHour+":"+endMinute+"坐车次/航班号为"+number+"的"+way+"去"+place+"旅游。备注："+content;
     }
     //事件Id
    //事件的父事件ID
    public void setParentID(int id){
        super.parentId = id;
    }
}
