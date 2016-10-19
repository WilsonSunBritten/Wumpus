
import java.util.ArrayList;
import java.util.Iterator;
/*
    A clause contains a list of predicates
*/
public class Clause implements Iterable {
    
    
    ArrayList<Fact> facts = new ArrayList<>();
    
    public Clause(){}
    public Clause(Fact fact){
        facts.add(fact);
    }
    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
