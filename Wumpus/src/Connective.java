
public enum Connective {

    NOT("~"),
    AND("&"),
    OR("|"),
    IMPLICATION("=>"),
    BICONDITIONAL("<=>");

    private final String symbol;

    @Override
    public String toString() {
        return symbol;
    }

    private Connective(String symbol) {
        this.symbol = symbol;
    }
}
