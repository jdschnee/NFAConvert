import java.util.ArrayList;
import java.util.HashMap;

public class Q {
    private ArrayList<Character> inputAlphabet = new ArrayList<>();
    private HashMap<String, State> StateMap;
    private State startState;

    public Q() {
        StateMap = new HashMap<String, State>();
    }

    public void setInputAlphabet(ArrayList<Character> inputAlphabet) {
        this.inputAlphabet = inputAlphabet;
    }

    public void addToInputAlphabet(Character symbol) {
        inputAlphabet.add(symbol);
    }

    public ArrayList<Character> getInputAlphabet() {
        return inputAlphabet;
    }

    public void setStartState(String identifier) {
        startState = StateMap.get(identifier);
    }

    public State getStartState() {
        return startState;
    }

    public void setFinalState(String identifier) {
        StateMap.get(identifier).setFinalState();
    }

    private void addState(State newState) {
        StateMap.put(newState.getIdentifier(), newState);
    }

    public int getCardinality() {
        return StateMap.size();
    }

    public void createState(String identifier) {
        State newState = new State(identifier);
        addState(newState);
    }

    private boolean checkInputSymbol(Character symbol) {
        if (inputAlphabet.contains(symbol))
            return true;
        return false;
    }

    public boolean setTransition(String fromState, Character symbol, String toState) {
        if (checkInputSymbol(symbol) == false)
            return false;
        StateMap.get(fromState).setTransition(symbol, StateMap.get(toState));
        return true;
    }

    public State getTransition(State state, Character symbol) {
        return state.getTransition(symbol);
    }

}