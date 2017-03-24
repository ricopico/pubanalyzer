import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Set;

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

    public static double calculateLevenshteinStringDistance(String s1, String s2) {
        int cost;

        /*base: empty strings*/
        if(s1.length()==0) {return (double)s2.length();}
        if(s2.length()==0) {return (double)s1.length();}

      /* test if last characters of the strings match */
        if(s1.charAt(s1.length()-1) == s2.charAt(s2.length()-1)) {
            cost = 0;
        }
        else {
            cost = 1;
        }

        /* return minimum of delete char from s, delete char from t, and delete char from both */
        return returnMinimum(calculateLevenshteinStringDistance(s1.substring(0, s1.length()-1), s2) + 1,
                calculateLevenshteinStringDistance(s1, s2.substring(0, s2.length()-1)) +1,
                calculateLevenshteinStringDistance(s1.substring(0, s1.length()-1), s2.substring(0, s2.length()-1) + cost));
    }

    public static double returnMinimum(double d1, double d2, double d3) {
        if(d1<d2 && d1<d3) {
            return d1;
        } else if(d2<d1 && d2<d3) {
            return d2;
        } else if(d3<d1 && d3<d2) {
            return d3;
        } else {
            System.err.println("no minimum");
            return -1;
        }
    }

    public static LinkedList<String> splitByDelimiter (String s, String delimiter) {
        LinkedList<String> toReturn = new LinkedList<>();
        String[] arr = s.split(delimiter);
        for(int i=0; i<arr.length; i++) {
            toReturn.add(arr[i]);
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
}
