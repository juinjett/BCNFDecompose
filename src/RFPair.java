public class RFPair {
    private Relation r;
    private FdList fdList;

    public RFPair(Relation r, FdList fdList) {
        this.r = r;
        this.fdList = fdList;
    }

    public Relation getRelation() {
        return r;
    }

    public FdList getFdList() {
        return fdList;
    }

    public boolean hasBCNFViolation() {
        return fdList.hasViolation(r);
    }

    public RFPair[] decompose() {
        RFPair[] res = new RFPair[2];
        int idx = 0;
        Fd fd = fdList.findViolation(r);
        System.out.println(fd.toString());
        RelList decomposedList = fdList.relationDecompose(fd, r);
        while (decomposedList.hasNext()) {
            Relation subR = decomposedList.getNext();
            FdList subFdList = fdList.fdProjection(subR);
            res[idx++] = new RFPair(subR, subFdList);
        }

        return res;
    }

    public String toString() {
        return r.toString() + ": \n" + fdList.toString();
//        return r.toString() + ": \n";
    }
}
