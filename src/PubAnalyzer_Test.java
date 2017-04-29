import javax.rmi.CORBA.Util;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Created by rich on 3/23/2017.
 */
public class PubAnalyzer_Test {

    public static void main(String[] args) {

        //testTrimLastChar();
        //testLevenshteinDistance();
        testCharFrequencySort();

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

    public static void testCharFrequencySort() {

        //public static LinkedList<Character> sortCharByFrequency(HashSet<HashMap<Character, Integer>> charToFreqMaps) {

        HashSet<HashMap<Character, Integer>> charToFreqMaps = new HashSet<>();
        LinkedList<Character> testListForSortedness = new LinkedList<>();

        HashMap<Character, Integer> map1 = new HashMap<>();
        map1.put('a', 4);
        map1.put('b', 23);
        map1.put('c', 1);
        map1.put('d', 55);
        map1.put('f', 23);
        map1.put('e', 23);

        //test a single map, just for sort.
        charToFreqMaps.add(map1);
        testListForSortedness = Utilities.sortCharByFrequency(charToFreqMaps);
        for(Character c : testListForSortedness) {
            System.out.print(c);    //correct: cabefd
        }

        System.out.println("\n");

        //test multiple maps
        HashMap<Character, Integer> map2 = new HashMap<>();
        map2.put('a', 43);
        map2.put('b', 20);
        map2.put('c', 1);
        map2.put('d', 50);
        map2.put('f', 500);
        map2.put('g', 11);
        map2.put('x', 12);

        charToFreqMaps.add(map2);
        testListForSortedness = Utilities.sortCharByFrequency(charToFreqMaps);
        for(Character c : testListForSortedness) {
            System.out.print(c);
        }

    }


}
