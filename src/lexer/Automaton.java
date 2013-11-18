package lexer;

import java.util.HashMap;
import java.util.HashSet;

abstract public class Automaton {
    private String currentState, previousSuccessState;
    private HashSet<String> endStates;
    private HashMap<String, HashMap<Character, String>> transitions;

    protected Automaton () {

    }

    protected void addTransition() {

    }

    protected String getState() {
        return currentState;
    }

    protected String calculateMaxPrefix(String input) {
        int len = input.length(), i;

    }
}