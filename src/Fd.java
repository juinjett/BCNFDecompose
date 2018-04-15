public class Fd {
    private Relation lhs;
    private Relation rhs;

    protected Fd () {}

    public Fd(String string) {
        if (!string.contains("->")) return;
        String[] parsed = string.split("->");
        Relation lhs = new Relation(parsed[0].trim());
        Relation rhs = new Relation(parsed[1].trim());
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Fd(Relation lhs, Relation rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public String toString() {
        return lhs.toString() + " -> " + rhs.toString();
    }

    public Relation getLhs() {
        return lhs;
    }

    public Relation getRhs() {
        return rhs;
    }

    // check if this functional dependency is a BCNF violation with respect to the given set of
    // attributes of relation s
//    public boolean BCNFviolation(Relation s) {
//    }


}
