import java.util.Calendar;
import java.util.Date;

class ItemOther extends ToDoItemNode {
    private Date dateFrom;
    private Date dateEnd;
    private String itemDetail;//概述
    boolean hasTime = true;//是否设置时间

    ItemOther(Date dateFrom, Date dateEnd, String content, Boolean hasTime) throws ToItemException {
        this.dateEnd = dateEnd;
        super.types = 7;
        this.dateFrom = dateFrom;
        this.itemDetail = content;
        this.hasTime = hasTime;
        if ((dateFrom != null) && (dateEnd != null) && (content != null)) {
            super.dateFrom = dateFrom;
            super.dateEnd = dateEnd;
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
            super.itemDetail = fromMonth + "月" + fromDay + "日" + fromHour + ":" + fromMinute + "到" + endMonth + "月" + endDay + "日" + endHour + ":" + endMinute + ",内容为" + content;
        } else {
            System.out.println("不能有空");
        }

    }

    ItemOther(String content) {
        if (content != null) {
            this.hasTime = false;
            super.itemDetail = content;
            //初始化
            super.dateFrom = new Date();
            long time = dateFrom.getTime();
            time += 10000;
            super.dateEnd.setTime(time);
        } else {
            System.out.println("不能有空");
        }

    }

    private boolean isHasTime() {
        return hasTime;
    }
////未设置时间时获取当天时间先后
//    public void setDate(){
//        Date date=new Date();
//        Calendar d=Calendar.getInstance();
//        d.setTime(date);
//        int Year=d.get(Calendar.YEAR);
//        int Month=d.get(Calendar.MONTH)+1;
//        int Day=d.get(Calendar.DAY_OF_MONTH);
//        this.dateFrom=new Date(Year,Month,Day,0,0,0);
//
//    }

    //    public Date getDateFrom(){
//        return dateFrom;
//    }
//    public Date getDateEnd(){
//        return dateEnd;
//    }
////    public String getContent(){
////        return content;
////    }
//    public String getItemDetail(){
//        return itemDetail;
//    }
//事件Id
    public void setId() {
        super.id = (int) (Math.random() * 100000);
    }

    //事件的父事件ID
    public void setParentID(int id) {
        super.parentId = id;
    }

    public void setHasTime(boolean hasTime) {
        this.hasTime = hasTime;
    }
}
