import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ItemClass extends ToDoItemNode{
    private Date dateFrom;
    private Date dateEnd;
    private String place;//地点
    private String name;//课程名
    private String remark;//备注
    private String content;//内容
    private String teacher;//老师
    private int duration;//持续时间
    private int week;//周几；0~6
//    private String itemDetail;
    private ArrayList<ItemClass> items=new ArrayList<>();
    ItemClass(Date dateFrom, Date dateEnd, String name, String place, String content, String remark,String teacher,int duration,int week)throws ToItemException{
        if ((dateFrom != null) && (dateEnd != null)&&(name!=null)&&(place!=null)&&(content!=null)&&(remark!=null)&&(teacher!=null)&&(duration>0)&&(week<7)&&(week>-1)){
            super.types = 2;
            super.dateFrom=dateFrom;
            super.dateEnd=dateEnd;
            this.dateFrom=dateFrom;
            this.dateEnd=dateEnd;
            this.place=place;
            this.name=name;
            this.remark=remark;
            this.content=content;
            this.teacher=teacher;
            this.duration=duration;
            this.week=week;
            String[] weeks={"周一","周二","周三","周四","周五","周六","周日"};
            Calendar from=Calendar.getInstance();
            from.setTime(dateFrom);
            int fromHour = from.get(Calendar.HOUR_OF_DAY);
            int fromMinute = from.get(Calendar.MINUTE);

            Calendar end=Calendar.getInstance();
            end.setTime(dateEnd);
            int endHour = end.get(Calendar.HOUR_OF_DAY);
            int endMinute = end.get(Calendar.MINUTE);
            super.itemDetail=weeks[week-1]+fromHour+":"+fromMinute+"至"+endHour+":"+endMinute+"到"+place+"上"+teacher+"老师教的"+name+",内容为"+content+"，备注："+remark;
        }else{
            System.out.println("不能有空");
        }
    }
//    private int getDuration(){
//        return duration;
//    }
//    private int getWeek(){
//        return week;
//    }
    //返回duration个对象list
    public ArrayList<ItemClass> add() throws ToItemException {
        for(int i=0;i<duration;i++){
            Date date1=new Date(dateFrom.getTime()+(long)(i*60*60*1000*24*7));
            Date date2=new Date(dateFrom.getTime()+(long)(i*60*60*1000*24*7));
            ItemClass item = new ItemClass(date1,date2,name, place,content,remark,teacher,duration,week);
            items.add(item);
        }
        return items;
    }
    //事件Id
    public void setId(){
        super.id = (int)(Math.random()*100000);
    }
    //事件的父事件ID
    public void setParentID(int id){
        super.parentId = id;
    }

    @Override
    public boolean equals(Object obj) {
        ItemClass anniversary = (ItemClass) obj;
        return dateFrom.equals(anniversary.dateFrom) && dateEnd.equals(anniversary.dateEnd)
                && name.equals(anniversary.name) & place.equals(anniversary.place) && content.equals(anniversary.content)
                && remark.equals(anniversary.remark) && teacher.equals(anniversary.teacher);
    }
}
