package lexer;

public class Token {
    // Falta guardar la columna
    public Token(int type, String text, int line) {
        this.type = type;
        this.text = text;
        this.line = line;
    }

    public Token(int type, String text) {
        this(type, text, 1);
    }

    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public int getLine() {
        return line;
    }

    private int type, line;
    private String text;
}
