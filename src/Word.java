public class Word {
    private String word;
    private String Pos;
    private String Root;
    private String number;

    public Word(String pos, String word, String root, String number) {
        this.word = word;
        Pos = pos;
        Root = root;
        this.number = number;
    }

    public String getWord() {
        return word;
    }

    public String getPos() {
        return Pos;
    }

    public String getRoot() {
        return Root;
    }

    public String getNumber() {
        return number;
    }
}
