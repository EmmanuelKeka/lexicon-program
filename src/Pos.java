public class Pos {
    private String tag;
    private Rule rule = new Rule();
    private String Lexicon;

    public Pos(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public Rule getRule() {
        return rule;
    }

    public String getLexicon() {
        return Lexicon;
    }

    public void setLexicon(String lexicon) {
        Lexicon = lexicon;
    }
}
