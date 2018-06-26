import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


class ItemAnniversary extends ToDoItemNode {
    private String name;//名字
    private String type;//结婚纪念日-1，生日0，节日1类型
    private String content;//描述
    //    private String itemDetail;
    private ArrayList<ItemAnniversary> items = new ArrayList<>();

    ItemAnniversary(Date dateFrom, Date dateEnd, String name, String type, String content) throws ToItemException {
        super(dateFrom, dateEnd, content);
        if ((name != null) && (type != null) && (content != null)) {
            super.types = 6;
            this.name = name;
            this.type = type;
            this.content = content;
            Calendar from = Calendar.getInstance();
            from.setTime(dateFrom);
            int fromMonth = from.get(Calendar.MONTH) + 1;
            int fromDay = from.get(Calendar.DAY_OF_MONTH);
            super.itemDetail = fromMonth + "月" + fromDay + "日是" + name + "," + content;
        } else {
            System.out.println("不能有空");
        }
    }

    private String getType() {
        return type;
    }

    //事件Id
    public void setId() {
        super.id = (int) (Math.random() * 100000);
    }

    //事件的父事件ID
    public void setParentID(int id) {
        super.parentId = id;
    }

    public ArrayList<ItemAnniversary> add() throws ToItemException {
        Calendar from = Calendar.getInstance();
        from.setTime(dateFrom);
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH) + 1;
        int fromDay = from.get(Calendar.DAY_OF_MONTH);
        if (DateUtil.isLeapYear(fromYear) && (fromMonth == 2) && (fromDay == 29)) {
            for (int i = fromYear; i < 2301; i += 4) {
                addOne(i);
            }
        } else {
            for (int i = fromYear; i < 2301; i++) {
                addOne(i);
            }
        }

        return items;
    }

    private void addOne(int i) throws ToItemException {
        Calendar from = Calendar.getInstance();
        from.setTime(dateFrom);
        int fromMonth = from.get(Calendar.MONTH) + 1;
        int fromDay = from.get(Calendar.DAY_OF_MONTH);

        Calendar end = Calendar.getInstance();
        end.setTime(dateEnd);
        int endMonth = end.get(Calendar.MONTH) + 1;
        int endDay = end.get(Calendar.DAY_OF_MONTH);

        String s1 = i + "/" + fromMonth + "/" + fromDay + " 00:00:00";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date1 = null;
        try {
            date1 = sdf1.parse(s1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String s2 = i + "/" + endMonth + "/" + endDay + " 23:59:59";
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date2 = null;
        try {
            date2 = sdf1.parse(s2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ItemAnniversary item = new ItemAnniversary(date1, date2, name, type, content);
        items.add(item);
    }

    @Override
    public boolean equals(Object obj) {
        ItemAnniversary anniversary = (ItemAnniversary) obj;
        return dateFrom.equals(anniversary.dateFrom) && dateEnd.equals(anniversary.dateEnd)
                && name.equals(anniversary.name) & type.equals(anniversary.type) && content.equals(anniversary.content);
    }

}
