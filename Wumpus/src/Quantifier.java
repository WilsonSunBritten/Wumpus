
public class Quantifier {

    int variableId;
    boolean isExistential; //if false, assumed universal...
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
