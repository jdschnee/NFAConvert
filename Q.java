import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class Q {
    private Set<String> inputAlphabet;
    private Set<State> finalStates;
    private Set<String> finalStrStates;
    private HashMap<String, State> StateMap;
    private State startState;
    private Set<String> states;
    private Set<String> transitions;

    public Q() {
        transitions = new HashSet<>();
        states = new HashSet<>();
        inputAlphabet = new HashSet<>();
        finalStates = new HashSet<>();
        finalStrStates = new HashSet<>();
        StateMap = new HashMap<String, State>();
        inputAlphabet.add("_");
    }

    public void setInputAlphabet(Set<String> inputAlphabet) {
        this.inputAlphabet = inputAlphabet;
    }

    public void addToInputAlphabet(String symbol) {
        inputAlphabet.add(symbol);
    }

    public Set<String> getInputAlphabet() {
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
        finalStates.add(StateMap.get(identifier));
        finalStrStates.add(identifier);
    }

    public Set<State> getFinalStates() {
        return finalStates;
    }

    public Set<String> getFinalStrStates(){
        return finalStrStates;
    }

    private void addState(State newState) {
        StateMap.put(newState.getIdentifier(), newState);
    }

    public int getCardinality() {
        return StateMap.size();
    }

    public void createState(String identifier) {
        states.add(identifier);
        State newState = new State(identifier);
        addState(newState);
    }

    public Set<String> getStates() {
        return states;
    }

    public boolean setTransition(String fromState, String symbol, String toState) {
        transitions.add(fromState + "  " + symbol + "  " + toState);
        StateMap.get(fromState).setTransition(symbol, StateMap.get(toState));
        return true;
    }

    public Set<String> getStrTransitions() {
        return transitions;
    }

    public State getTransition(State state, String symbol) {
        return state.getTransition(symbol);
    }

    public int getTransitionCount(String state) {
        return StateMap.get(state).getTransitionCount();
    }

    public boolean setTransitions(String fromState, String symbol, String toState) {
        StateMap.get(fromState).setTransitions(symbol, StateMap.get(toState));
        return true;
    }

    public Set<State> getTransitions(State state, String symbol) {
        return state.getTransitions(symbol);
    }

    public boolean contains(String identifier) {
        return StateMap.containsKey(identifier);
    }
}