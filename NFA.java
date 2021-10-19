import java.util.HashSet;
import java.util.Set;

public class NFA {
    private Q nonDeterministicSetOfStates;
    private Q deterministicSetOfStates = new Q();
    private Set<String> inputAlphabet;
    private Set<State> epsilonTransitionStates = new HashSet<>();
    private Set<State> symbolTransitionStates = new HashSet<>();

    public NFA(Q nonDeterministicSetOfStates) {
        this.nonDeterministicSetOfStates = nonDeterministicSetOfStates;
        this.inputAlphabet = nonDeterministicSetOfStates.getInputAlphabet();
        deterministicSetOfStates.setInputAlphabet(nonDeterministicSetOfStates.getInputAlphabet());
        getEpsilonTransitionStates(nonDeterministicSetOfStates.getStartState());
        deterministicSetOfStates.createState(epsilonTransitionStates.toString());

        Set<State> finalStates = nonDeterministicSetOfStates.getFinalStates();

        boolean sentinel = false;
        for (State state : finalStates) {
            if (epsilonTransitionStates.contains(state))
                sentinel = true;
        }

        if (sentinel == true) {
            deterministicSetOfStates.setFinalState(epsilonTransitionStates.toString());
        }

        deterministicSetOfStates.setStartState(epsilonTransitionStates.toString());
        convert(epsilonTransitionStates);
    }

    private void getEpsilonTransitionStates(State state) {
        epsilonTransitionStates.add(state);
        getEpsilons(state);
    }

    private void getEpsilons(State state) {
        Set<State> states = state.getTransitions("_");

        if (states == null) {
            return;
        }

        epsilonTransitionStates.addAll(states);
        for (State st : states) {
            getEpsilons(st);
        }
        return;
    }

    private void getSymbolTransitionStates(String symbol, State state) {
        Set<State> states = state.getTransitions(symbol);
        if (states == null)
            return;

        symbolTransitionStates.addAll(states);
        for (State st : states) {
            if (st.getTransitions("_") != null) {
                getEpsilons(st);
                symbolTransitionStates.addAll(epsilonTransitionStates);
            }
        }
        return;
    }

    private void convert(Set<State> states) {
        Set<String> symbols = new HashSet<>();
        symbols.addAll(inputAlphabet);
        Set<State> tempStates = new HashSet<>();
        tempStates.addAll(states);

        for (String symbol : symbols) {

            epsilonTransitionStates.clear();
            symbolTransitionStates.clear();
            for (State state : tempStates) {
                if (symbol.equalsIgnoreCase("_") == false) {
                    getSymbolTransitionStates(symbol, state);
                }
            }

            if (symbol.equalsIgnoreCase("_") == false && !tempStates.isEmpty()) {
                if (deterministicSetOfStates.contains(symbolTransitionStates.toString())) {
                    deterministicSetOfStates.setTransition(tempStates.toString(), symbol,
                            symbolTransitionStates.toString());
                } else if (symbolTransitionStates.isEmpty()) {
                    deterministicSetOfStates.createState(symbolTransitionStates.toString());
                    deterministicSetOfStates.setTransition(tempStates.toString(), symbol,
                            symbolTransitionStates.toString());

                    for (String sym : inputAlphabet) {
                        if (sym.equalsIgnoreCase("_") == false) {
                            deterministicSetOfStates.setTransition(symbolTransitionStates.toString(), sym,
                                    symbolTransitionStates.toString());
                        }
                    }
                } else {
                    Set<State> finalStates = nonDeterministicSetOfStates.getFinalStates();
                    boolean sentinel = false;
                    deterministicSetOfStates.createState(symbolTransitionStates.toString());

                    for (State state : finalStates) {
                        if (symbolTransitionStates.contains(state))
                            sentinel = true;
                    }

                    if (sentinel == true) {
                        deterministicSetOfStates.setFinalState(symbolTransitionStates.toString());
                    }

                    deterministicSetOfStates.setTransition(tempStates.toString(), symbol,
                            symbolTransitionStates.toString());
                    convert(symbolTransitionStates);
                }

            }
        }

    }

    public Q getDeterministicSet() {
        return deterministicSetOfStates;
    }

}
