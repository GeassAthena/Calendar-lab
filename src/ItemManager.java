/**
 * Created by 13568 on 2018/6/1.
 */

import exception.DataErrorException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class ItemManager {
    private final static Set<String> overlappedTypes = new HashSet<>(); //不允许重叠的待办事项类型

    private ItemManager() {

    }

    static class ManagerInstance {
        final static ItemManager manager = new ItemManager();
    }

    public static ItemManager getInstance() {
        return ManagerInstance.manager;
    }

    //待办事项重叠
    //（会议，课程，约会，面试，旅程）不能出现时间重叠情况
    private boolean isOverlapping(ToDoItemNode node) throws DataErrorException {
        ArrayList<ToDoItemNode> arrayList = ToDoListUtil.search(node.dateFrom, node.dateEnd);
        if (arrayList != null && (node.types == 1 || node.types == 2 || node.types == 3 || node.types == 4 || node.types == 5)) {
            for (ToDoItemNode node1 : arrayList) {
                if (node1.types == 1 || node1.types == 2 || node1.types == 3 || node1.types == 4 || node1.types == 5)
                    throw new DataErrorException("会议，课程，约会，面试，旅程等类型不允许时间重叠！");
            }
        }
        return true;
    }

    public ToDoItemNode getItemByID(int id) {
        for (ToDoItemNode node : ToDoListUtil.getToDoItemList()) {
            if (node.id == id) {
                return node;
            }
        }
        return null;

    }


    //添加待办事项
    public boolean addItem(ToDoItemNode node) throws DataErrorException {
        boolean added = true;
        if (isOverlapping(node)) {
            if (node instanceof ItemAnniversary) {
                ItemAnniversary item = (ItemAnniversary) node;
                ArrayList<ItemAnniversary> list = null;
                try {
                    list = item.add();
                    list.remove(item);
                    for (int i = 0; i < list.size(); i++) {
                        added &= ToDoListUtil.insert(list.get(i));
                    }
                } catch (ToItemException e) {
                    System.out.println();
                }
            } else if (node instanceof ItemClass) {
                ItemClass item = (ItemClass) node;
                ArrayList<ItemClass> list = null;
                try {
                    list = item.add();
                    list.remove(item);
                    for (int i = 0; i < list.size(); i++) {
                        added &= ToDoListUtil.insert(list.get(i));
                    }
                } catch (ToItemException e) {
                    System.out.println();
                }
            } else {
                added &= ToDoListUtil.insert(node);
                ItemPersistence.outPutItems();
            }
        }
        return added;
    }

    //添加子待办事项
    public void addChildItem(ToDoItemNode node) throws DataErrorException {
        if (node == null)
            throw new DataErrorException("添加失败");
        ToDoItemNode father = getItemByID(node.getFatherID());
        if (father == null)
            throw new DataErrorException("添加失败");

        if (!father.isParent())
            throw new DataErrorException("子待办事项不能再添加子待办事项！");

        //会议，课程，约会，面试 不能相互包含.
        if ((node.types == 1) && (father.types == 2 || father.types == 3 || father.types == 4))
            throw new DataErrorException("会议，课程，约会，面试等为互斥类型！");
        else if ((node.types == 2) && (father.types == 1 || father.types == 3 || father.types == 4))
            throw new DataErrorException("会议，课程，约会，面试等为互斥类型！");
        else if ((node.types == 3) && (father.types == 1 || father.types == 2 || father.types == 4))
            throw new DataErrorException("会议，课程，约会，面试等为互斥类型！");
        else if ((node.types == 4) && (father.types == 1 || father.types == 3 || father.types == 3))
            throw new DataErrorException("会议，课程，约会，面试等为互斥类型！");

        //父待办事项必须有起止时间，添加的子待办事项时间必须在父待办事项的时间范围内
        Date fatherFrom = father.getDateFrom();
        Date fatherTo = father.getDateEnd();
        Date from = node.getDateFrom();
        Date to = node.getDateEnd();
        if (fatherFrom == null || fatherTo == null) {
            throw new DataErrorException("未设置时间的代办事项不能添加子待办事项");
        }
        if (from == null || to == null) {
            throw new DataErrorException("未设置时间的待办事项不能添加为子待办事项");
        }
        if (from.before(fatherFrom) || to.after(fatherTo)) {
            throw new DataErrorException("子待办事项的时间应在父待办事项时间范围内");
        }
        ToDoListUtil.insert(node);
        ItemPersistence.outPutItems();

    }
}

