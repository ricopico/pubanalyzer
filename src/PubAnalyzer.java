import java.util.LinkedList;

/**
 * Created by rich on 3/13/2017.
 */
public class PubAnalyzer {

    public static void main(String[] args) {
        try {
            Analyzer analyzer = new Analyzer(args);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
