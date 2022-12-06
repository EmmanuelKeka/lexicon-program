import java.util.ArrayList;

public class Rule {
    private ArrayList<String> conponents = new ArrayList<String>();

    public Rule() {
    }
    public void addConponents (String component){
        conponents.add(component);
    }

    public ArrayList<String> getConponents() {
        return conponents;
    }
}
