import java.util.HashSet;
import java.util.Set;

/**
 * Created by rich on 4/8/2017.
 */
public class BrillStyleNameTagger {

    private Set<Rule> rules;

    public BrillStyleNameTagger() {
        rules = new HashSet<>();
    }
    public void createNewRule() {

    }


    public class Rule {
        private Antecedent antecedent;
        private Consequent consequent;
        private HashSet<Condition> conditions;

        public Rule(Antecedent a, Consequent c, Condition condition) {
            antecedent = a;
            consequent = c;
            conditions = new HashSet<>();
            conditions.add(condition);
        }

        public class Condition {
            public Condition() {
            }
        }
        public abstract class Proposition {
            public Proposition() {
            }
        }
        public class Antecedent extends Proposition {
            public String getAntecedentString() {
                return antecedentString;
            }

            String antecedentString;
            public Antecedent(String as) {
                antecedentString = as;
            }
        }
        public class Consequent extends Proposition {
            public String getConsequentString() {
                return consequentString;
            }

            private String consequentString;
            public Consequent(String cs) {
                consequentString = cs;
            }
        }
    }

}
