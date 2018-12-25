package threading;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Test;

/**
 * http://www.careercup.com/page?pid=google-interview-questions
 * @author suketu
 *
 */
public class Throttler {

    @Test
    public void testTPS1() {
        
        // create class with one TPS
        Throttler throttler = new Throttler();
        throttler.setQPS(1);
        
        Date startTime = Calendar.getInstance().getTime();
        
        // expect success
        Assert.assertTrue(throttler.allowThisRequest());
        Assert.assertFalse(throttler.allowThisRequest());
    }
    
    @Test
    public void testTPSN() {
        
        int n = 100;
        
        // create class with one TPS
        Throttler throttler = new Throttler();
        throttler.setQPS(n);
        
        // expect success
        for(int i = 0; i < n; i++)
            Assert.assertTrue(throttler.allowThisRequest());
        Assert.assertFalse(throttler.allowThisRequest());
    }
    
    int qps = 0;
    int qpsServed = 0;
    
    Queue<Date> queryServed = new LinkedList<Date>();
    
    /**
     * Queries per second that can be hit.
     * @param qps
     */
    void setQPS(int qps) {
        this.qps = qps;
    }
    
    boolean allowThisRequest() {
        if(this.qpsServed < this.qps) {
            this.qpsServed ++;
            queryServed.add(Calendar.getInstance().getTime());
            return true;
        }
        else if(this.qpsServed == this.qps) {
            Calendar currentTime = Calendar.getInstance();
            currentTime.add(Calendar.SECOND, -1);
            Date previousSecond = currentTime.getTime();
            if(queryServed.peek().compareTo(previousSecond) < 0) {
                queryServed.poll();
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
