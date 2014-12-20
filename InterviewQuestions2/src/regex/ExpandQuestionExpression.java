package regex;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class ExpandQuestionExpression {

    @Test
    public void testExpandExpression() {
        
        Set<String> expressions = expandQuestionExpression("??");
        
        System.out.println(expressions);
    }
    
    public static Set<String> expandQuestionExpression(String regex) {
        
        Set<String> currentExpressions = new HashSet<String>();
        for(int i = 0; i < regex.length(); i++) {
            
            if(regex.charAt(i) == '?') {
                if(currentExpressions.size() == 0) {
                    currentExpressions.add(regex.substring(0,i) +"0" + ( i+1 == regex.length() ? "" :regex.substring(i+1))  );
                    currentExpressions.add(regex.substring(0,i) +"1" + ( i+1 == regex.length() ? "" :regex.substring(i+1))  );
                }
                else {
                    Set<String> prevExpressions = currentExpressions;
                    currentExpressions = new HashSet<String>();
                    for(String expression: prevExpressions) {
                        currentExpressions.add(expression.substring(0,i) +"0" + ( i+1 == expression.length() ? "" :expression.substring(i+1))  );
                        currentExpressions.add(expression.substring(0,i) +"1" + ( i+1 == expression.length() ? "" :expression.substring(i+1))  );
                    }
                }
            }
        }
        return currentExpressions;
        
        
    }
}
