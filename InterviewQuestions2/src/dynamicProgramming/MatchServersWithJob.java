package dynamicProgramming;

import org.junit.Test;

/**
 * http://www.careercup.com/question?id=6282171643854848
 * @author suketu
 *
 */
public class MatchServersWithJob {
    
    @Test
    public void testSuccess() {
        int[] serversCapacity = {8, 16, 8, 32};
        int[] jobs = {18, 4, 8, 4, 6, 6, 8, 8};
        
        System.out.println("testSuccess gives:" + canBeScheduledOnServer(serversCapacity, jobs));
    }
    
    @Test
    public void testFailures() {
        int[] serversCapacity = {3, 1};
        int[] jobs = {4};
        
        System.out.println("testFailure gives:" + canBeScheduledOnServer(serversCapacity, jobs));
    }
    
    public boolean canBeScheduledOnServer(int[] serversCapacity, int[] jobs) {
        
        if(isDone(jobs)) return true;
        
        return canBeScheduledOnServer(serversCapacity, jobs, 0);
        
    }

    private boolean canBeScheduledOnServer(int[] serversCapacity, int[] jobs, int lookAtServer) {
        if(isDone(jobs)) return true;

        if(lookAtServer >= serversCapacity.length){
            return false;
        }
        
        for(int i = 0; i < jobs.length; i++) {
            
            int jobValue = jobs[i];
            
            if(jobValue == 0) {
                continue;
            }
            
            if(jobValue <= serversCapacity[lookAtServer]) {
                serversCapacity[lookAtServer] -= jobValue;
                jobs[i] = 0;
                
                // call next
                if(canBeScheduledOnServer(serversCapacity, jobs, lookAtServer + 1) ||
                        canBeScheduledOnServer(serversCapacity, jobs, lookAtServer)) {
                    return true;
                }
                
                serversCapacity[lookAtServer] += jobValue;
                jobs[i] = jobValue;
            }
        }
        
        // if none of these worked then just skip this server all together and try the rest.
        if(canBeScheduledOnServer(serversCapacity, jobs, lookAtServer + 1)) {
            return true;
        }
        return false;
    }

    private boolean isDone(int[] jobs) {
        if(jobs == null) return false;
        
        boolean toReturn = true;
        
        for(int current: jobs) {
            if(current != 0) toReturn = false;
        }
        if(toReturn) {
            System.out.println("returning true from IsDone.");
        }
        return toReturn;
    }

}
