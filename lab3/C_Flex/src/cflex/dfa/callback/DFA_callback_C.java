package cflex.dfa.callback;

import cflex.dfa.DFA;
import cflex.dfa.DFA_event;

public class DFA_callback_C {
    private static String[] keywords = {"auto", "break", "case", "char", "const", "continue", "default",
            "do", "double", "else", "enum", "extern", "float", "for", "goto", "if", "inline", "int",
            "long", "register", "restrict", "return", "short", "signed", "sizeof", "static",
            "struct", "switch", "typedef", "union", "unsigned", "void", "volatile", "while"
    };

    public static class Accept_Identifier_Keep implements ICallback {
        public void execute(DFA dfa, DFA_event e) {
            Util.debugInfo(e);

            String lexeme = dfa.getLexeme();
            for(String str : keywords){
                if(lexeme.equals(str)){
                    Util.acceptFinishedKeep(dfa, String.format("'%s'", lexeme));
                    return;
                }
            }
            Util.acceptFinishedKeep(dfa, "Identifier");
        }
    }

}