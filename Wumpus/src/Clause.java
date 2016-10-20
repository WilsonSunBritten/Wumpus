
import java.util.ArrayList;
import java.util.Iterator;
/*
    A clause contains a list of predicates
*/
public class Clause extends Fact implements Iterable {
    
    
    ArrayList<Fact> facts = new ArrayList<>();
    
    public Clause(){}
    public Clause(Clause clause){
        for(Fact tempFact : clause.facts){
            facts.add(new Fact(tempFact));
        }
    }
    public Clause(Fact fact){
        facts.add(fact);
    }
    
    @Override
    public boolean contains(Variable var) {
        
        for (Fact f : facts) {
            if (f.contains(var) || f.equals(var)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public ArrayList getArgs() {
        return facts;
    }
    
    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
