import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DFACheck {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("DFACheck: No input files specified");
        } else if (args.length == 1) {
            System.err.println("DFACheck: Invalid Usage - The program must be given two files as input");
        } else {

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
                                setOfStates.addToInputAlphabet(string.charAt(0));
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
                                setOfStates.setTransition(tokens[0], tokens[1].charAt(0), tokens[2]);
                            }
                            break;
                    }
                    if (tupleElement > 5) break;
                }
            } catch (FileNotFoundException e) {
                System.err.println("DFACheck: The file " + args[0] + " could not be found");
            } catch (IOException e) {
                System.err.println("DFACheck: The file " + args[0] + " could not be opened");
            }

            try {

                List<String> fileStream = Files.readAllLines(Paths.get(args[1]));
                int noOfLines = fileStream.size();

                DFA dfaArr[] = new DFA[noOfLines];
                int index = 0;

                for (String string : fileStream) {
                    dfaArr[index] = new DFA(string, setOfStates);
                    System.out.print(string + " ");

                    if (dfaArr[index].getAcceptability()) {
                        System.out.println("accepted");
                    } else {
                        System.out.println("rejected");
                    }

                    index++;
                }
            } catch (FileNotFoundException e) {
                System.err.println("DFACheck: The file " + args[1] + " could not be found");
            } catch (IOException e) {
                System.err.println("DFACheck: The file " + args[1] + " could not be opened");
            }
        }
    }
}