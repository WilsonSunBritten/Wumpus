
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class SubstitutionString extends LinkedHashMap {
    
    private ArrayList<Variable> string = new ArrayList<>();
    
    public SubstitutionString() {
        
    }
    
    
    public boolean contains(Variable var) {
        //returns true if the string contains the variable
        return true;
    }
    
    public Variable get(Variable var) {
        
        return string.get(string.indexOf(var));
    }
    
    public void set(Variable var, Variable replacement) {
        
        int index = string.indexOf(var);
        string.remove(var);
        string.add(index, replacement);
    }
}
