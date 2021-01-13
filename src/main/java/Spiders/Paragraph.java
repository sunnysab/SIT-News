package Spiders;

public class Paragraph {
    enum Type {Text, Img}

    Type  type_;
    private String value;

    private Paragraph(String  text, String  path) {
        if (text != null) {
            type_ = Type.Text;
            this.value = text;
        } else if (path != null) {
            type_ = Type.Img;
            this.value = path;
        }
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
            case Img: return String.format("[Img] %s", value);
            case Text: return String.format("[Text] %s", value);
            default: return "null";
        }
    }
}