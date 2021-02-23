import java.util.HashMap;

public class State {
    private HashMap<Character, State> transitionMap = new HashMap<Character, State>();
    private boolean isFinalState = false;
    private String identifier;

    public State(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean isFinalState() {
        return isFinalState;
    }

    public void setFinalState() {
        isFinalState = true;
    }

    public State getTransition(Character symbol) {
        return transitionMap.get(symbol);
    }

    public void setTransition(Character symbol, State forwardState) {
        transitionMap.put(symbol, forwardState);
    }
}