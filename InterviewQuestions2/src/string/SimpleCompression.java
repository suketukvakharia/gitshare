package string;

import org.junit.Test;

public class SimpleCompression {

    
    @Test
    public void testSuccess() {
        
        String test = "FOOBASSSSSSDDDD";
        
        System.out.println(compressed(test.toCharArray()));
    }
    
    
    public static char[] compressed(char[] toCompress) {
        
        if(toCompress.length == 0) return toCompress;
        
        
        char previous = toCompress[0], next;
        int previousCount = 0;
        int i = 0, compressionIndex = 0; 
        
        
        do {
            next = toCompress[i];
            
            if(next == previous) {
                previousCount++;
            }
            else {
                if(previousCount == 1) {
                    toCompress[compressionIndex++] = previous;
                }
                else {
                    toCompress[compressionIndex++] = previous;
                    toCompress[compressionIndex++] = String.valueOf(previousCount).toCharArray()[0];
                }
                previousCount = 1;
            }
            
            previous = next;
            i++;
        } while(i < toCompress.length);
        
        if(previousCount == 1) {
            toCompress[compressionIndex++] = previous;
        }
        else {
            toCompress[compressionIndex++] = previous;
            toCompress[compressionIndex++] = String.valueOf(previousCount).toCharArray()[0];
        }
        
        // mark all chars after as /n
        
        while(compressionIndex < toCompress.length) {
            toCompress[compressionIndex++] = '\n';
        }
        return toCompress;
    }
}
