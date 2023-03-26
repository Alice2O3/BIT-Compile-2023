import cflex.dfa.DFA_lexing;
import cflex.dfa.callback.*;
import cflex.dfa.DFA;
import cflex.dfa.DFA_edge;
import cflex.dfa.DFA_edgeInterval;

public class C_Flex_Test {
    public static void main(String[] args) {
        DFA dfa = new DFA();
        dfa.initSize(5);
        dfa.setInitialState(1);

        dfa.addEdge(new DFA_edgeInterval(0, 0, 0, 127, new DFA_callback_demo.Default()));
        dfa.addEdge(new DFA_edgeInterval(1, 0, 0, 127, new DFA_callback_demo.Default()));
        dfa.addEdge(new DFA_edgeInterval(2, 0, 0, 127, new DFA_callback_demo.Default()));
        dfa.addEdge(new DFA_edgeInterval(3, 0, 0, 127, new DFA_callback_demo.Default()));
        dfa.addEdge(new DFA_edgeInterval(4, 0, 0, 127, new DFA_callback_demo.Default()));

        dfa.addEdge(new DFA_edge(1, 2, '1', new DFA_callback_demo.Default()));
        dfa.addEdge(new DFA_edge(2, 1, '1', new DFA_callback_demo.Default()));
        dfa.addEdge(new DFA_edge(1, 3, '0', new DFA_callback_demo.Default()));
        dfa.addEdge(new DFA_edge(3, 1, '0', new DFA_callback_demo.Default()));
        dfa.addEdge(new DFA_edge(3, 4, '1', new DFA_callback_demo.Accepted()));
        dfa.addEdge(new DFA_edge(4, 3, '1', new DFA_callback_demo.Default()));
        dfa.addEdge(new DFA_edge(2, 4, '0', new DFA_callback_demo.Accepted()));
        dfa.addEdge(new DFA_edge(4, 2, '0', new DFA_callback_demo.Default()));

        dfa.processString("1100100010101010");

        //System.out.println(dfa.tokens_list);
        int token_index = 0;
        for(DFA_lexing l : dfa.tokens_list){
            System.out.printf("[@%d,%d:%d='%s',<%s>,%d:%d]%n", token_index, l.l_index_global, l.r_index_global, l.token, l.token_type, l.line_index, l.row_index);
            token_index++;
        }

        dfa.processString("1100100010101010");

        token_index = 0;
        for(DFA_lexing l : dfa.tokens_list){
            System.out.printf("[@%d,%d:%d='%s',<%s>,%d:%d]%n", token_index, l.l_index_global, l.r_index_global, l.token, l.token_type, l.line_index, l.row_index);
            token_index++;
        }
    }
}
