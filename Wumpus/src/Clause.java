
import java.util.ArrayList;
import java.util.Iterator;
/*
    A clause contains a list of predicates
*/
public class Clause extends Fact implements Iterable {
    
    
    ArrayList<Fact> facts = new ArrayList<>();
    
    public Clause(){}
    public Clause(Fact fact){
        facts.add(fact);
    }
    
    @Override
    public boolean isUnary() {
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
