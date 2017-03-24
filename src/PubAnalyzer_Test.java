/**
 * Created by rich on 3/23/2017.
 */
public class PubAnalyzer_Test {

    public static void main(String[] args) {

        testLevenshteinDistance();

    }

    public static void testLevenshteinDistance() {
        //test utilities: levenshtein distance
        String s1 = "test";
        String s2 = "tester";
        String s3 = "tet";
        String s4 = "testtest";

        double distance = Utilities.calculateLevenshteinStringDistance(s1, s1);
        System.out.println(s1 + " <=> " + s1 + " : " + distance);

        distance = Utilities.calculateLevenshteinStringDistance(s1, s2);
        System.out.println(s1 + " <=> " + s2 + " : " + distance);

        distance = Utilities.calculateLevenshteinStringDistance(s1, s3);
        System.out.println(s1 + " <=> " + s3 + " : " + distance);

        distance = Utilities.calculateLevenshteinStringDistance(s1, s4);
        System.out.println(s1 + " <=> " + s4 + " : " + distance);
    }
}
