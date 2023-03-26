package cflex.scanner;

import cflex.dfa.*;
import cflex.dfa.callback.*;

import java.util.ArrayList;
import java.util.List;

public class Scanner_C implements IScanner{
    private final DFA dfa;
    private static class States{
        private static final Integer INITIAL = 0;
        private static final Integer IDENTIFIER = 1;

        private static final Integer OPERATORS_RANGE_L = 2;

        //Operators
        private static final Integer LEFTSB = 2; // (
        private static final Integer RIGHTSB = 3; // )
        private static final Integer LEFTMB = 4; // [
        private static final Integer RIGHTMB = 5; // ]
        private static final Integer LEFTLB = 6; // {
        private static final Integer RIGHTLB = 7; // }
        private static final Integer COMMA = 8; // ,
        private static final Integer SEMICOLON = 9; // ;
        private static final Integer NEGATIVE = 10; // ~

        private static final Integer QUESTION = 11; // ?

        private static final Integer ADD = 12; // +
        private static final Integer ADDADD = 13; // ++
        private static final Integer ADDEQUAL = 14; // +=

        private static final Integer MINUS = 15; // -
        private static final Integer MINUSMINUS = 16; // --
        private static final Integer MINUSEQUAL = 17; // -=
        private static final Integer MINUSRIGHT = 18; // ->

        private static final Integer MUL = 19; // *
        private static final Integer MULEQUAL = 20; // *=

        private static final Integer DIVIDE = 21; // /
        private static final Integer DIVIDEEQUAL = 22; // /=

        private static final Integer MOD = 23; // %
        private static final Integer MODEQUAL = 24; // %=
        private static final Integer MODCOLON = 25; // %:
        private static final Integer MODCOLONMOD = 26; // %:% (Discard)
        private static final Integer MODCOLONMODCOLON = 27; // %:%:

        private static final Integer EQUAL = 28; // =
        private static final Integer EQUALEQUAL = 29; // ==

        private static final Integer LEFT = 30; // <
        private static final Integer LEFTLEFT = 31; // <<
        private static final Integer LEFTEQUAL = 32; // <=
        private static final Integer LEFTCOLON = 33; // <:
        private static final Integer LEFTLEFTEQUAL = 34; // <<=

        private static final Integer RIGHT = 35; // >
        private static final Integer RIGHTRIGHT = 36; // >>
        private static final Integer RIGHTEQUAL = 37; // >=
        private static final Integer RIGHTRIGHTEQUAL = 38; // >>=

        private static final Integer AND = 39; // &
        private static final Integer ANDAND = 40; // &&
        private static final Integer ANDEQUAL = 41; // &=

        private static final Integer OR = 42; // |
        private static final Integer OROR = 43; // ||
        private static final Integer OREQUAL = 44; // |=

        private static final Integer XOR = 45; // ^
        private static final Integer XOREQUAL = 46; // ^=

        private static final Integer NOT = 47; // !
        private static final Integer NOTEQUAL = 48; // !=

        private static final Integer COLON = 49; // :
        private static final Integer COLONRIGHT = 50; // :>

        private static final Integer SHARP = 51; // #
        private static final Integer SHARPSHARP = 52; // ##

        private static final Integer DOT = 53; // .
        private static final Integer DOTDOT = 54; // .. (Discard)
        private static final Integer DOTDOTDOT = 55; // ...

        private static final Integer OPERATORS_RANGE_R = 55;

        //Integers
        private static final Integer DECIMAL_CONST = 60; // 123
        private static final Integer ZERO_CONST = 61; // 0
        private static final Integer OCTAL_CONST = 62; // 0***
        private static final Integer HEX_CONST_PREFIX = 63; // 0x or 0X
        private static final Integer HEX_CONST = 64; // 0x*** or 0X***

        //Integer Suffixes
        //ul, uL, Ul, UL, lU, lu, LU, Lu
        //ull, Ull, uLL, ULL
        //llu, llU, LLu, LLU
        private static final Integer SUFFIX_RANGE_L = 70;

        private static final Integer SUFFIX_u = 70; // u or U
        private static final Integer SUFFIX_ul = 71; // ul or Ul
        private static final Integer SUFFIX_ull = 72; // ull or Ull
        private static final Integer SUFFIX_uL = 73; // uL or UL
        private static final Integer SUFFIX_uLL = 74; // uLL or ULL

        private static final Integer SUFFIX_l = 75; // l
        private static final Integer SUFFIX_lu = 76; // lu or lU
        private static final Integer SUFFIX_ll = 77; // ll
        private static final Integer SUFFIX_llu = 78; // ll
        private static final Integer SUFFIX_L = 79; // L
        private static final Integer SUFFIX_Lu = 80; // Lu or LU
        private static final Integer SUFFIX_LL = 81; // LL
        private static final Integer SUFFIX_LLu = 82; // ll

        private static final Integer SUFFIX_RANGE_R = 82;

        //Chars
        private static final Integer CHAR = 90; // '
        private static final Integer CHAR_ESCAPE = 91; // '\
        private static final Integer CHAR_FINISHED = 92; // ''

        //Strings
        private static final Integer STRING = 100; // "
        private static final Integer STRING_ESCAPE = 101; // "\
        private static final Integer STRING_FINISHED = 102; // ''

        //Encoding_Prefix (Default: Identifiers)
        private static final Integer PREFIX_u = 103; // u
        private static final Integer PREFIX_u8 = 104; // u8
        private static final Integer PREFIX_U = 105; // U
        private static final Integer PREFIX_L = 106; // L

        //Floats (DOT can be also derived to FLOAT)
        private static final Integer FLOAT_DOT = 107; // 0.
        private static final Integer FLOAT = 108; // 0.x
        private static final Integer FLOAT_EXP = 109; // 0.xe or 0.xE
        private static final Integer FLOAT_EXP_SIGN = 110; // 0.xe+ or 0.xe-
        private static final Integer FLOAT_EXP_SIGN_NUM = 111; // 0.xe+y or 0.xe-y

        private static final Integer HEXFLOAT_DOT = 112; // 0x0.
        private static final Integer HEXFLOAT = 113; // 0x0.x
        private static final Integer HEXFLOAT_EXP = 114; // 0x0.xp or 0x0.xP
        private static final Integer HEXFLOAT_EXP_SIGN = 115; // 0.xp+ or 0.xp-
        private static final Integer HEXFLOAT_EXP_SIGN_NUM = 116; // 0.xp+y or 0.xp-y

        // Float suffix
        private static final Integer FLOAT_SUFFIX = 117; // 0.0f or 0.0F or 0.0l or 0.0L

        //Length
        private static final Integer length = 128;
    }

    private static class Callbacks{
        private static final ICallback ACCEPT = new DFA_callback.Accept();
        private static final ICallback FORWARD = new DFA_callback.Forward();
        private static final ICallback ESCAPE = new DFA_callback.Escape();
        private static final ICallback IDENTIFIER_KEEP = new DFA_callback_C.Accept_Identifier_Keep();
        private static final ICallback OPERATOR_KEEP = new DFA_callback.Accept_Operator_Keep();
        private static final ICallback DISCARD_KEEP = new DFA_callback.Discard_Keep();
        private static ICallback KEEP(String token_type){
            return new DFA_callback.Accept_Keep(token_type);
        }
    }

    public Scanner_C(){
        dfa = new DFA();
        dfa.initSize(States.length);

        //Set Rules
        setGeneralRules();

        setIdentifiers();
        setIntegers();
        setFloats();

        setOperators();
        setPrefix();
        setChars();
        setStrings();
    }

    @Override
    public List<String> scan(String code) {
        dfa.processString(code);

        Util.moveForward(dfa);
        dfa.setLexeme("EOF");
        Util.addTokenInfo(dfa, "EOF");

        return parse_tokens(dfa.tokens_list);
    }

    public List<String> parse_tokens(List<DFA_lexing> tokens_list){
        List<String> ret = new ArrayList<>();
        int token_index = 0;
        for(DFA_lexing l : tokens_list){
            ret.add(String.format("[@%d,%d:%d='%s',<%s>,%d:%d]", token_index, l.l_index_global, l.r_index_global, l.token, l.token_type, l.line_index, l.row_index));
            token_index++;
        }
        return ret;
    }

    private void setGeneralRules(){
        //Finish Rules
        dfa.addEdge(new DFA_edgeInterval(States.INITIAL, States.INITIAL, 0, 127, Callbacks.FORWARD));
        dfa.addEdge(new DFA_edgeInterval(States.IDENTIFIER, States.INITIAL, 0, 127, Callbacks.IDENTIFIER_KEEP));

        dfa.addEdge(new DFA_edgeInterval(States.PREFIX_u, States.INITIAL, 0, 127, Callbacks.IDENTIFIER_KEEP)); //u
        dfa.addEdge(new DFA_edgeInterval(States.PREFIX_U, States.INITIAL, 0, 127, Callbacks.IDENTIFIER_KEEP)); //U
        dfa.addEdge(new DFA_edgeInterval(States.PREFIX_L, States.INITIAL, 0, 127, Callbacks.IDENTIFIER_KEEP)); //L
        dfa.addEdge(new DFA_edgeInterval(States.PREFIX_u8, States.INITIAL, 0, 127, Callbacks.IDENTIFIER_KEEP)); //u8

        //Process Escapes
        dfa.addEdge(new DFA_edge(States.INITIAL, States.INITIAL, '\n', Callbacks.ESCAPE));
    }

    private void setIntegers(){
        //Set Suffixes
        for(Integer i = States.SUFFIX_RANGE_L; i <= States.SUFFIX_RANGE_R; i++){
            dfa.addEdge(new DFA_edgeInterval(i, States.INITIAL, 0, 127, Callbacks.KEEP("IntegerConstant")));
        }

        //SUFFIX
        dfa.addEdge(new DFA_edge(States.SUFFIX_l, States.SUFFIX_lu, 'u', Callbacks.ACCEPT)); // lu
        dfa.addEdge(new DFA_edge(States.SUFFIX_l, States.SUFFIX_lu, 'U', Callbacks.ACCEPT)); // lU
        dfa.addEdge(new DFA_edge(States.SUFFIX_L, States.SUFFIX_Lu, 'u', Callbacks.ACCEPT)); // Lu
        dfa.addEdge(new DFA_edge(States.SUFFIX_L, States.SUFFIX_Lu, 'U', Callbacks.ACCEPT)); // LU

        dfa.addEdge(new DFA_edge(States.SUFFIX_l, States.SUFFIX_ll, 'l', Callbacks.ACCEPT)); // ll
        dfa.addEdge(new DFA_edge(States.SUFFIX_ll, States.SUFFIX_llu, 'u', Callbacks.ACCEPT)); // llu
        dfa.addEdge(new DFA_edge(States.SUFFIX_ll, States.SUFFIX_llu, 'U', Callbacks.ACCEPT)); // llU
        dfa.addEdge(new DFA_edge(States.SUFFIX_L, States.SUFFIX_LL, 'L', Callbacks.ACCEPT)); // LL
        dfa.addEdge(new DFA_edge(States.SUFFIX_LL, States.SUFFIX_LLu, 'u', Callbacks.ACCEPT)); // LLu
        dfa.addEdge(new DFA_edge(States.SUFFIX_LL, States.SUFFIX_LLu, 'U', Callbacks.ACCEPT)); // LLU

        dfa.addEdge(new DFA_edge(States.SUFFIX_u, States.SUFFIX_ul, 'l', Callbacks.ACCEPT)); // ul or Ul
        dfa.addEdge(new DFA_edge(States.SUFFIX_ul, States.SUFFIX_ull, 'l', Callbacks.ACCEPT)); // ull or Ull
        dfa.addEdge(new DFA_edge(States.SUFFIX_u, States.SUFFIX_uL, 'L', Callbacks.ACCEPT)); // uL or UL
        dfa.addEdge(new DFA_edge(States.SUFFIX_uL, States.SUFFIX_uLL, 'L', Callbacks.ACCEPT)); // uLL or ULL

        //DECIMAL
        dfa.addEdge(new DFA_edgeInterval(States.INITIAL, States.DECIMAL_CONST, '1', '9', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edgeInterval(States.DECIMAL_CONST, States.INITIAL, 0, 127, Callbacks.KEEP("IntegerConstant")));
        dfa.addEdge(new DFA_edgeInterval(States.DECIMAL_CONST, States.DECIMAL_CONST, '0', '9', Callbacks.ACCEPT));
        setSuffix(States.DECIMAL_CONST);

        //OCTAL
        dfa.addEdge(new DFA_edge(States.INITIAL, States.ZERO_CONST, '0', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edgeInterval(States.ZERO_CONST, States.INITIAL, 0, 127, Callbacks.KEEP("IntegerConstant")));
        dfa.addEdge(new DFA_edgeInterval(States.ZERO_CONST, States.OCTAL_CONST, '0', '7', Callbacks.ACCEPT));
        setSuffix(States.ZERO_CONST);

        dfa.addEdge(new DFA_edgeInterval(States.OCTAL_CONST, States.INITIAL, 0, 127, Callbacks.KEEP("IntegerConstant")));
        dfa.addEdge(new DFA_edgeInterval(States.OCTAL_CONST, States.OCTAL_CONST, '0', '7', Callbacks.ACCEPT));
        setSuffix(States.OCTAL_CONST);

        //HEX
        dfa.addEdge(new DFA_edge(States.ZERO_CONST, States.HEX_CONST_PREFIX, 'x', Callbacks.ACCEPT)); //0x
        dfa.addEdge(new DFA_edge(States.ZERO_CONST, States.HEX_CONST_PREFIX, 'X', Callbacks.ACCEPT)); //0X

        dfa.addEdge(new DFA_edgeInterval(States.HEX_CONST_PREFIX, States.INITIAL, 0, 127, Callbacks.DISCARD_KEEP));
        dfa.addEdge(new DFA_edgeInterval(States.HEX_CONST_PREFIX, States.HEX_CONST, '0', '9', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edgeInterval(States.HEX_CONST_PREFIX, States.HEX_CONST, 'A', 'F', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edgeInterval(States.HEX_CONST_PREFIX, States.HEX_CONST, 'a', 'f', Callbacks.ACCEPT));

        dfa.addEdge(new DFA_edgeInterval(States.HEX_CONST, States.INITIAL, 0, 127, Callbacks.KEEP("IntegerConstant")));
        dfa.addEdge(new DFA_edgeInterval(States.HEX_CONST, States.HEX_CONST, '0', '9', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edgeInterval(States.HEX_CONST, States.HEX_CONST, 'A', 'F', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edgeInterval(States.HEX_CONST, States.HEX_CONST, 'a', 'f', Callbacks.ACCEPT));
        setSuffix(States.HEX_CONST);
    }

    private void setSuffix(Integer s){
        dfa.addEdge(new DFA_edge(s, States.SUFFIX_u, 'u', Callbacks.ACCEPT)); // u
        dfa.addEdge(new DFA_edge(s, States.SUFFIX_u, 'U', Callbacks.ACCEPT)); // U
        dfa.addEdge(new DFA_edge(s, States.SUFFIX_l, 'l', Callbacks.ACCEPT)); // l
        dfa.addEdge(new DFA_edge(s, States.SUFFIX_L, 'L', Callbacks.ACCEPT)); // L
    }

    private void setFloats(){
        dfa.addEdge(new DFA_edge(States.ZERO_CONST, States.FLOAT_DOT, '.', Callbacks.ACCEPT)); // 0.
        dfa.addEdge(new DFA_edge(States.DECIMAL_CONST, States.FLOAT_DOT, '.', Callbacks.ACCEPT)); // 1.

        dfa.addEdge(new DFA_edgeInterval(States.FLOAT_DOT, States.INITIAL, 0, 127, Callbacks.KEEP("FloatingConstant")));
        dfa.addEdge(new DFA_edgeInterval(States.FLOAT_DOT, States.FLOAT, '0', '9', Callbacks.ACCEPT));
        setFloatSuffix(States.FLOAT_DOT);

        //DOT can also transfer to floats
        dfa.addEdge(new DFA_edgeInterval(States.DOT, States.FLOAT, '0', '9', Callbacks.ACCEPT)); // .0

        dfa.addEdge(new DFA_edgeInterval(States.FLOAT, States.INITIAL, 0, 127, Callbacks.KEEP("FloatingConstant")));
        dfa.addEdge(new DFA_edge(States.FLOAT, States.FLOAT_EXP, 'e', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edge(States.FLOAT, States.FLOAT_EXP, 'E', Callbacks.ACCEPT));
        setFloatSuffix(States.FLOAT);

        dfa.addEdge(new DFA_edgeInterval(States.FLOAT_EXP, States.INITIAL, 0, 127, Callbacks.DISCARD_KEEP));
        dfa.addEdge(new DFA_edge(States.FLOAT_EXP, States.FLOAT_EXP_SIGN, '+', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edge(States.FLOAT_EXP, States.FLOAT_EXP_SIGN, '-', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edgeInterval(States.FLOAT_EXP, States.FLOAT_EXP_SIGN_NUM, '0', '9', Callbacks.ACCEPT));

        dfa.addEdge(new DFA_edgeInterval(States.FLOAT_EXP_SIGN, States.INITIAL, 0, 127, Callbacks.DISCARD_KEEP));
        dfa.addEdge(new DFA_edgeInterval(States.FLOAT_EXP_SIGN, States.FLOAT_EXP_SIGN_NUM, '0', '9', Callbacks.ACCEPT));

        dfa.addEdge(new DFA_edgeInterval(States.FLOAT_EXP_SIGN_NUM, States.INITIAL, 0, 127, Callbacks.KEEP("FloatingConstant")));
        dfa.addEdge(new DFA_edgeInterval(States.FLOAT_EXP_SIGN_NUM, States.FLOAT_EXP_SIGN_NUM, '0', '9', Callbacks.ACCEPT));
        setFloatSuffix(States.FLOAT_EXP_SIGN_NUM);

        //HEX FLOATS
        dfa.addEdge(new DFA_edge(States.HEX_CONST, States.HEXFLOAT_DOT, '.', Callbacks.ACCEPT)); //0X0.

        dfa.addEdge(new DFA_edgeInterval(States.HEXFLOAT_DOT, States.INITIAL, 0, 127, Callbacks.KEEP("FloatingConstant")));
        dfa.addEdge(new DFA_edgeInterval(States.HEXFLOAT_DOT, States.HEXFLOAT, '0', '9', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edgeInterval(States.HEXFLOAT_DOT, States.HEXFLOAT, 'A', 'F', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edgeInterval(States.HEXFLOAT_DOT, States.HEXFLOAT, 'a', 'f', Callbacks.ACCEPT));
        setFloatSuffix(States.HEXFLOAT_DOT);

        dfa.addEdge(new DFA_edgeInterval(States.HEXFLOAT, States.INITIAL, 0, 127, Callbacks.KEEP("FloatingConstant")));
        dfa.addEdge(new DFA_edge(States.HEXFLOAT, States.HEXFLOAT_EXP, 'p', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edge(States.HEXFLOAT, States.HEXFLOAT_EXP, 'P', Callbacks.ACCEPT));
        setFloatSuffix(States.HEXFLOAT);

        // 0x0 can also go to 0x0p
        dfa.addEdge(new DFA_edge(States.HEX_CONST, States.HEXFLOAT_EXP, 'p', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edge(States.HEX_CONST, States.HEXFLOAT_EXP, 'P', Callbacks.ACCEPT));

        dfa.addEdge(new DFA_edgeInterval(States.HEXFLOAT_EXP, States.INITIAL, 0, 127, Callbacks.DISCARD_KEEP));
        dfa.addEdge(new DFA_edge(States.HEXFLOAT_EXP, States.HEXFLOAT_EXP_SIGN, '+', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edge(States.HEXFLOAT_EXP, States.HEXFLOAT_EXP_SIGN, '-', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edgeInterval(States.HEXFLOAT_EXP, States.HEXFLOAT_EXP_SIGN_NUM, '0', '9', Callbacks.ACCEPT));

        dfa.addEdge(new DFA_edgeInterval(States.HEXFLOAT_EXP_SIGN, States.INITIAL, 0, 127, Callbacks.DISCARD_KEEP));
        dfa.addEdge(new DFA_edgeInterval(States.HEXFLOAT_EXP_SIGN, States.HEXFLOAT_EXP_SIGN_NUM, '0', '9', Callbacks.ACCEPT));

        dfa.addEdge(new DFA_edgeInterval(States.HEXFLOAT_EXP_SIGN_NUM, States.INITIAL, 0, 127, Callbacks.KEEP("FloatingConstant")));
        dfa.addEdge(new DFA_edgeInterval(States.HEXFLOAT_EXP_SIGN_NUM, States.HEXFLOAT_EXP_SIGN_NUM, '0', '9', Callbacks.ACCEPT));
        setFloatSuffix(States.HEXFLOAT_EXP_SIGN_NUM);

        //Float Suffix
        dfa.addEdge(new DFA_edgeInterval(States.FLOAT_SUFFIX, States.INITIAL, 0, 127, Callbacks.KEEP("FloatingConstant")));
    }

    private void setFloatSuffix(Integer s){
        dfa.addEdge(new DFA_edge(s, States.FLOAT_SUFFIX, 'f', Callbacks.ACCEPT)); // f
        dfa.addEdge(new DFA_edge(s, States.FLOAT_SUFFIX, 'F', Callbacks.ACCEPT)); // F
        dfa.addEdge(new DFA_edge(s, States.FLOAT_SUFFIX, 'l', Callbacks.ACCEPT)); // l
        dfa.addEdge(new DFA_edge(s, States.FLOAT_SUFFIX, 'L', Callbacks.ACCEPT)); // L
    }

    private void setIdentifiers(){
        dfa.addEdge(new DFA_edge(States.INITIAL, States.IDENTIFIER, '_', Callbacks.ACCEPT));
        setIdentifiers_sub('_');
        for(char c = 'A'; c <= 'Z'; c++){
            dfa.addEdge(new DFA_edge(States.INITIAL, States.IDENTIFIER, c, Callbacks.ACCEPT));
            setIdentifiers_sub(c);
        }
        for(char c = 'a'; c <= 'z'; c++){
            dfa.addEdge(new DFA_edge(States.INITIAL, States.IDENTIFIER, c, Callbacks.ACCEPT));
            setIdentifiers_sub(c);
        }
        for(char c = '0'; c <= '9'; c++){
            setIdentifiers_sub(c);
        }
    }

    private void setIdentifiers_sub(Character c){
        dfa.addEdge(new DFA_edge(States.IDENTIFIER, States.IDENTIFIER, c, Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edge(States.PREFIX_u, States.IDENTIFIER, c, Callbacks.ACCEPT)); //u
        dfa.addEdge(new DFA_edge(States.PREFIX_U, States.IDENTIFIER, c, Callbacks.ACCEPT)); //U
        dfa.addEdge(new DFA_edge(States.PREFIX_L, States.IDENTIFIER, c, Callbacks.ACCEPT)); //L
        dfa.addEdge(new DFA_edge(States.PREFIX_u8, States.IDENTIFIER, c, Callbacks.ACCEPT)); //u8
    }

    private void setOperators(){
        //Set Operators
        for(Integer i = States.OPERATORS_RANGE_L; i <= States.OPERATORS_RANGE_R; i++){
            dfa.addEdge(new DFA_edgeInterval(i, States.INITIAL, 0, 127, Callbacks.OPERATOR_KEEP));
        }

        //Simple Expressions
        dfa.addEdge(new DFA_edge(States.INITIAL, States.LEFTSB, '(', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edge(States.INITIAL, States.RIGHTSB, ')', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edge(States.INITIAL, States.LEFTMB, '[', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edge(States.INITIAL, States.RIGHTMB, ']', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edge(States.INITIAL, States.LEFTLB, '{', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edge(States.INITIAL, States.RIGHTLB, '}', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edge(States.INITIAL, States.COMMA, ',', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edge(States.INITIAL, States.SEMICOLON, ';', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edge(States.INITIAL, States.NEGATIVE, '~', Callbacks.ACCEPT));
        dfa.addEdge(new DFA_edge(States.INITIAL, States.QUESTION, '?', Callbacks.ACCEPT));

        //ADD
        dfa.addEdge(new DFA_edge(States.INITIAL, States.ADD, '+', Callbacks.ACCEPT)); // +
        dfa.addEdge(new DFA_edge(States.ADD, States.ADDADD, '+', Callbacks.ACCEPT)); // ++
        dfa.addEdge(new DFA_edge(States.ADD, States.ADDEQUAL, '=', Callbacks.ACCEPT)); // +=

        //MINUS
        dfa.addEdge(new DFA_edge(States.INITIAL, States.MINUS, '-', Callbacks.ACCEPT)); // -
        dfa.addEdge(new DFA_edge(States.MINUS, States.MINUSMINUS, '-', Callbacks.ACCEPT)); // --
        dfa.addEdge(new DFA_edge(States.MINUS, States.MINUSEQUAL, '-', Callbacks.ACCEPT)); // -=
        dfa.addEdge(new DFA_edge(States.MINUS, States.MINUSRIGHT, '>', Callbacks.ACCEPT)); // ->

        //MUL
        dfa.addEdge(new DFA_edge(States.INITIAL, States.MUL, '*', Callbacks.ACCEPT)); // *
        dfa.addEdge(new DFA_edge(States.MUL, States.MULEQUAL, '=', Callbacks.ACCEPT)); // *=

        //DIVIDE
        dfa.addEdge(new DFA_edge(States.INITIAL, States.DIVIDE, '/', Callbacks.ACCEPT)); // /
        dfa.addEdge(new DFA_edge(States.DIVIDE, States.DIVIDEEQUAL, '=', Callbacks.ACCEPT)); // /=

        //MOD
        dfa.addEdge(new DFA_edge(States.INITIAL, States.MOD, '%', Callbacks.ACCEPT)); // %
        dfa.addEdge(new DFA_edge(States.MOD, States.MODEQUAL, '=', Callbacks.ACCEPT)); // %=
        dfa.addEdge(new DFA_edge(States.MOD, States.MODCOLON, ':', Callbacks.ACCEPT)); // %:
        dfa.addEdge(new DFA_edge(States.MODCOLON, States.MODCOLONMOD, '%', Callbacks.ACCEPT)); // %:% (Discard)
        dfa.addEdge(new DFA_edgeInterval(States.MODCOLONMOD, States.INITIAL, 0, 127, Callbacks.DISCARD_KEEP));
        dfa.addEdge(new DFA_edge(States.MODCOLONMOD, States.MODCOLONMODCOLON, ':', Callbacks.ACCEPT)); // %:%:

        //EQUAL
        dfa.addEdge(new DFA_edge(States.INITIAL, States.EQUAL, '=', Callbacks.ACCEPT)); // =
        dfa.addEdge(new DFA_edge(States.EQUAL, States.EQUALEQUAL, '=', Callbacks.ACCEPT)); // ==

        //LEFT
        dfa.addEdge(new DFA_edge(States.INITIAL, States.LEFT, '<', Callbacks.ACCEPT)); // <
        dfa.addEdge(new DFA_edge(States.LEFT, States.LEFTLEFT, '<', Callbacks.ACCEPT)); // <<
        dfa.addEdge(new DFA_edge(States.LEFT, States.LEFTEQUAL, '=', Callbacks.ACCEPT)); // <=
        dfa.addEdge(new DFA_edge(States.LEFT, States.LEFTCOLON, ':', Callbacks.ACCEPT)); // <:
        dfa.addEdge(new DFA_edge(States.LEFTLEFT, States.LEFTLEFTEQUAL, '=', Callbacks.ACCEPT)); // <<=

        //RIGHT
        dfa.addEdge(new DFA_edge(States.INITIAL, States.RIGHT, '>', Callbacks.ACCEPT)); // >
        dfa.addEdge(new DFA_edge(States.RIGHT, States.RIGHTRIGHT, '>', Callbacks.ACCEPT)); // >>
        dfa.addEdge(new DFA_edge(States.RIGHT, States.RIGHTEQUAL, '=', Callbacks.ACCEPT)); // >=
        dfa.addEdge(new DFA_edge(States.RIGHTRIGHT, States.RIGHTRIGHTEQUAL, '=', Callbacks.ACCEPT)); // >>=

        //AND
        dfa.addEdge(new DFA_edge(States.INITIAL, States.AND, '&', Callbacks.ACCEPT)); // &
        dfa.addEdge(new DFA_edge(States.AND, States.ANDAND, '&', Callbacks.ACCEPT)); // &&
        dfa.addEdge(new DFA_edge(States.AND, States.ANDEQUAL, '=', Callbacks.ACCEPT)); // &=

        //OR
        dfa.addEdge(new DFA_edge(States.INITIAL, States.OR, '|', Callbacks.ACCEPT)); // |
        dfa.addEdge(new DFA_edge(States.OR, States.OROR, '|', Callbacks.ACCEPT)); // ||
        dfa.addEdge(new DFA_edge(States.OR, States.OREQUAL, '=', Callbacks.ACCEPT)); // |=

        //XOR
        dfa.addEdge(new DFA_edge(States.INITIAL, States.XOR, '^', Callbacks.ACCEPT)); // ^
        dfa.addEdge(new DFA_edge(States.XOR, States.XOREQUAL, '=', Callbacks.ACCEPT)); // ^=

        //NOT
        dfa.addEdge(new DFA_edge(States.INITIAL, States.NOT, '!', Callbacks.ACCEPT)); // !
        dfa.addEdge(new DFA_edge(States.NOT, States.NOTEQUAL, '=', Callbacks.ACCEPT)); // !=

        //COLON
        dfa.addEdge(new DFA_edge(States.INITIAL, States.COLON, ':', Callbacks.ACCEPT)); // :
        dfa.addEdge(new DFA_edge(States.COLON, States.COLONRIGHT, '>', Callbacks.ACCEPT)); // :>

        //SHARP
        dfa.addEdge(new DFA_edge(States.INITIAL, States.SHARP, '#', Callbacks.ACCEPT)); // #
        dfa.addEdge(new DFA_edge(States.SHARP, States.SHARPSHARP, '#', Callbacks.ACCEPT)); // ##

        //DOT
        dfa.addEdge(new DFA_edge(States.INITIAL, States.DOT, '.', Callbacks.ACCEPT)); // .
        dfa.addEdge(new DFA_edge(States.DOT, States.DOTDOT, '.', Callbacks.ACCEPT)); // .. (Discard)
        dfa.addEdge(new DFA_edgeInterval(States.DOTDOT, States.INITIAL, 0, 127, Callbacks.DISCARD_KEEP));
        dfa.addEdge(new DFA_edge(States.DOTDOT, States.DOTDOTDOT, '.', Callbacks.ACCEPT)); // ...
    }

    private void setPrefix(){
        dfa.addEdge(new DFA_edge(States.INITIAL, States.PREFIX_u, 'u', Callbacks.ACCEPT)); // u
        dfa.addEdge(new DFA_edge(States.INITIAL, States.PREFIX_U, 'U', Callbacks.ACCEPT)); // U
        dfa.addEdge(new DFA_edge(States.INITIAL, States.PREFIX_L, 'L', Callbacks.ACCEPT)); // L
        dfa.addEdge(new DFA_edge(States.PREFIX_u, States.PREFIX_u8, '8', Callbacks.ACCEPT)); // u8
    }

    private void setChars(){
        dfa.addEdge(new DFA_edge(States.INITIAL, States.CHAR, '\'', Callbacks.ACCEPT)); // '
        dfa.addEdge(new DFA_edge(States.PREFIX_u, States.CHAR, '\'', Callbacks.ACCEPT)); // '
        dfa.addEdge(new DFA_edge(States.PREFIX_U, States.CHAR, '\'', Callbacks.ACCEPT)); // '
        dfa.addEdge(new DFA_edge(States.PREFIX_L, States.CHAR, '\'', Callbacks.ACCEPT)); // '

        dfa.addEdge(new DFA_edgeInterval(States.CHAR, States.CHAR, 0, 127, Callbacks.ACCEPT)); // 'a
        dfa.addEdge(new DFA_edge(States.CHAR, States.INITIAL, '\n', Callbacks.ESCAPE)); // '

        dfa.addEdge(new DFA_edge(States.CHAR, States.CHAR_FINISHED, '\'', Callbacks.ACCEPT)); // ''
        dfa.addEdge(new DFA_edge(States.CHAR, States.CHAR_ESCAPE, '\\', Callbacks.ACCEPT)); // '\

        dfa.addEdge(new DFA_edgeInterval(States.CHAR_ESCAPE, States.CHAR, 0, 127, Callbacks.ACCEPT)); // '\'
        dfa.addEdge(new DFA_edge(States.CHAR_ESCAPE, States.INITIAL, '\n', Callbacks.ESCAPE)); // '

        dfa.addEdge(new DFA_edgeInterval(States.CHAR_FINISHED, States.INITIAL, 0, 127, Callbacks.KEEP("CharacterConstant"))); // ''
    }

    private void setStrings(){
        dfa.addEdge(new DFA_edge(States.INITIAL, States.STRING, '\"', Callbacks.ACCEPT)); // "
        dfa.addEdge(new DFA_edge(States.PREFIX_u, States.STRING, '\"', Callbacks.ACCEPT)); // u"
        dfa.addEdge(new DFA_edge(States.PREFIX_U, States.STRING, '\"', Callbacks.ACCEPT)); // U"
        dfa.addEdge(new DFA_edge(States.PREFIX_L, States.STRING, '\"', Callbacks.ACCEPT)); // L"
        dfa.addEdge(new DFA_edge(States.PREFIX_u8, States.STRING, '\"', Callbacks.ACCEPT)); // u8"

        dfa.addEdge(new DFA_edgeInterval(States.STRING, States.STRING, 0, 127, Callbacks.ACCEPT)); // "a
        dfa.addEdge(new DFA_edge(States.STRING, States.INITIAL, '\n', Callbacks.ESCAPE)); // "

        dfa.addEdge(new DFA_edge(States.STRING, States.STRING_FINISHED, '\"', Callbacks.ACCEPT)); // ""
        dfa.addEdge(new DFA_edge(States.STRING, States.STRING_ESCAPE, '\\', Callbacks.ACCEPT)); // "\

        dfa.addEdge(new DFA_edgeInterval(States.STRING_ESCAPE, States.STRING, 0, 127, Callbacks.ACCEPT)); // "\"
        dfa.addEdge(new DFA_edge(States.STRING_ESCAPE, States.INITIAL, '\n', Callbacks.ESCAPE)); // "

        dfa.addEdge(new DFA_edgeInterval(States.STRING_FINISHED, States.INITIAL, 0, 127, Callbacks.KEEP("StringLiteral"))); // ""
    }
}
