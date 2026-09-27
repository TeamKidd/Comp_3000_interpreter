package lox;

import java.util.Map;

class AstPrinter implements Expr.Visitor<String> {
    String print(Expr expr) {
        return expr.accept(this);
    }


    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.lexeme,
                            expr.left, expr.right);
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) return "nil";
        if (expr.value instanceof Map) {
        Map<?, ?> fields = (Map<?, ?>) expr.value;

        StringBuilder result = new StringBuilder("{");

        for (Map.Entry<?, ?> field : fields.entrySet()) {
            result.append(field.getKey())
                  .append(": ")
                  .append(print((Expr) field.getValue()))
                  .append(", ");
        }

        if (!fields.isEmpty()) {
            result.setLength(result.length() - 2);
        }

        result.append("}");
        return result.toString();
    }

    return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return parenthesize(expr.operator.lexeme, expr.right);
    }


    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Expr expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }


    public static void main(String[] args) {
    Expr expression = new Expr.Binary(
            new Expr.Unary(
            new Token(TokenType.MINUS, "-", null, 1),
            new Expr.Literal(123)),
            new Token(TokenType.STAR, "*", null, 1),
            new Expr.Grouping(
            new Expr.Literal(45.67)));

    System.out.println(new AstPrinter().print(expression));
    }

    @Override
    public String visitFlowLiteralExpr(Expr.FlowLiteral expr) {
        return expr.fields.toString();
    }
}