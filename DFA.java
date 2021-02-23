import java.util.List;

public class DFA {
    private boolean isAcceptable;
    private String bitString;
    Q setOfStates;

    public DFA(String bitString, Q setOfStates){
        this.bitString = bitString;
        //InitialState(0);
        this.setOfStates = setOfStates;
        genericState(0, setOfStates.getStartState());
    }

    public boolean getAcceptability() {
        return isAcceptable;
    }

    public void setAcceptability(boolean acceptability) {
        isAcceptable = acceptability;
    }

    private void genericState(int symbolIndex, State currentState){
        if(symbolIndex == bitString.length()){
            if(currentState.isFinalState()){
                setAcceptability(true);
                return;
            }else{
                setAcceptability(false);
                return;
            }
        }

        List<Character> inputAlphabet = List.copyOf(setOfStates.getInputAlphabet());

        if(inputAlphabet.contains(bitString.charAt(symbolIndex))){
            State newState = currentState.getTransition(bitString.charAt(symbolIndex));
            symbolIndex++;
            genericState(symbolIndex, newState);
        }else{
            setAcceptability(false);
            symbolIndex = bitString.length();
            return;
        }
    }
}
