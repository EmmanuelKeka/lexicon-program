import java.util.ArrayList;

public class SyntaxParseTreeGenerator {
    private TreeNode Head;
    private ArrayList<Pos> posArrayList;
    private String sentence [] = new String[6];
    private int sentenceIndex = 0;
    private String bracketedPhrasal = "";

    public SyntaxParseTreeGenerator(ArrayList<Pos> posArrayList) {
        this.posArrayList = posArrayList;
        this.Head = new TreeNode(posArrayList.get(0));
    }

    public void start (String sentence) throws InvalidLexiconException {
        this.sentence = sentence.split(" ");
        bracketedPhrasal = "";
        if(sentence.split(" ").length != 6){
            throw new InvalidLexiconException("sentence to long or short");
        }
        posArrayList.remove(0);
        generateTree(Head);
        checkforNumber();
        GenerateBracketedPhrasal(Head);
    }

    public String getBracketedPhrasal() {
        return bracketedPhrasal;
    }

    private void generateTree(TreeNode node) throws InvalidLexiconException {
        Pos valid;
        int numberOfchild = node.getLabel().getRule().getConponents().size();
        if(checkifLeaf(node.getLabel().getRule().getConponents().get(0))){
                if(checkifLexi(sentence[sentenceIndex],node.getLabel().getRule().getConponents())) {
                    node.getLabel().setLexicon(sentence[sentenceIndex]);
                    sentenceIndex++;
                }
                else{
                    throw new InvalidLexiconException("invalid word at index " + sentenceIndex);
                }
        }
        if(numberOfchild == 1){
            valid = checkifMatch(node.getLabel().getRule().getConponents().get(0));
            if (valid != null) {
                setRightChildNode(node,valid);
                return;
            }
        }
        if(numberOfchild == 2){
            valid = checkifMatch(node.getLabel().getRule().getConponents().get(0));
            if (valid != null) {
                setRightChildNode(node,valid);
            }
            valid = checkifMatch(node.getLabel().getRule().getConponents().get(1));
            if (valid != null) {
                setLeftChildNode(node,valid);
                return;
            }
        }
        if(numberOfchild == 3){
            valid = checkifMatch(node.getLabel().getRule().getConponents().get(0));
            if (valid != null) {
                setRightChildNode(node,valid);
            }
            valid = checkifMatch(node.getLabel().getRule().getConponents().get(1));
            if (valid != null) {
                setLeftMidelNode(node,valid);
            }
            valid = checkifMatch(node.getLabel().getRule().getConponents().get(2));
            if (valid != null) {
                setLeftChildNode(node,valid);
                return;
            }
        }
    }
    private void setRightChildNode(TreeNode node,Pos valid) throws InvalidLexiconException {
        node.setRightChild(new TreeNode(valid));
        generateTree(node.getRightChild());
        return;
    }

    private void setLeftChildNode(TreeNode node,Pos valid) throws InvalidLexiconException {
        node.setLeftChild(new TreeNode(valid));
        generateTree(node.getLeftChild());
        return;
    }

    private void setLeftMidelNode(TreeNode node,Pos valid) throws InvalidLexiconException {
        node.setMidelChild(new TreeNode(valid));
        generateTree(node.getMidelChild());
        return;
    }
    private Pos checkifMatch(String tag){
        Pos valiMatch = null;
        for(int i = 0;i<posArrayList.size();i++){
            String tag2 = posArrayList.get(i).getTag();
            if(tag.equals(tag2)){
                valiMatch = posArrayList.remove(i);
                break;
            }
        }
        return valiMatch;
    }
    private Boolean checkifLeaf(String tag){
        boolean valiMatch = true;
        for(int i = 0;i<posArrayList.size();i++){
            String tag2 = posArrayList.get(i).getTag();
            if(tag.equals(tag2)){
                valiMatch = false;
                break;
            }
        }
        return valiMatch;
    }
    private Boolean checkifLexi(String tag,ArrayList<String> arrayList){
        boolean valiMatch = false;
        for(int i = 0;i<arrayList.size();i++){
            String tag2 = arrayList.get(i);
            if(tag.equals(tag2)){
                valiMatch = true;
                break;
            }
        }
        return valiMatch;
    }
    private void GenerateBracketedPhrasal(TreeNode root){
        //while root is no null we keep going
        if(root !=null){
            bracketedPhrasal = bracketedPhrasal + "[" + root.getLabel().getTag() + " ";
            if(root.getRightChild() == null){
                bracketedPhrasal = bracketedPhrasal + "[" + root.getLabel().getLexicon() + "]";
            }
            GenerateBracketedPhrasal(root.getRightChild());
            GenerateBracketedPhrasal(root.getMidelChild());
            GenerateBracketedPhrasal(root.getLeftChild());
            bracketedPhrasal = bracketedPhrasal + " ]";
        }
    }
    public void drawTree(){
        System.out.println("                                   S1                                     ");
        System.out.println("                                   |                                     ");
        System.out.println("                                   S                                     ");
        System.out.println("                  _________________|___________________________");
        System.out.println("                  |                                           |");
        System.out.println("                  NP                                         VP");
        System.out.println(" _________________|______                       ______________|__________________");
        System.out.println(" |                      |                       |                               |");
        System.out.println("DET                     NN                      V                               VP");
        String pprint = " |                      |                       |               ________________|__________________";
        System.out.println(pprint);
        ArrayList<Integer> indexs = countNumberOfIndex(pprint);
        ArrayList<String> nodes = new ArrayList<String>();
        nodes.add(sentence[0]);
        nodes.add(sentence[1]);
        nodes.add(sentence[2]);
        String nextRole = "";
        for(int i= 0; i< (16 - sentence[2].length()); i++){
            nextRole = nextRole + " ";
        }
        pprint = dynampicPrint(indexs, nodes) + nextRole +"|                                 |";
        System.out.println(pprint);
        indexs = countNumberOfIndex(pprint);
        nodes = new ArrayList<String>();
        nodes.add(sentence[3]);
        nodes.add(sentence[4]);
        pprint = dynampicPrint(indexs, nodes);
        System.out.println(pprint);
    }
    public  ArrayList<Integer> countNumberOfIndex(String pprint){
        ArrayList<Integer> indexs = new ArrayList<Integer>();
        for(int i = 0; i<pprint.length(); i++){
            if(pprint.charAt(i) == '|'){
                indexs.add(i);
            }
        }
        return indexs;
    }
    public String dynampicPrint(ArrayList<Integer> indexs,ArrayList<String> nodes){
        int i = 0;
        int index = 0;
        int nodeIndex = 0;
        int prevoislengh = 0;
        String pprint = "";
        while (true){
            if(nodes.size() == nodeIndex){
                break;
            }
            if(i == indexs.get(index)){
                pprint = pprint + nodes.get(nodeIndex);
                if(nodeIndex  != 0){
                    prevoislengh = prevoislengh + nodes.get(nodeIndex - 1).length();
                    if(index + 1 != indexs.size()) {
                        indexs.set(index + 1, indexs.get(index + 1) - nodes.get(nodeIndex).length() - prevoislengh);
                    }
                }
                else{
                    indexs.set(index + 1,indexs.get(index + 1) - nodes.get(nodeIndex).length());
                }
                nodeIndex++;
                index ++;
            }
            else{
                pprint = pprint + " ";
                i++;
            }
        }
        return pprint;
    }
    public void checkforNumber() throws InvalidLexiconException {
        if(this.sentence[0].equals("the")){
            if( this.sentence[1].equals("people")){
                if(!(this.sentence[2].equals("likes") || this.sentence[2].equals("dislikes"))){
                    throw new InvalidLexiconException("invilde sentence s missing for plural");
                }
            }
            else{
                if(!(this.sentence[2].equals("like") || this.sentence[2].equals("dislike"))){
                    throw new InvalidLexiconException("invilde sentence plural word entered");
                }
            }
        }
        else{
            if( this.sentence[1].equals("person")){
                if(!(this.sentence[2].equals("like") || this.sentence[2].equals("dislike"))){
                    throw new InvalidLexiconException("invilde sentence plural word entered for DET a");
                }
            }
            else{
                throw new InvalidLexiconException("invilde sentence, people entered for DET a");
            }
        }
    }
}
