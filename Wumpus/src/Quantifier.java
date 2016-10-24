
public class Quantifier {

    int variableId;
    char variableRep;
    boolean isExistential;
    boolean not;

    public void printQuantifier() {
        if (isExistential) {
            System.out.print("EXIST(");
        } else {
            System.out.print("FORALL(");
        }
        System.out.print((char) (variableId + 97) + ") ");
    }
}
