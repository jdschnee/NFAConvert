import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class NFAConvert {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("NFAConvert: No input files specified");
        } else {
            boolean validFirstArg = true;
            Q setOfStates = new Q();

            try {
                List<String> fileStream = Files.readAllLines(Paths.get(args[0]));
                int tupleElement = 0;

                for (String string : fileStream) {
                    switch (tupleElement) {
                    case 0:
                        if (string.startsWith("%")) {
                            tupleElement++;
                        }
                        break;
                    case 1:
                        if (string.startsWith("%")) {
                            tupleElement++;
                        } else {
                            setOfStates.createState(string);
                        }
                        break;
                    case 2:
                        if (string.startsWith("%")) {
                            tupleElement++;
                        } else {
                            setOfStates.addToInputAlphabet(string);
                        }
                        break;
                    case 3:
                        if (string.startsWith("%")) {
                            tupleElement++;
                        } else {
                            setOfStates.setFinalState(string);
                        }
                        break;
                    case 4:
                        if (string.startsWith("%")) {
                            tupleElement++;
                        } else {
                            setOfStates.setStartState(string);
                        }
                        break;
                    case 5:
                        if (string.startsWith("%")) {
                            tupleElement++;
                        } else {
                            String delims = "[ ]+";
                            String[] tokens = string.split(delims);
                            setOfStates.setTransitions(tokens[0], tokens[1], tokens[2]);
                        }
                        break;
                    }
                    if (tupleElement > 5)
                        break;
                }
            } catch (FileNotFoundException e) {
                System.err.println("NFAConvert: The file " + args[0] + " could not be found");
                validFirstArg = false;
            } catch (IOException e) {
                System.err.println("NFAConvert: The file " + args[0] + " could not be opened");
                validFirstArg = false;
            }

            if (validFirstArg) {
                NFA nfa = new NFA(setOfStates);
                Q dfa = nfa.getDeterministicSet();

                System.out.println("% Q");

                for (String stateName : dfa.getStates()) {
                    System.out.println(stateName);
                }

                System.out.println("% Sigma");

                for (String symbol : dfa.getInputAlphabet()) {
                    if (symbol.equalsIgnoreCase("_") == false) {
                        System.out.println(symbol);
                    }
                }

                System.out.println("% F");

                for (String finalState : dfa.getFinalStrStates()) {
                    System.out.println(finalState);
                }

                System.out.println("% Q0");
                System.out.println(dfa.getStartState().getIdentifier());

                System.out.println("% Delta");

                for (String transition : dfa.getStrTransitions()) {
                    System.out.println(transition);
                }
            }
        }
    }
}