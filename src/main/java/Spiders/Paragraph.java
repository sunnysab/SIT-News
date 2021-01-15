package Spiders;

public class Paragraph {
    enum Type {Text, Img}

    public Type type_;
    public String text;
    public String imgPath;

    private Paragraph(String text, String path) {
        if (text != null) {
            type_ = Type.Text;
            this.text = text;
        } else if (path != null) {
            type_ = Type.Img;
            this.imgPath = path;
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
            case Img: return String.format("[Img] %s", imgPath);
            case Text: return String.format("[Text] %s", text);
            default: return "null";
        }
    }
}