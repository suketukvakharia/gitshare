package matrix;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

/**
 * Better solution is to do it by putting points in a queue.
 * http://www.careercup.com/question?id=4716965625069568
 * @author suketu
 *
 */
public class ShortestPathToGuard {
    
    @Test
    public void testShortestPathToGuard() {
        
        int[][] toTest = {  {-1, 0, 0},
                            {-2, 0, -1},
                            {-2, 0, 0} };
        
        print2DArr(toTest);
        
        System.out.println("Marking smallest path to entries");
        markShortestPathToGuardQueue(toTest);
//        markShortestPathToGuard(toTest);
        print2DArr(toTest);
        
    }
    
    private class Point {
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public int x, y;
    }
    private void markShortestPathToGuardQueue(int[][] toTest) {
        
        
        // scan through and put all the guards in the queue for starting the first level.
        Queue<Point> queue = new LinkedList<ShortestPathToGuard.Point>();
        
        for(int i = 0; i < toTest.length; i++) {
            for(int j = 0; j < toTest[i].length; j++) {
                if(toTest[i][j] == -1) {
                    queue.add(new Point(i,j));
                }
            }
        }
        queue.add(null);
        
        int stepsToReachGuard = 1;
        while(!queue.isEmpty()) {
            
            Point point = queue.poll();
            if(point == null) {
                if(queue.isEmpty()) {
                    break;
                }
                stepsToReachGuard++;
                queue.add(null);
                continue;
            }
            
            // examine left, right, up, down
            Point left = new Point(point.x-1, point.y), right = new Point(point.x+1, point.y), up = new Point(point.x, point.y+1), down = new Point(point.x, point.y-1);
            
            processPoint(toTest, queue, stepsToReachGuard, left);
            processPoint(toTest, queue, stepsToReachGuard, right);
            processPoint(toTest, queue, stepsToReachGuard, up);
            processPoint(toTest, queue, stepsToReachGuard, down);
        }
        
    }


    private void processPoint(int[][] toTest, Queue<Point> queue,
            int stepsToReachGuard, Point point) {
        if(isValid(point, toTest)) {
            toTest[point.x][point.y]= stepsToReachGuard;
            queue.add(point);
        }
    }


    private boolean isValid(Point point, int[][] toTest) {
        if(point.x < 0 || point.y < 0 || point.x >= toTest.length || point.y >= toTest[0].length || toTest[point.x][point.y] != 0 )
            return false;
        return true;
    }


    private void print2DArr(int[][] toTest) {
        
        for(int i = 0; i < toTest.length; i++) {
            System.out.println(Arrays.toString(toTest[i]));
        }
        
    }

    /**
     * -2 is blocked (B)
     * -1 is Guard (G)
     * 0 is where we need to put in a value.
     * @param a
     */
    public void markShortestPathToGuard(int[][] a) {
        
        int updatedValues;
        int searchFor = -1;
        int stepsToReach = 1;
        do {
            updatedValues = 0;
            for(int j = 0; j < a.length; j++) {
                for(int k = 0; k < a[j].length; k++) {
                    
                    if(a[j][k] == searchFor) {
                        if(updateable(a, j-1, k)) {
                            a[j-1][k] = stepsToReach;
                            updatedValues++;
                        }
                        if(updateable(a, j+1, k)) {
                            a[j+1][k] = stepsToReach;
                            updatedValues++;
                        }
                        
                        if(updateable(a, j, k-1)) {
                            a[j][k-1] = stepsToReach;
                            updatedValues++;
                        }
                        if(updateable(a, j, k+1)) {
                            a[j][k+1] = stepsToReach;
                            updatedValues++;
                        }
                    }
                }
            }
            searchFor = stepsToReach;
            stepsToReach++;
        } while(updatedValues != 0);
    }

    private boolean updateable(int[][] a, int j, int k) {
        if( a == null || j < 0 || j >= a.length || k < 0 || k >= a[j].length || a[j][k] != 0) {
            return false;
        }
        return true;
    }

}
