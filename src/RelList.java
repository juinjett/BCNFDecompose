import java.util.ArrayList;
import java.util.List;

public class RelList implements CustomizedList<Relation> {
    private List<Relation> list = new ArrayList<>();
    private int cursor = 0;

    @Override
    public boolean hasNext() {
        return cursor < list.size();
    }

    @Override
    public Relation getNext() {
        if(!hasNext()) return null;
        Relation res = list.get(cursor++);
        return res;
    }

    @Override
    public Relation getFirst() {
        if (list.size() == 0) return null;
        cursor = 1;
        return list.get(0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Relation each: list) {
            sb.append(each.toString() + "\n");
        }
        return sb.toString();
    }

    public void insert(Relation r) {
        list.add(r);
    }
}
