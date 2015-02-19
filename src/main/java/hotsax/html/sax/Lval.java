package hotsax.html.sax;


/**
 * Lval - extends HtmlParserVal (generatated by byacc/j)
 *    overrides toString function
 * @author edh
 */
public class Lval extends HtmlParserVal {

	public Lval(String s)
	{
		super(s);
	}

	public Lval(Object o)
	{
		super(o);
	}


    public String toString() {
		if (sval != null)
            return sval;
        else if (obj != null)
        	return obj.toString();
        else
        	return "numeric";
    }

}
