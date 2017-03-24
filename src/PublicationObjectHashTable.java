import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by rich on 3/15/2017.
 */
public class PublicationObjectHashTable {

    private HashMap<String, PublicationObject> titleToPublicationObject;

    public PublicationObjectHashTable() {
        titleToPublicationObject = new HashMap<>();
    }

    public void addPublicationObject(PublicationObject po) {
        titleToPublicationObject.put(po.getTitle(), po);
    }

    public HashMap<String, PublicationObject> getTitleToPublicationObject() {
        return titleToPublicationObject;
    }

    public Set<PublicationObject> getPublicationObjectsByYear(LinkedList<String> years) {
        HashSet<PublicationObject> toReturn = new HashSet<>();

        for(String title : titleToPublicationObject.keySet()) {
            if(years.contains(titleToPublicationObject.get(title).getYear())) {
                toReturn.add(titleToPublicationObject.get(title));
            }
        }
        return toReturn;
    }

    //this function cleans the database from bad pub objects
    public void cleanPublicationObjectTable(){
        Set<PublicationObject> toRemove = new HashSet<>();
        for(String title : titleToPublicationObject.keySet()) {
            //if title is over half "?" then remove
            int numQuestionMarks = Utilities.charOccurencesInString(title, '?');
            if(numQuestionMarks == 0) {
                continue;
            }
            double ratio = title.length()/numQuestionMarks;
            if(ratio>=.5) {
                toRemove.add(titleToPublicationObject.get(title));
            }
        }
        for(PublicationObject pObject : toRemove) {
            titleToPublicationObject.remove(pObject.getTitle());
        }
    }

    //this function cleans pub objects of bad author compositions
    public void cleanAuthorsList() {
        for(String title : titleToPublicationObject.keySet()) {
            PublicationObject toClean = titleToPublicationObject.get(title);
            String authorString = toClean.getAuthorsString();
            authorString = authorString.replace("...\"","");
            authorString = authorString.replace("\"","");

            //TODO: remove question mark authors
            if(authorString.contains("?")) {
                toClean.prettyPrint();
            }

            authorString = authorString.trim();

            toClean.setAuthorsString(authorString);
            toClean.setAuthorsListFromString(authorString);
        }
    }
    /*TODO
    1. flatten titles
    2. flatten authors
    3. detect possible name redundancy, merge
     */
}
