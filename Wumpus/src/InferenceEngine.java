
import java.util.ArrayList;

public class InferenceEngine {

    KnowledgeBase kb;
    public InferenceEngine(KnowledgeBase kb){
        this.kb = kb;
    }

    public boolean follows(Fact fact){
        Clause clause = new Clause();
        //Step 1: negate input fact
        fact.not = !fact.not;
        clause.facts.add(fact);
        ArrayList<Clause> tempClone = new ArrayList<>(kb.getClauses());
        ArrayList<Clause> kbClausesClone = new ArrayList<>();
        for (Clause tempClause : tempClone) {
            kbClausesClone.add(new Clause(tempClause));
    }
        boolean keepGoing = true;
        //Step 2: Run negated facts against all known facts
        while(!kbClausesClone.isEmpty()){
            keepGoing = true;
        for(Clause kbClause:kbClausesClone){
            for(Fact kbFact:kbClause.facts){
                
                for(Fact followFact:clause.facts){
                    if(kbFact.predicate.equals(followFact.predicate) && kbFact.not == !followFact.not){
                        //extend clause with everything in kbClause, remove kbFact and followFact, start over
                        kbClause.facts.remove(kbFact);
                        clause.facts.remove(followFact);
                        clause.facts.addAll(kbClause.facts);
                        
                        //before starting over check if clause is empty...
                        if(clause.facts.isEmpty())
                            return true;
                        keepGoing = false;
                    }
                    if(!keepGoing)
                        break;
                }
                if(!keepGoing)
                    break;
            }
            if(!keepGoing)
                break;
        }
        if(keepGoing)//if we didn't do any changes, keepGoing will be true, if we hit here, then we exhausted every clause comparison with no results, terminate
            return false;
        }
        return false;
        //Step 3: If all facts become empty, return true, else if all facts are exhausted, return false
    }
    
    public void infer(Fact fact){
        //look at each fact in each clause in kb.rules, if predicates match, and negations are opposite
        //unify the two rules
        //apply the unification substitution on a copy of the kb.rules fact thing
        //remove the matching fact from that rule
        //add the rule copy with the substitution to kb.clauses
    }
    public void infer(Clause clause){
        //look in kb.rules, only use rules with a single fact
        //for each such fact, go through the clause and look for a matching predicate
        //if negations are opposite, apply unification, if unification exists..
        //with the substitution, apply to copies of both
        //strip fact from input clause, make sure it's not a copy
    }
}
