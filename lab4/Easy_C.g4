grammar Easy_C;

//Start Rule

prog:
    prog_start? EOF
;

prog_start:
    statement+
;

//Non-Terminators
//Expressions

expression:
    atom | infix_expression
;

infix_expression:
    atom expression_operator expression
;

atom:
    const | identifier
;

identifier:
    C_Identifier
;

const:
    Integer
;

//Statements

statement:
    block_statement | simple_statement
;

simple_statement:
    identifier_declare_statement
    | fuction_declare_statement
    | assign_statement
    | expression_statement
    | if_statement
    | loop_statement
    | return_statement
;

block_statement:
    '{' statement* '}' statement_end?
;

identifier_declare_statement:
    identifier_declare_statement_assign | identifier_declare_statement_noassign
;

identifier_declare_statement_assign:
    type identifier declare_operator expression statement_end
;

identifier_declare_statement_noassign:
    type identifier statement_end
;

fuction_declare_statement:
    type_func identifier '(' param_list ')' block_statement
;

param_list:
    param (',' param)*
;

param:
    type identifier
;

assign_statement:
    assign_statement_sub statement_end
;

assign_statement_sub:
    identifier assign_operator expression
;

expression_statement:
    expression statement_end
;

if_statement:
    'if' '(' expression ')' statement
;

loop_statement:
    'for' '(' assign_statement_sub ';' expression ';' assign_statement_sub ')' statement
;

return_statement:
    'return' expression statement_end
;

statement_end:
    Statement_end
;

//Types

type:
    'int'
;

type_func:
    'int' | 'void'
;

//Operators

expression_operator:
    '+' | '-' | '==' | '>' | '<' | '<=' | '>='
;

declare_operator:
    '='
;

assign_operator:
    '=' | '+=' | '-='
;

//Terminators

WS:
    [ \t\r\n]+ -> skip
;

Integer:
    [0-9]+
;

C_Identifier:
    [a-zA-Z_] [a-zA-Z0-9_]*
;

Statement_end:
    ';'
;