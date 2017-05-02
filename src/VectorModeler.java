import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by rich on 4/27/2017.
 */
public class VectorModeler {
    private PublicationObjectHashTable poHashTable;
    private HashMap<String, String> titleToBigString;
    private HashMap<String, String> authorNameToVectorMap;
    private HashMap<String, HashMap<Character, Integer>> authorNameToCharFreqMap;

    public VectorModeler(PublicationObjectHashTable poht) {
        this.poHashTable = poht;

        this.titleToBigString = new HashMap<>();
        populateTitleToBigString();

        this.authorNameToCharFreqMap = new HashMap<>();
        populateAuthorNameToCharFreqMap();

        this.authorNameToVectorMap = new HashMap<>();
        populateAuthorNameToVectorMap();
    }

    private void populateTitleToBigString () {
        //gather all publication objects from the hash table with this name
        for(String title : poHashTable.getTitleToPublicationObject().keySet()) {
            String bigString = "";

            bigString += title;
            bigString += poHashTable.getTitleToPublicationObject().get(title).getPublisher();
            bigString += poHashTable.getTitleToPublicationObject().get(title).getSource();
            bigString += poHashTable.getTitleToPublicationObject().get(title).getAuthorListAsString();

            //remove spaces, trim
            bigString = bigString.trim();
            bigString = bigString.replace(" ", "");

            this.titleToBigString.put(title, bigString);
        }
    }

    private void populateAuthorNameToCharFreqMap() {
        for(String title : poHashTable.getTitleToPublicationObject().keySet()) {
            for (String authorOfPub : poHashTable.getTitleToPublicationObject().get(title).getAuthorsList()) {
                //produce a char freq map of the pub object
                HashMap<Character, Integer> pubObjectCharFreqMap = Utilities.getCharFrequencyMapFromString(this.titleToBigString.get(title));

                //check to insert or merge into existing record
                if(authorNameToCharFreqMap.keySet().contains(authorOfPub)) {
                    //merge
                    HashMap<Character, Integer> mergedMap = Utilities.mergeCharToFreqMapIntoFirstMap(authorNameToCharFreqMap.get(authorOfPub), pubObjectCharFreqMap);
                    this.authorNameToCharFreqMap.put(authorOfPub, mergedMap);
                } else {
                    //insert
                    this.authorNameToCharFreqMap.put(authorOfPub, pubObjectCharFreqMap);
                }
            }
        }
    }
    private void populateAuthorNameToVectorMap() {
        for(String name : authorNameToCharFreqMap.keySet()) {
            LinkedList<Character> charVector = Utilities.sortCharByFrequency(authorNameToCharFreqMap.get(name));
            this.authorNameToVectorMap.put(name, charVector.toString());
        }
    }

    public void vectorCompare(double ratioMatchCeiling) {
        //posit possible matches based on the threshold
        HashMap<String, Set<String>> authorToPossibleAliases = new HashMap<>();
        for(String authorFocus : authorNameToVectorMap.keySet()) {
            String vectorFocus = authorNameToVectorMap.get(authorFocus);
            for(String authorCompare : authorNameToVectorMap.keySet()) {
                if(!authorCompare.equals(authorFocus)) {
                    String vectorCompare = authorNameToVectorMap.get(authorCompare);
                    double averageLevenshteinDistance = Utilities.averageRatioLevenshteinStringCompare(vectorCompare, vectorFocus);
                    if(averageLevenshteinDistance > 0 && averageLevenshteinDistance <= ratioMatchCeiling) {
                        if(!authorToPossibleAliases.keySet().contains(authorFocus)) {
                            authorToPossibleAliases.put(authorFocus, new HashSet<>());
                        }
                        authorToPossibleAliases.get(authorFocus).add(authorCompare);

                    }
                }
            }
        }


    }
}
