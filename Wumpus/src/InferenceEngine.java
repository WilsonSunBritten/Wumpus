
import java.util.ArrayList;

public class InferenceEngine {

    KnowledgeBase kb;

    public InferenceEngine(KnowledgeBase kb) {
        this.kb = kb;
    }

    public boolean follows(Fact fact) {
        Clause clause = new Clause();
        //Step 1: negate input fact
        fact.not = !fact.not;

        clause.facts.add(fact);
        ArrayList<Clause> tempClone = new ArrayList<>(kb.getClauses());
        ArrayList<Clause> kbClausesClone = new ArrayList<>();
        for (Clause tempClause : tempClone) {
            kbClausesClone.add(new Clause(tempClause));
        }

        //shortcut, check for direct contradiction to original fact
        for (Clause factClause : kbClausesClone) {
            if (factClause.facts.size() == 1) {
                Fact holder = factClause.facts.get(0);
                if (holder.predicate.equals(fact.predicate) && holder.not != fact.not && Unifier.equalWithSubs(holder, fact, new ArrayList<>())) {
                    return true;
                }
            }
        }

        boolean keepGoing = true;
        //Step 2: Run negated facts against all known facts
        while (!kbClausesClone.isEmpty()) {
            keepGoing = true;
            for (Clause kbClause : kbClausesClone) {
                for (Fact kbFact : kbClause.facts) {

                    for (Fact followFact : clause.facts) {
                        if (kbFact.predicate.equals(followFact.predicate) && kbFact.not == !followFact.not) {
                            //extend clause with everything in kbClause, remove kbFact and followFact, start over
                            boolean skipOut = false;
                            for (int i = 0; i < kbFact.variables.size(); i++) {
                                Variable var1 = kbFact.variables.get(i);
                                Variable var2 = followFact.variables.get(i);
                                if (var1.value != var2.value) {
                                    skipOut = true;
                                }
                            }
                            if (!skipOut) {
                                kbClause.facts.remove(kbFact);//maybe commment out this line
                                clause.facts.remove(followFact);
                                clause.facts.addAll(kbClause.facts);

                                //before starting over check if clause is empty...
                                if (clause.facts.isEmpty()) {
                                    return true;
                                }
                                keepGoing = false;
                            }
                        }
                        if (!keepGoing) {
                            break;
                        }
                    }
                    if (!keepGoing) {
                        break;
                    }
                }
                if (!keepGoing) {
                    break;
                }
            }
            if (keepGoing)//if we didn't do any changes, keepGoing will be true, if we hit here, then we exhausted every clause comparison with no results, terminate
            {
                return false;
            }
        }
        return false;
        //Step 3: If all facts become empty, return true, else if all facts are exhausted, return false
    }

    public void infer(Fact factStart) {
        //look at each fact in each clause in kb.rules, if predicates match, and negations are opposite
        Fact fact = new Fact(factStart);
        ArrayList<Clause> tempClone = new ArrayList<>(kb.rules);
        tempClone.addAll(kb.getClauses());
        ArrayList<Clause> kbClausesClone = new ArrayList<>();
        for (Clause tempClause : tempClone) {
            kbClausesClone.add(new Clause(tempClause));
        }
        for (Clause clause : kbClausesClone) {
            for (Fact ruleFact : clause.facts) {
                if (ruleFact.predicate.equals(fact.predicate) && ruleFact.not == !fact.not) {
                    ArrayList<Substitute> substitutions = Unifier.unify(fact, ruleFact);
                    if (!Unifier.equalWithSubs(fact, ruleFact, substitutions)) {
                        continue;
                    }
                    for (Substitute sub : substitutions) {
                        for (Variable var : fact.variables) {
                            if (var.isVariable && var.variableId == sub.varIdToSubstitute) {
                                var.isVariable = false;
                                var.value = sub.valToSubstituteWith;
                                if (var.function != null) {
                                    var.value = var.function.process(var.value);
                                }
                            }
                        }
                        for (Fact tempFact : clause.facts) {

                            for (Variable var : tempFact.variables) {
                                if (var.isVariable && var.variableId == sub.varIdToSubstitute) {
                                    var.isVariable = false;
                                    var.value = sub.valToSubstituteWith;
                                    if (var.function != null) {
                                        var.value = var.function.process(var.value);
                                    }
                                }
                            }
                        }
                    }

                    clause.facts.remove(ruleFact);
                  //  System.out.println("Inferred:");
                  //  Clause.printClause(clause);
                    kb.addToClauses(clause);
                    break;
                }
            }
        }
        //unify the two rules
        //apply the unification substitution on a copy of the kb.rules fact thing
        //remove the matching fact from that rule
        //add the rule copy with the substitution to kb.clauses
    }

    public void infer(Clause clauseToCheck) {
        //look in kb.rules, only use rules with a single fact
        //for each such fact, go through the clause and look for a matching predicate
        //if negations are opposite, apply unification, if unification exists..
        //with the substitution, apply to copies of both
        //strip fact from input clause 
        ArrayList<Clause> tempClone = new ArrayList<>(kb.rules);
        tempClone.addAll(kb.getClauses());
        ArrayList<Clause> kbClausesClone = new ArrayList<>();
        for (Clause tempClause : tempClone) {
            kbClausesClone.add(new Clause(tempClause));
        }
        Clause addedClause = new Clause(clauseToCheck);
        for (Clause ruleClause : kbClausesClone) {
            if (ruleClause.facts.size() == 1) {
                Fact ruleFact = new Fact(ruleClause.facts.get(0));//we use a copy
                Clause clause = new Clause(addedClause);//copy the added clause, redo each time
                for (Fact fact : clause.facts) {
                    if (fact.predicate.equals(ruleFact.predicate) && fact.not != ruleFact.not) {
                        ArrayList<Substitute> substitutions = Unifier.unify(fact, ruleFact);
                        for (Substitute sub : substitutions) {
                            for (Variable var : ruleFact.variables) {
                                if (var.isVariable && var.variableId == sub.varIdToSubstitute) {
                                    var.isVariable = false;
                                    var.value = sub.valToSubstituteWith;
                                }
                            }
                            for (Fact changeFact : clause.facts) {
                                for (Variable var : changeFact.variables) {
                                    if (var.isVariable && var.variableId == sub.varIdToSubstitute) {
                                        var.isVariable = false;
                                        var.value = sub.valToSubstituteWith;
                                    }
                                }
                            }
                        }
                        if (Unifier.equalWithSubs(fact, ruleFact, substitutions)) {
                            clause.facts.remove(fact);
                   //         System.out.println("Inferred:");
                      //      Clause.printClause(clause);
                            kb.addToClauses(clause);
                            return;//the smaller clause will be inferred against again, no need to keep trying rules immediately.
                        }
                    }
                }
            }
        }
    }
}
