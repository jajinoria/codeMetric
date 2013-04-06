
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author Johanna 
 */
public class main {
    public static void main(String [ ] args){
        String writtenLineMetaExpression = "\\d|\\w|\\S";
        String writtenLineMetaExpression2 = "\n";
        System.out.println(containsMetaExpression(writtenLineMetaExpression, "  } "));
    }
    
    private static boolean containsMetaExpression(String metaExpression, String expression) {
        Pattern pattern = Pattern.compile(metaExpression);
        Matcher matcher = pattern.matcher(expression);
        return matcher.find();
    }
}
