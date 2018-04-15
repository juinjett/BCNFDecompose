import java.util.ArrayList;
import java.util.List;

public class FdList implements CustomizedList<Fd> {
    private List<Fd> list = new ArrayList<>();
    private int cursor = 0;
    private Fd firstViolation;

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Fd each: list) {
            sb.append(each.toString() + "\n");
        }
        return sb.toString();
    }

    public void insert(Fd f) {
        list.add(f);
    }

    @Override
    public Fd getFirst() {
        if (list.size() == 0) return null;
        cursor = 1;
        return list.get(0);
    }

    @Override
    public boolean hasNext() {
        return cursor < list.size();
    }

    @Override
    public Fd getNext() {
        if (!hasNext()) return null;
        Fd res = list.get(cursor++);
        return res;
    }

//     computes the closure with respect to a list of functional dependencies
    public Relation closure(Relation r) {   // here r could be a subset of input relation, usually lhs
        Relation rPlus = new Relation(r.getChars());
        int prevSize = rPlus.getSize();
        while (true) {
            for (Fd each: list) {
                Relation lhs = each.getLhs();
                Relation rhs = each.getRhs();
                if (lhs.subset(rPlus)) {
                    rPlus = rPlus.union(rhs);
                }
            }
            int currSize = rPlus.getSize();
            if (currSize == prevSize) break;
            prevSize = currSize;
        }
        return rPlus;
    }

    // get keys by passing in an input relation r
//    public RelList getKeys(Relation r) {   // r is the input relation
//        RelList keyList = new RelList();
//        while (r.powerSetHasNext()) {
//            Relation curr = r.powerSetNext();
//            if(curr.equals(r)) keyList.insert(closure(curr));
//        }
//        return keyList;
//    }

    // In the FdList, find the FIRST Fd that has the BCNF violation
    public Fd findViolation(Relation r) {   //here r is current input relation
        if (list.size() == 0) return null;
        if (firstViolation != null) return firstViolation;
        for (int i=0; i<list.size(); i++) {
            Fd fd = list.get(i);
            if (BCNFviolation(fd, r)) {
                firstViolation = fd;
                return fd;
            }
        }
        return null;
    }

    public boolean hasViolation(Relation r) {   //here r is current input relation
        firstViolation = findViolation(r);
        return firstViolation != null;
    }

    // for every relation lhs, check if this functional dependency is a BCNF violation with respect to the given set of
    // attributes of relation s
    public boolean BCNFviolation(Fd fd, Relation r) {
        Relation lhsPlus = closure(fd.getLhs());
        if (!r.subset(lhsPlus)) return true;
        return false;
    }

    public RelList relationDecompose(Fd fd, Relation r) {   //here r is current input relation
        Relation lhs = fd.getLhs();
        Relation lhsPlus = closure(lhs);
        Relation z = lhsPlus.complement(r);

        RelList decomposedList = new RelList();
        decomposedList.insert(lhsPlus);
        decomposedList.insert(z.union(lhs));
        return decomposedList;
    }

    public FdList fdProjection(Relation r) {    //here r is the subR after decomposition
        FdList res = new FdList();

        while (r.powerSetHasNext()) {
            Relation lhs = r.powerSetNext();
            Relation lhsPlus = closure(lhs);
            Relation rhs = lhs.complement(lhsPlus).intersect(r);
            if (!rhs.emptySet()) res.insert(new Fd(lhs, rhs));
        }
        return res;
    }



}
