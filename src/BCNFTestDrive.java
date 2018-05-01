import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class BCNFTestDrive {
    public static Set<String> BCNFDecomposition(RFPair rf) {
        Set<String> res = new HashSet<>();
        if (rf == null) {
            res.add("Empty RFPair, invalid input");
            return res;
        }
        Stack<RFPair> stack = new Stack<>();
        stack.push(rf);

        while (!stack.isEmpty()) {
            RFPair curr = stack.pop();
//            System.out.println(curr.toString());
            if (curr.hasBCNFViolation()) {
                RFPair[] twoPairs = curr.decompose();
//                System.out.println("step");
//                System.out.println(twoPairs[0].toString());
//                System.out.println(twoPairs[1].toString());
                stack.push(twoPairs[0]);
                stack.push(twoPairs[1]);
            }
            else {
                res.add(curr.toString());
            }
        }

        return res;
    }

    public static RFPair getRFPair(String string) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(string));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Relation r = null;
        FdList fdList = new FdList();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            if (!line.contains("->")) {
                r = new Relation(line);
            }
            else {
                fdList.insert(new Fd(line));
            }
        }
        scanner.close();
        if (r == null) return null;
        return new RFPair(r, fdList);
    }

    public static void write(Set<String> resList, String string) {
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new File(string));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (String each: resList) {
            printWriter.println(each);
        }
        printWriter.close();
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            throw new Exception("Usage: <input dir> <output dir>");
        }
        String inPath = args[0];
        String outPath = args[1];
        RFPair rf = getRFPair(inPath);
        Set<String> res = BCNFDecomposition(rf);
        write(res, outPath);
    }
}
