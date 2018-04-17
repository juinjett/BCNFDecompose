import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Relation implements CustomizedList<Relation> {
    private byte[] chars = new byte[26];
    private byte offset = 65;
    private List<Relation> list = new ArrayList<>();
    private int cursor = 0;

    private Relation() {}

    public Relation(byte[] chars) {
        this.chars = chars;
    }

    public Relation(String in_r) {
        in_r = in_r.toUpperCase();
        for (byte each: in_r.getBytes()) {
            int idx = each - offset;
            if (idx >= 0) this.chars[idx] = 1;
        }
    }

    public Relation(List<Integer> idxList) {
        for (int each: idxList) {
            this.chars[each] = 1;
        }
    }

    public int getSize() {
        int count = 0;
        for (int i=0; i<chars.length; i++) {
            if (chars[i] == 1) count++;
        }
        return count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<chars.length; i++) {
            if (chars[i] == 1) {
                sb.append((char) (i+offset)).append((char) 32);
            }
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    public boolean contains(char c){
        c = Character.toUpperCase(c);
        return chars[(int) c - offset] == 1;
    }

    public int properSubset(Relation r2) {
        int res = 0;
        byte[] r2chars = r2.getChars();
        for (int i=0; i<chars.length; i++) {
            if (chars[i] > r2chars[i]) return -1;
            if (chars[i] < r2chars[i]) res++;
        }
        return res;
    }

    public boolean equals(Relation r2) {
        return properSubset(r2) == 0;
    }

    public boolean subset(Relation r2) {
        return properSubset(r2) >= 0;
    }

    public boolean emptySet() {
        for (int i=0; i<chars.length; i++) {
            if (chars[i] == 1) return false;
        }
        return true;
    }

    public Relation union (Relation r2) {
        byte[] res = new byte[26];
        byte[] r2chars = r2.getChars();
        for (int i=0; i<res.length; i++) {
            res[i] = (byte) (chars[i] | r2chars[i]);
        }
        return new Relation(res);
    }

    public Relation intersect (Relation r2) {
        byte[] res = new byte[26];
        byte[] r2chars = r2.getChars();
        for (int i=0; i<res.length; i++) {
            res[i] = (byte) (chars[i] & r2chars[i]);
        }
        return new Relation(res);
    }

    // get relative complement of r with respect to a set r2, i.e., r2 \ r
    public Relation complement(Relation r2) {
        byte[] rComplement = new byte[26];
        byte[] res = new byte[26];
        byte[] r2chars = r2.getChars();
        byte[] universe = new byte[26];
        Arrays.fill(universe, (byte)1);
        for (int i=0; i<r2chars.length; i++) {
            rComplement[i] = (byte) (universe[i] ^ chars[i]);
            res[i] = (byte) (r2chars[i] & rComplement[i]);
        }
        return new Relation(res);
    }

    public byte[] getChars() {
        return chars;
    }

    public void getPowerSet() {
        if (emptySet()) return;
        List<Integer> idxList = new ArrayList<>();
        for (int i=0; i<chars.length; i++ ) {
            if (chars[i] == 1) idxList.add(i);
        }
        List<List<Integer>> powerIdxList = new ArrayList<>();
        recursiveHelper(idxList, new ArrayList<>(), powerIdxList, idxList.size(), 0);
        powerIdxList.remove(0);
//        System.out.println(powerIdxList);
//        System.out.println(powerIdxList.size());

        for (List<Integer> each: powerIdxList) {
            list.add(new Relation(each));
        }
    }

    public void recursiveHelper(List<Integer> idxList, List<Integer> tmpList, List<List<Integer>> powerIdxList, int length, int start) {
        if (start == length) {
            powerIdxList.add(0, new ArrayList<>(tmpList));
            return;
        }

        for (int i=start; i<length; i++) {
            tmpList.add(idxList.get(i));
            recursiveHelper(idxList, tmpList, powerIdxList, length, i+1);
            tmpList.remove(tmpList.size()-1);
        }
        powerIdxList.add(0, new ArrayList<>(tmpList));
    }

    public boolean powerSetHasNext() {
        if (list.size() == 0) getPowerSet();
        return hasNext();
    }

    public Relation powerSetFirst() {
        if (!powerSetHasNext()) return null;
        return getFirst();
    }

    public Relation powerSetNext() {
        if (!powerSetHasNext()) return null;
        return getNext();
    }

    @Override
    public boolean hasNext() {
        return cursor < list.size();
    }

    @Override
    public Relation getNext() {
        if (!hasNext()) return null;
        return list.get(cursor++);
    }

    @Override
    public Relation getFirst() {
        if (list.size() == 0) return null;
        cursor = 1;
        return list.get(0);
    }

//    public static void main(String[] args) {
//        Relation r = new Relation("c d e f");
//        Relation r2 = new Relation("C D");
//        Relation r3 = r2.complement(r);
//        System.out.println(r3.toString());
//
//        r.getPowerSet();
//    }
}
