import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
    private ArrayList<Pos> posArrayList = new ArrayList<Pos>();
    private ArrayList<Word> wordArrayList = new ArrayList<Word>();
    private SyntaxParseTreeGenerator generator;

    public Parser() {
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter \"exit\" to end the programme");
        while(true) {
            readRules();
            readLexicon();
            generator = new SyntaxParseTreeGenerator(posArrayList);
            System.out.print("Enter a sentence: ");
            String str = sc.nextLine();
            if(str.equals("exit")){
                System.out.println("Thank you for using my programme bye");
                break;
            }
            if(!valideSentence(str)){
                System.out.println("invalid words entered");
                continue;
            }
            try {
                generator = new SyntaxParseTreeGenerator(posArrayList);
                generator.start(str);
                generator.drawTree();
                System.out.println(generator.getBracketedPhrasal());
            } catch (Exception e) {
                System.out.println("invalid sentence");
            }
        }
    }
    public void readRules(){
        try {
            File ruleReader = new File("Resources/rules.txt");
            Scanner myReader = new Scanner(ruleReader);
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String rulesTexts []= myReader.nextLine().split(" ");
                Pos pos = new Pos(rulesTexts[0]);
                for(int i=1; i<rulesTexts.length;i++){
                    pos.getRule().addConponents(rulesTexts[i]);
                }
                posArrayList.add(pos);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void readLexicon(){
        try {
            File lexiconReader = new File("Resources/lexicon.txt");
            Scanner myReader = new Scanner(lexiconReader);
            while (myReader.hasNextLine()) {
                String lexiconTexts []= myReader.nextLine().split(" ");
                wordArrayList.add(new Word(lexiconTexts[0],lexiconTexts[1],lexiconTexts[2],lexiconTexts[3]));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Boolean valideSentence(String sentence){
        String text [] = sentence.split(" ");
        boolean valide = false;
        for (int i =0; i < text.length; i++){
            valide = false;
            for (int j = 0; j < wordArrayList.size(); j++){
                if(wordArrayList.get(j).getWord().equals(text[i]))
                    valide = true;
            }
            if(!valide)
                break;
        }
        return valide;
    }
}
