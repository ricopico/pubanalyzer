import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by rich on 3/13/2017.
 */
public class Utilities {

    public static int charOccurencesInString(String s, char c) {
        int occurences=0;

        for(int i=0; i<s.length(); i++) {
            char charToExamine = s.charAt(i);
            if(charToExamine == c) {
                occurences++;
            }
        }

        return occurences;
    }

    public static double levenshteinDistance(String s, String t) {
        /*
        code from: http://people.cs.pitt.edu/~kirk/cs1501/Pruhs/Spring2006/assignments/editdistance/Levenshtein%20Distance.htm
         */
        double d[][]; // matrix
        int n; // length of s
        int m; // length of t
        int i; // iterates through s
        int j; // iterates through t
        char s_i; // ith character of s
        char t_j; // jth character of t
        int cost; // cost

        // Step 1

        n = s.length ();
        m = t.length ();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new double[n+1][m+1];

        // Step 2

        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        // Step 3

        for (i = 1; i <= n; i++) {

            s_i = s.charAt (i - 1);

            // Step 4

            for (j = 1; j <= m; j++) {

                t_j = t.charAt (j - 1);

                // Step 5

                if (s_i == t_j) {
                    cost = 0;
                }
                else {
                    cost = 1;
                }

                // Step 6

                d[i][j] = returnMinimum (d[i-1][j]+1, d[i][j-1]+1, d[i-1][j-1] + cost);

            }

        }

        // Step 7

        return d[n][m];
    }

    public static double returnMinimum(double d1, double d2, double d3) {
        double min = d1;
        if(d2<min) {
            min=d2;
        }
        if(d3<min) {
            min=d3;
        }
        return min;
    }

    public static LinkedList<String> splitByDelimiter (String s, String delimiter) {
        LinkedList<String> toReturn = new LinkedList<>();
        String[] arr = s.split(delimiter);
        for(int i=0; i<arr.length; i++) {
            toReturn.add(arr[i].trim());
        }
        return toReturn;
    }

    public static LinkedList<String> textFileToStringList(String pathToFile) throws Exception {
        LinkedList<String> toReturn = new LinkedList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            toReturn.add(line);

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                toReturn.add(line);

            }
            String everything = sb.toString();
        }

        return toReturn;
    }

    public static void writeStringListToFile(LinkedList<String> stringList, String pathToOutput) throws Exception {
        PrintWriter writer = new PrintWriter(pathToOutput, "UTF-8");
        for(String s : stringList) {
            writer.println(s);
        }
        writer.close();
    }
    public static void writeStringSetToFile(Set<String> stringSet, String pathToOutput) throws Exception {
        PrintWriter writer = new PrintWriter(pathToOutput, "UTF-8");
        for(String s : stringSet) {
            writer.println(s);
        }
        writer.close();
    }
    public static String trimLastChar(String s){
        if(s.length()==0) {
            return s;
        }
        String toReturn = s.substring(0, s.length()-1);
        return toReturn;
    }
    public static String flatten(String s) {
        return s.toLowerCase();
    }
    public static HashMap<Character, Integer> getCharFrequencyMapFromString(String s) {
        HashMap<Character, Integer> charToFreq = new HashMap<Character, Integer>();
        for (int i=0; i<s.length(); i++) {
            Character c = s.charAt(i);
            int update = 0;
            if(charToFreq.keySet().contains(c)) {
                update = charToFreq.get(c);
                update++;
            }
            charToFreq.put(c, update);
        }
        return charToFreq;
    }
    public static LinkedList<Character> sortCharByFrequency(HashMap<Character, Integer> charToFreqSuperMap) {
        //sort the map into a list, in descending order
        HashMap<Integer, Set<Character>> freqToCharBucket = new HashMap<>();
        for(Character c : charToFreqSuperMap.keySet()) {
            Integer key = charToFreqSuperMap.get(c);
            if(!freqToCharBucket.keySet().contains(key)) {
                freqToCharBucket.put(key, new HashSet<Character>());
            }
            freqToCharBucket.get(key).add(c);
        }

        //sort by bucket frequemcy
        LinkedList<Set<Character>> sortedCharSets = new LinkedList<>();
        for(Integer i : freqToCharBucket.keySet()) {
            if(i>=sortedCharSets.size()) {
                //sortedCharSets.add(i, new HashSet<Character>());
                growLinkedList(i, sortedCharSets);
            }
            else if(sortedCharSets.get(i) == null) {
                sortedCharSets.add(i, new HashSet<Character>());
            }
            for(Character c : freqToCharBucket.get(i)) {
                sortedCharSets.get(i).add(c);
            }
        }

        LinkedList<Character> sortedCharList = new LinkedList<>();  //right now sort ties by alphabetical order.  can adjust later.
        for(int i=0; i<sortedCharSets.size(); i++) {
            if(sortedCharSets.get(i) == null) {
                continue;
            }

            Character[] toSort = new Character[sortedCharSets.get(i).size()];
            int j=0;
            for(Character toAdd : sortedCharSets.get(i)) {
                toSort[j] = toAdd;
                j++;
            }
            Arrays.sort(toSort);

            for(int k=0; k<toSort.length; k++) {
                sortedCharList.add(toSort[k]);
            }
        }

        return sortedCharList;
    }

    public static LinkedList<Character> sortCharByFrequency(HashSet<HashMap<Character, Integer>> charToFreqMaps) {
        //merge into one map
        HashMap<Character, Integer> charToFreqSuperMap = new HashMap<>();
        for(HashMap<Character, Integer> map : charToFreqMaps) {
            mergeCharToFreqMapIntoFirstMap(charToFreqSuperMap, map);
        }

        return sortCharByFrequency(charToFreqSuperMap);
    }
    public static HashMap<Character, Integer> mergeCharToFreqMapIntoFirstMap(HashMap<Character, Integer> map1, HashMap<Character, Integer> map2) {
        for(Character c : map2.keySet()) {
            int freq2 = map2.get(c);
            int update;
            if(map1.keySet().contains(c)) {
                update = map1.get(c) + freq2;
                map1.put(c, update);
            } else {
                map1.put(c, freq2);
            }
        }
        return map1;
    }
    public static void growLinkedList(int indexToGrowTo, LinkedList<Set<Character>> toGrow) {
        int numEltsToAdd = indexToGrowTo - (toGrow.size()-1);
        for(int i=0; i<numEltsToAdd; i++) {
            toGrow.add(new HashSet<Character>());
        }
    }
    public static double averageRatioLevenshteinStringCompare(String s1, String s2) {

        //calculate levenshtein distance
        double d = levenshteinDistance(s1, s2);
        //calculate ratios
        double s1Ratio = d/s1.length();
        double s2Ratio = d/s2.length();
        //take average
        double average = (s1Ratio = s2Ratio)/2;

        return average;
    }
}
