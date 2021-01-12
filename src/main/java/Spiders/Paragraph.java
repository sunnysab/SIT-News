package Spiders;

public class Paragraph {
    enum Type {Text, Img}

    Type  type_;
    private String  text;
    private String  path;

    private Paragraph(String  text, String  path) {
        if (text != null) {
            type_ = Type.Text;
        } else if (path != null) {
            type_ = Type.Img;
        }
        this.text = text;
        this.path = path;
    }

    public static Paragraph text(String  text) {
        return new Paragraph(text, null);
    }

    public static Paragraph image(String  path) {
        return new Paragraph(null, path);
    }

    @Override
    public String toString() {
        switch (type_) {
            case Img: return String.format("[Img] %s", path);
            case Text: return String.format("[Text] %s", text);
            default: return "null";
        }
    }
}