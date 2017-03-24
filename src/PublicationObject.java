import java.util.LinkedList;

/**
 * Created by rich on 3/13/2017.
 */
public class PublicationObject {

    //private stuff
    private int cites = -1;
    private String authorsString = null;
    private String year = null;
    private LinkedList<String> authorsList = null;
    private String title = null;
    private String source = null;
    private String publisher = null;
    private String articleURL = null;
    private int GSRank = -1;

    //constructor
    public PublicationObject() {
        System.out.println("congrats!  You made a pub object!");
    }
    public PublicationObject(String entry) {
        /*
            0=cites
            1=authors
            2=title
            3=year
            4=source
            5=publisher
            6=url
            7=citesURL
            8-GSRank
        */
        LinkedList<String> componentList = Utilities.splitByDelimiter(entry, "\t");
        this.setCites(Integer.parseInt(componentList.get(0)));
        this.setTitle(componentList.get(2));
        this.setYear(componentList.get(3));
        this.setSource(componentList.get(4));
        this.setPublisher(componentList.get(5));
        this.setArticleURL(componentList.get(6));
        this.setGSRank(Integer.parseInt(componentList.get(8)));
        this.setAuthorsString(componentList.get(1));
        this.setAuthorsListFromString(componentList.get(1));

    }
    public PublicationObject(String authorsString, String year, String title, String source) {
        this.setAuthorsString(authorsString);
        this.setTitle(title);
        this.setSource(source);
        this.setYear(year);
    }

    //pretty print
    public void prettyPrint() {
        System.out.println("----------------------------------------------------------------\n");
        System.out.println("Title:\t\t" + title + "\n");
        System.out.println("Year:\t\t" + year + "\n");
        System.out.println("Authors:\t\t" + authorsList + "\n");
    }

    //cloner
    public PublicationObject clone(PublicationObject poToClone) {
        return null;
    }

    //getters
    public String getAuthorsString() {
        return authorsString;
    }

    public String getYear() {
        return year;
    }

    public LinkedList<String> getAuthorsList() {
        return authorsList;
    }

    public String getTitle() {
        return title;
    }

    public String getSource() {
        return source;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getArticleURL() {
        return articleURL;
    }

    public int getGSRank() {
        return GSRank;
    }

    public String getAuthorListAsString () {
        String toReturn = "";
        for(String author : authorsList) {
            toReturn += author;
            toReturn += "\t";
        }
        return toReturn.trim();
    }

    //setters
    public void setCites(int cites) {
        this.cites = cites;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setAuthorsString(String authorsString) {
        this.authorsString = authorsString;
    }

    public void setAuthorsListFromString(String authorsString) {
        setAuthorsList(Utilities.splitByDelimiter(authorsString, ","));
    }
    public void setAuthorsStringFromList(LinkedList<String> authorsString) {
        String authorStringToSet = "";

        for(String author : authorsString) {
            authorStringToSet += author;
            authorStringToSet += ",";
        }
        authorStringToSet = Utilities.trimLastChar(authorStringToSet);
        setAuthorsString(authorStringToSet);
    }

    public void setAuthorsList(LinkedList<String> authorsList) {
        this.authorsList = authorsList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setArticleURL(String articleURL) {
        this.articleURL = articleURL;
    }

    public void setGSRank(int GSRank) {
        this.GSRank = GSRank;
    }
}
