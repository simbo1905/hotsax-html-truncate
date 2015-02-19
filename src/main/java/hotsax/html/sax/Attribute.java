
package hotsax.html.sax;

public class Attribute extends Lval {
    private String name;
    private String value;

    public Attribute(String name, String value)
    {
            super(name);
            this.name = name;
            this.value = value;
    }

    public String toString() {
            return ": " + name + "=\"" + value + "\"";
    }


    public String getName() { return name; }
    public String getValue() { return value; }
}
