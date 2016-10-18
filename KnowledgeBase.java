

public class KnowledgeBase {
    
    public void initializeRules(){
        //Stench(x,y)=>Wumpus(x-1,y)ORWumpus(x+1,y)ORWumpus(x,y-1)ORWumpus(x,y+1)
        initializeStenchRule();
        
        
        //Breeze mimics this
        //Glitter(x,y)=>Gold(x,y)
        //Bump(x,y)=>Obsticle(x,y)
        //action(Move
        //(!Wumpus(x,y)&&!Pit(x,y))=>Safe(x,y)
        //!Wumpus(-1,y)&&!Wumpus(x,-1)&&!Pit(-1,z)&&!Pit(-1,a)//no boarder things
        //!Wumpus(x,size)&&!Wumpus(size,y)&&!PIT(z,size)&&!Pit(size,a)
    }
    
    public boolean ask(String placeholder){
        return true;
    }
    
    public void tell(String placeholder){

    }
    
    public void untell(String placeholder){
        //we need this for when say, a Wumpus is killed.
    }
    
    private void initializeStenchRule(){
        //Stench(x,y)=>Wumpus(x-1,y)OR(Wumpus(x+1,y)OR(Wumpus(x,y-1) OR Wumpus(x,y+1)))
        Fact leftSide = createFact("Stench", true, true, 0, 1, new IdentityFunction(), new IdentityFunction());
        
        Rule rightSide = new Rule();
        Fact xMinusOne = createFact("Wumpus", true, true, 0, 1, new MinusFunction(), new IdentityFunction());
        Fact xPlusOne = createFact("Wumpus", true, true, 0, 1, new PlusFunction(), new IdentityFunction());
        Fact yMinusOne = createFact("Wumpus", true, true, 0, 1, new IdentityFunction(), new MinusFunction());
        Fact yPlusOne = createFact("Wumpus", true, true, 0, 1, new IdentityFunction(), new PlusFunction());
        Rule firstPart = new Rule();
        firstPart.justFact = true;
        firstPart.fact = xMinusOne;
        rightSide.leftRule = firstPart;
        
        Rule mostInnerRule = new Rule();
        mostInnerRule.leftRule = new Rule(yMinusOne);
        mostInnerRule.rightRule = new Rule(yPlusOne);
        mostInnerRule.connector = Rule.OR;
        
        Rule secondMostInnerRule = new Rule();
        secondMostInnerRule.leftRule = new Rule(xPlusOne);
        secondMostInnerRule.rightRule = mostInnerRule;
        secondMostInnerRule.connector = Rule.OR;
        
        rightSide.rightRule = secondMostInnerRule;
        rightSide.connector = Rule.OR;
        
        Rule stenchRule = new Rule();
        stenchRule.leftRule = new Rule(leftSide);
        stenchRule.rightRule = rightSide;
        stenchRule.connector = Rule.IMPLIES;
        
        //ClauseFormConversion from here...
        
    }
    
    private Fact createFact(String predicate, boolean isXVar, boolean isYVar, int xVarId, int yVarId, IFunction xFunc, IFunction yFunc){
        Fact fact = new Fact();
        fact.predicate = predicate;
        fact.isXVariable = isXVar;
        fact.isYVariable = isYVar;
        if(fact.isXVariable){
            Variable xVar = new Variable();
            xVar.variableId = xVarId;
            fact.xVariable = xVar;
        }
        if(fact.isYVariable){
            Variable yVar = new Variable();
            yVar.variableId = yVarId;
            fact.yVariable = yVar;
        }
        fact.xFunction = xFunc;
        fact.yFunction = yFunc;
        
        return fact;
    }
}
