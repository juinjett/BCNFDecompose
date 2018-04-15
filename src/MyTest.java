import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyTest implements CustomizedList<Integer> {
    private List<Integer> list = new ArrayList<>();
    private int cursor = 0;

//    public boolean hasNext() {
//        return cursor < list.size();
//    }
//
//    public Integer next() {
//        if (!hasNext()) return null;
//        Integer res = list.get(cursor++);
//        return res;
//    }

    public void add(Integer i) {
        list.add(i);
    }

    public static void main(String[] args) {
        MyTest myTest = new MyTest();

        myTest.add(1);
        System.out.println(myTest.getNext());
        System.out.println(myTest.getNext());

        myTest.add(2);
        System.out.println(myTest.getNext());
        System.out.println(myTest.getNext());
    }

    @Override
    public boolean hasNext() {
        return cursor < list.size();
    }

    @Override
    public Integer getNext() {
        if (!hasNext()) return null;
        Integer res = list.get(cursor++);
        return res;
    }

    @Override
    public Integer getFirst() {
        return null;
    }

}
