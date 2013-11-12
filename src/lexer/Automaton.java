package lexer;

import java.util.HashMap;

abstract public class Automaton {
    private int startingState;
    private int[] endStates;
    private HashMap<Symbol, int[]> transitions;
    private int currentState;

    private class Symbol {
        public boolean isAnyOtherSymbol;
        public char charSymbol;

        Symbol(boolean isAnyOtherSymbol, char charSymbol) {
            this.isAnyOtherSymbol = isAnyOtherSymbol;
            this.charSymbol = charSymbol;
        }

        @Override
        public int hashCode() {
            return this.isAnyOtherSymbol ? -1 : this.charSymbol;
        }

        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        @Override
        public boolean equals(Object otherObj) {
            Symbol other = (Symbol)otherObj;
            if(this.isAnyOtherSymbol) {
                return this.isAnyOtherSymbol == other.isAnyOtherSymbol;
            }
            else
                return this.isAnyOtherSymbol == other.isAnyOtherSymbol
                        &&
                       this.charSymbol == other.charSymbol;
        }
    }
    private boolean inEndState() {
        return currentState == endStates[0] || currentState == endStates[1];
    }

    protected Automaton (int startingState, int errorState, int successState) {
        this.startingState = startingState;
        endStates = new int[2];
        endStates[0] = errorState;
        endStates[1] = successState;
        transitions = new HashMap<Symbol, int[]>();
    }

    protected void addTransition(char charSymbol, int ... transitions) {
        this.transitions.put(new Symbol(false, charSymbol), transitions);
    }

    protected void addGeneralTransition(int ... transitions) {
        this.transitions.put(new Symbol(true, '*'), transitions);
    }

    protected String calculateMaxPrefix(String input) {
        int len = input.length(), i;
        currentState = startingState;
        for(i = 0; i < len && !inEndState(); i++) {
            char nextChar = input.charAt(i);
            Symbol nextSymbol = new Symbol(false, nextChar);

            if (!transitions.containsKey(nextSymbol))
                nextSymbol = new Symbol(true, '*');

            currentState = transitions.get(nextSymbol)[currentState];
        }

        if(!inEndState()) {//no \n in input
            currentState = transitions.get(new Symbol(false, '\n'))[currentState];
            i++;
        }

        assert(inEndState());

        if(currentState == endStates[1])
            return input.substring(0, i-1);
        else
            return null;
    }
}