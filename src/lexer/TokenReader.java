package lexer;

import java.util.HashMap;
import java.util.HashSet;

abstract public class TokenReader {
    private String lastSuccessState;
    private HashMap<String, HashMap<Character, String>> transitions;
    private HashMap<String, String> generalTransitions;
    private HashMap<String, String> linkedStates;
    private HashSet<String> finalStates;

    protected TokenReader (String ... finalStates) {
        lastSuccessState = null;
        transitions = new HashMap<String, HashMap<Character, String>>();
        generalTransitions = new HashMap<String, String>();
        linkedStates = new HashMap<String, String>();
        this.finalStates = new HashSet<String>();
        for (String i : finalStates) {
            this.finalStates.add(i);
            linkedStates.put(i, i);
        }
    }

    protected void addTransition(String from, String characters, String to) {
        HashMap<Character, String> bufMap;

        if(transitions.containsKey(from))
            bufMap = transitions.get(from);
        else
            bufMap = new HashMap<Character, String>();

        for(char i : characters.toCharArray())
            bufMap.put(i, to);

        transitions.put(from, bufMap);
    }

    protected void addGeneralTransition(String from, String to) {
        generalTransitions.put(from, to);
    }

    protected void link(String toState, String ... states) {
        for(String i : states)
            linkedStates.put(i, toState);
    }

    protected String getState() {
        return linkedStates.get(lastSuccessState);
    }

    protected String calculateMaxPrefix(String input, int offset) {
        String currentState = "start";
        lastSuccessState = "error";
        int lastSuccessIndex = 0;
        int len = input.length(), i;

        for(i = offset; i < len && !currentState.equals("error"); i++) {
            char currentChar = input.charAt(i);
            HashMap<Character, String> currentMap = transitions.get(currentState);

            if(currentMap != null && currentMap.containsKey(currentChar)) {
                currentState = currentMap.get(currentChar);
                if(finalStates.contains(currentState)) {
                    lastSuccessState = currentState;
                    lastSuccessIndex = i;
                }
            }
            else
            if(generalTransitions.containsKey(currentState)) {
                currentState = generalTransitions.get(currentState);
                if(finalStates.contains(currentState)) {
                    lastSuccessState = currentState;
                    lastSuccessIndex = i;
                }
            }
            else {
                //transition isn't specified anywhere: to error state by default
                currentState = "error";
            }
        }

        if(lastSuccessIndex > 0)
            return input.substring(offset, lastSuccessIndex + 1);
        else
            return null;
    }

    abstract Token tryReadToken(String input, int offset);
    
    public Token tryReadToken(String input) {
        return tryReadToken(input, 0);
    }
}
