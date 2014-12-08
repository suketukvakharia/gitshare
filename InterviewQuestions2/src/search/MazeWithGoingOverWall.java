package search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MazeWithGoingOverWall {
    
    
    
    /**
     * 
     * @param grid 0 for traversible and 1 for un traversible.
     * @param startx
     * @param starty
     * @param endx
     * @param endy
     * @param goOverWalls
     */
    public static void findShortestPath(int[][] grid, int startx, int starty, int endx, int endy, int goOverWalls) {
        
        
        if(startx < 0 || starty < 0 || endx < 0 || endy < 0 || startx >= grid.length || grid.length == 0|| starty >= grid[0].length || endx >= grid.length || endy >= grid[0].length) {
            return;
        }
        int[][] wallsCrossedGrid = new int[grid.length][grid[0].length];
        
        boolean foundSolution = false;
        Point startingPoint = new Point(startx, starty);
        Queue<Point> currentQueue = new LinkedList<>(), nextQueue = new LinkedList<>();
        currentQueue.add(startingPoint);
        grid[startingPoint.getX()][startingPoint.getY()] = 2;
        
        
        while(!currentQueue.isEmpty() || !nextQueue.isEmpty()) {
            
            
            // for each point in the queu
            Point current = currentQueue.poll();
            int wallsCrossed  = wallsCrossedGrid[current.getX()][current.getY()];
            
            
            Point top = new Point(current.x, current.y + 1), bottom = new Point(current.x, current.y-1),
                    left = new Point(current.x-1, current.y), right = new Point(current.x+1, current.y);
            
            if(isValid(top, grid, wallsCrossedGrid, wallsCrossed, goOverWalls)) {
                if(top.x == endx && top.y == endy) {
                    foundSolution = true;
                    break;
                }
                nextQueue.add(top);
            }
            if(isValid(bottom, grid, wallsCrossedGrid, wallsCrossed, goOverWalls)) {
                if(bottom.x == endx && bottom.y == endy) {
                    foundSolution = true;
                    break;
                }
                nextQueue.add(bottom);
            }
            if(isValid(left, grid, wallsCrossedGrid, wallsCrossed, goOverWalls)) {
                if(left.x == endx && left.y == endy) {
                    foundSolution = true;
                    break;
                }
                nextQueue.add(left);
            }
            if(isValid(right, grid, wallsCrossedGrid, wallsCrossed, goOverWalls)) {
                if(right.x == endx && right.y == endy) {
                    foundSolution = true;
                    break;
                }
                nextQueue.add(right);
            }
            
            
        }
        
    }
    

    private static boolean isValid(Point point, int[][] grid, int[][] wallsCrossedGrid, int wallsCrossed, int goOverWalls) {
        
        if(point.x < 0 || point.y < 0 || point.x >= grid.length || point.y >= grid[0].length) return false;
        
        boolean toReturn = false;
        
        if(grid[point.getX()][point.getY()] == 0 || (grid[point.getX()][point.getY()] == 1 && wallsCrossed < goOverWalls)) {
            toReturn = true;
        }
        else if(grid[point.getX()][point.getY()] > 1 && wallsCrossedGrid[point.getX()][point.getY()] > wallsCrossed) {
            toReturn = true;
        }
        
        
        return toReturn;
    }


    private static class Point {
        
        private final  int x, y;
        
        public Point(int x, int y) {
            this.x = x; this.y = y;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
    }

}
