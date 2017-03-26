import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by rich on 3/15/2017.
 */
public class Analyzer {

    private boolean testModeActive = false;
    private String productionMode = "edgeList";
    private PublicationObjectHashTable publicationObjectHashTable;
    private Set<String> years;

    public Analyzer(String[] args) throws Exception{

        publicationObjectHashTable = new PublicationObjectHashTable();
        years = new HashSet<>();

        for(int i=0; i<args.length; i++) {
            String key = args[i];
            String val = args[i+1];
            i++;
            System.out.println(key + " : " + val);
            if(key.equals("-testMode")) {
                if(val.equals("true")) {
                    testModeActive = true;
                }
            }
            else if(key.equals("-productionMode")) {
                productionMode = val;
            }
            else if(key.equals("-yearRange")){
                val = val.replace("[","");
                val = val.replace("]","");
                for(String year : val.split(",")) {
                    years.add(year);
                }
            }
        }
        execute();
    }

    private void execute() throws Exception {
        if(testModeActive) {
            runTestMode();
        } else {
            //LinkedList<String> data = Utilities.textFileToStringList("C:\\Users\\rich\\IdeaProjects\\PubAnalyzer\\src\\input\\2012faculty.txt");
            LinkedList<String> data = Utilities.textFileToStringList("C:\\Users\\rich\\IdeaProjects\\PubAnalyzer\\src\\input\\2016faculty.txt");

            for(String entry : data) {
                if((entry != null)
                    && (!(entry.split("\t")[0].equals("Cites")))) {
                    PublicationObject po = new PublicationObject(entry);
                    po.flattenAuthorsAndTitle();    //flatten the authors and the title
                    publicationObjectHashTable.addPublicationObject(po);
                }
            }

            //clean the data
            publicationObjectHashTable.cleanPublicationObjectTable();
            publicationObjectHashTable.cleanAuthorsList();

            //fuzzy algorithms: guess author matches
            //publicationObjectHashTable.guessAuthorMatches();

            if(productionMode.equals("edgeList")) {
                Set<String> edgeList = new HashSet<>();

                for(String title : publicationObjectHashTable.getTitleToPublicationObject().keySet()) {
                    PublicationObject toAdd = publicationObjectHashTable.getTitleToPublicationObject().get(title);

                    if(years.contains(toAdd.getYear())) {
                        edgeList.add(publicationObjectHashTable.getTitleToPublicationObject().get(title).getAuthorListAsString());
                    }
                }
                Utilities.writeStringSetToFile(edgeList, "C:\\Users\\rich\\IdeaProjects\\PubAnalyzer\\src\\output\\output.out");
            }

        }
    }

    private void runTestMode() throws Exception {

        if(productionMode.equals("matrix")) {
            String testString = "3\tDL Hall, S Aungst\tThe use of soft sensors and I-space for improved combat ID\t2008\tProceedings of the Air Force Laboratory Workshop on …\tceri.org\thttp://ceri.org/workshop/2008Workshops/HALL%20-%20CID%20paper.pdf\thttps://scholar.google.com/scholar?cites=5160801643976065142&as_sdt=5,39&sciodt=0,39&hl=en&num=20\t5\t3/8/2017\tPDF\t\t\t\t0\t0\t0\t0\t3\n";
            String testString2 = "0\tJ Knox, S Aungst, T Bacastow, M Campbell…\tFusion of Ground and Satellite Data via Army Battle Command System\t2008\t… of Multisensor Data …\tcrcnetbase.com\thttp://www.crcnetbase.com/doi/pdf/10.1201/9781420053098.ch30\t\t8\t3/8/2017\t\t\t\t\t0\t0\t0\t0\t0\n";

            LinkedList<String> testEntries = new LinkedList<>();
            testEntries.add(testString);
            testEntries.add(testString2);

            for(String entry : testEntries) {
                PublicationObject po = new PublicationObject(entry);
                publicationObjectHashTable.addPublicationObject(po);
                po.prettyPrint();
            }
        }
        else {
            System.err.println("unknown mode");
        }
    }
}
