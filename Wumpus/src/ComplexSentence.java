
public class ComplexSentence implements Sentence {

    private final Connective connective;
    private final Sentence[] sentences;

    public ComplexSentence(Connective connective, Sentence... sentences) {

        this.connective = connective;
        this.sentences = new Sentence[sentences.length];
        System.arraycopy(sentences, 0, this.sentences, 0, sentences.length);
    }

    public Connective getConnective() {
        return this.connective;
    }

    public Sentence getSentence(int index) {
        return this.sentences[index];
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if ((object == null) || this.getClass() != object.getClass()) {
            return false;
        } else {
            ComplexSentence sentence = (ComplexSentence) object;
            if ((this.connective == sentence.connective)) {
                for (int i = 0; i < sentences.length; i ++) {
                    if (!this.sentences[i].equals(sentence.sentences[i])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        
        //requires implementation
        String stringSentence = "";
        
        return stringSentence;
    }
}
