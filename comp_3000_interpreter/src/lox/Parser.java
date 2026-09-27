package lox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static lox.TokenType.*;

class Parser {
    private static class ParseError extends RuntimeException {}
    private final List<Token> tokens;
    private int current = 0;


    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }


    Expr parse() {
        try {
            return expression();
        } catch (ParseError error) {
            return null;
        }
    }


    private Expr expression() {
        return riverConnection();
    }

    private Expr riverCombination() {
        Expr expr = equality();

        while (match(AMPERSAND)) {
            Token operator = previous();
            Expr right = equality();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr riverConnection() {
        Expr expr = riverCombination();

        while (match(ARROW)) {
            Token operator = previous();
            Expr right = riverCombination();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr equality() {
        Expr expr = comparison();

        while (match(BANG_EQUAL, EQUAL_EQUAL)) {
            Token operator = previous();
            Expr right = comparison();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }


    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }


    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }


    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }


    private boolean isAtEnd() {
        return peek().type == EOF;
    }


    private Token peek() {
        return tokens.get(current);
    }


    private Token previous() {
        return tokens.get(current - 1);
    }


    private Expr comparison() {
        Expr expr = term();

        while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            Token operator = previous();
            Expr right = term();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }


    private Expr term() {
        Expr expr = factor();

        while (match(MINUS, PLUS)) {
            Token operator = previous();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }


    private Expr factor() {
        Expr expr = unary();

        while (match(SLASH, STAR)) {
            Token operator = previous();
            Expr right = unary();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }


    private Expr unary() {
        if (match(BANG, MINUS)) {
            Token operator = previous();
            Expr right = unary();
            return new Expr.Unary(operator, right);
        }

        return primary();
    }


    private Expr primary() {
        if (match(FALSE)) return new Expr.Literal(false);
        if (match(TRUE)) return new Expr.Literal(true);
        if (match(NIL)) return new Expr.Literal(null);

        if (match(NUMBER, STRING)) {
            return new Expr.Literal(previous().literal);
        }

        if (match(IDENTIFIER)) {
            return new Expr.Literal(previous().lexeme);
        }

        if (match(LEFT_PAREN)) {
            Expr expr = expression();
            consume(RIGHT_PAREN, "Expect ')' after expression.");
            return new Expr.Grouping(expr);
        }

        if (match(FLOW_START)) {
            return flowLiteral();
        }

        throw error(peek(), "Expect expression.");
    }


    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();

        throw error(peek(), message);
    }


    private ParseError error(Token token, String message) {
        Lox.error(token, message);
        return new ParseError();
    }



    private void synchronize() {
        advance();

        while (!isAtEnd()) {
            if (previous().type == SEMICOLON) return;

            switch (peek().type) {
                case CLASS:
                case FUN:
                case VAR:
                case FOR:
                case IF:
                case WHILE:
                case PRINT:
                case RETURN:
                return;
            }

            advance();
        }
    }

    private Expr flowLiteral() {
        Map<String, Expr> fields = new HashMap<>();

        do {
            Token name = consume(IDENTIFIER, "Expected field name.");
            consume(COLON, "Expected ':' after field name.");

            Expr value = expression();
            fields.put(name.lexeme, value);
        } while (match(COMMA));

        consume(FLOW_END, "Expected '}}' after flow literal.");

        return new Expr.Literal(fields);
    }
}