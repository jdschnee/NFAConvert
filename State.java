import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class State {
    private HashMap<String, State> transitionMap = new HashMap<>();
    private HashMap<String, Set<State>> transitionsMap = new HashMap<>();
    private boolean isFinalState = false;
    private String identifier;
    private int transitionCount;

    public State(String identifier) {
        this.identifier = identifier;
        transitionCount = 0;
    }

    public int getTransitionCount() {
        return transitionCount;
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

    public State getTransition(String symbol) {
        return transitionMap.get(symbol);
    }

    public void setTransition(String symbol, State forwardState) {
        transitionMap.put(symbol, forwardState);
        transitionCount++;
    }

    public Set<State> getTransitions(String symbol) {
        return transitionsMap.get(symbol);
    }

    public void setTransitions(String symbol, State forwardState) {
        Set<State> states = transitionsMap.get(symbol);

        if (states == null) {
            states = new HashSet<State>();
            states.add(forwardState);
            transitionsMap.put(symbol, states);
        } else {
            states.add(forwardState);
            transitionsMap.put(symbol, states);
        }
    }

    public String toString() {
        return getIdentifier();
    }
}