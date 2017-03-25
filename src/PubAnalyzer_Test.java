import javax.rmi.CORBA.Util;

/**
 * Created by rich on 3/23/2017.
 */
public class PubAnalyzer_Test {

    public static void main(String[] args) {

        //testTrimLastChar();
        testLevenshteinDistance();

    }

    public static void testTrimLastChar() {
        System.out.println(Utilities.trimLastChar("test"));
    }
    public static void testLevenshteinDistance() {
        //test utilities: levenshtein distance
        String s1 = "test";
        String s2 = "tester";
        String s3 = "tet";
        String s4 = "testtest";

        //double distance = Utilities.calculateLevenshteinStringDistance(s1, s1);
        double distance = Utilities.levenshteinDistance(s1, s1);
        System.out.println(s1 + " <=> " + s1 + " : " + distance);

        distance = Utilities.levenshteinDistance(s1, s2);
        System.out.println(s1 + " <=> " + s2 + " : " + distance);

        distance = Utilities.levenshteinDistance(s1, s3);
        System.out.println(s1 + " <=> " + s3 + " : " + distance);

        distance = Utilities.levenshteinDistance(s1, s4);
        System.out.println(s1 + " <=> " + s4 + " : " + distance);
    }
}
