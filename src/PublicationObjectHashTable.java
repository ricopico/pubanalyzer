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

            //operations to the overall strings
            String authorString = toClean.getAuthorsString();

            //remove ...
            authorString = authorString.replace("...\"","");
            authorString = authorString.replace("\"","");

            authorString = authorString.trim();

            toClean.setAuthorsString(authorString);
            toClean.setAuthorsListFromString(authorString);

            //operations to individual authors
            Set<String> authorsToRemove = new HashSet<>();
            for(String author : toClean.getAuthorsList()) {
                /*
                remove empty authors
                 */
                if(author.isEmpty()) {
                    authorsToRemove.add(author);
                }
                if(author.equals(" ")) {
                    authorsToRemove.add(author);
                }

                /*
                remove question mark authors
                 */
                //get question mark ratio
                int numQuestionMarks = Utilities.charOccurencesInString(author, '?');
                if(numQuestionMarks == 0) {
                    continue;
                }
                double ratio = author.length()/numQuestionMarks;
                if(ratio == 1) {
                    authorsToRemove.add(author);
                }
            }

            /*
            check for redundancy
             */

            LinkedList<String> cleanedList = new LinkedList<>();
            for(String author : toClean.getAuthorsList()) {
                if(!authorsToRemove.contains(author)) {
                    cleanedList.add(author);
                }
            }

            toClean.setAuthorsList(cleanedList);
            toClean.setAuthorsStringFromList(cleanedList);

            //check for redundant authors
            Set<String> seenAuthors = new HashSet<>();
            Set<String> redundantAuthors = new HashSet<>();
            for(String author : toClean.getAuthorsList()) {
                if(seenAuthors.contains(author)) {
                    redundantAuthors.add(author);
                } else {
                    seenAuthors.add(author);
                }
            }
            cleanedList = new LinkedList<>();//reset the list
            for(String author : toClean.getAuthorsList()) {
                if(redundantAuthors.contains(author)) {
                    if(!cleanedList.contains(author)) {
                        cleanedList.add(author);
                    }
                } else {
                    cleanedList.add(author);
                }
            }
            toClean.setAuthorsList(cleanedList);
            toClean.setAuthorsStringFromList(cleanedList);
        }
    }
    public void guessAuthorMatches() {
        //check for author potential matches
        Set<String> seenAuthors = new HashSet<>();
        for(String title : titleToPublicationObject.keySet()) {
            for(String author : titleToPublicationObject.get(title).getAuthorsList()) {
                for (String seenAuthor : seenAuthors) {
                    //calculate levenshtein distance
                    double d = Utilities.levenshteinDistance(author, seenAuthor);
                    if(d<=1 && d>0) {
                        System.out.println(d + " : " + author + " <=> " + seenAuthor);
                    }

                }

            }
            seenAuthors.addAll(titleToPublicationObject.get(title).getAuthorsList());
            //TODO: Finish this...
        }


        //vector model
        VectorModeler vm = new VectorModeler(this);

    }
    /*TODO
    3. detect possible name redundancy, merge
     */
}
