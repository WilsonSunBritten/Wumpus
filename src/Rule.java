
public class Rule {
    public static final int AND = 1;
    public static final int OR = 2;
    public static final int IMPLIES = 3;
    public static final int IFF = 4;
    
    
    Fact fact;
    Rule leftRule;
    Rule rightRule;
    boolean justFact = false;
    int connector;
    public Rule(){}
    public Rule(Fact fact){
        this.fact = fact;
        justFact = true;
    }
}
