package geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;


public class Logical2DPlane {
    
    static Map<String, String> reverseDirections = new HashMap<String, String>(){{
        put("S", "N");
        put("N", "S");
        put("W", "E");
        put("E", "W");
    }};
    private static Logger logger = LogManager.getLogger(LogManager.class);

    @Test
    public void testSuccess() {
        List<String> relations = Arrays.asList("p1 SE p2",
                                               "p2 SE p3",
                                               "p3 NW p1");
        logger.info("So the relations are " + isLogical(relations));
    }
    
    
    public static boolean isLogical(List<String> relations) {

        Map<String, Map<String, Set<String>>> pointToDirToPoints = new HashMap<String, Map<String,Set<String>>>();
        
        
        for (String string : relations) {
            String[] split = string.split(" ");
            
            if(split.length != 3 ) {
                continue;
            }
            String fromPoint = split[0], toPoint = split[2], direction = split[1];
            logger.debug("fromPoint:" + fromPoint + " direction:" + direction + " toPoint:" + toPoint);
            
            
            String dir1 = ""+direction.charAt(0), dir2 = direction.length() == 1 ? null : "" +direction.charAt(1);
            
            addIfNeeded(pointToDirToPoints, fromPoint);
            addIfNeeded(pointToDirToPoints, toPoint);
            
            // add fromToDir
            boolean isSuccessful = true; 
            
            isSuccessful = addFromToDir(fromPoint, dir1, toPoint, pointToDirToPoints) && 
                    addFromToDir(toPoint, reverseDirections.get(dir1), fromPoint, pointToDirToPoints);
            
            
            if(dir2 != null) {
                isSuccessful = addFromToDir(fromPoint, dir2, toPoint, pointToDirToPoints) && 
                        addFromToDir(toPoint, reverseDirections.get(dir2), fromPoint, pointToDirToPoints);
            }
            
            if(!isSuccessful) {
                return false;
            }
        }
        
        return true;
    }


    private static boolean addFromToDir(String fromPoint, String dir1,
            String toPoint,
            Map<String, Map<String, Set<String>>> pointToDirToPoints) {
        
        if(dir1 == null) return true;
        
        String oppositeDirection = reverseDirections.get(dir1);
        
        
        // add to currentNode 
        Map<String, Set<String>> fromDirToPoints = pointToDirToPoints.get(fromPoint);
        
        if(fromDirToPoints.get(oppositeDirection).contains(toPoint)) {
            return false;
        }
        fromDirToPoints.get(dir1).add(toPoint);
        
        Set<String> oppositeDirPoints = fromDirToPoints.get(oppositeDirection);
        
        // validate that oppositePoints -> fromPoint -> toPoint, are also having their directions to Point as dir1.
        // if not add
        // if they have opposite pointing then return false. i.e. toPoint <- oppositePoint -> fromPoint -> toPoint
        for (String oppDirPoint : oppositeDirPoints) {
            
            if(pointToDirToPoints.get(oppDirPoint).get(oppositeDirection).contains(toPoint)) {
                return false;
            }
            else {
                pointToDirToPoints.get(oppDirPoint).get(dir1).add(toPoint);
            }
        }
        
        return true;
        
    }


    private static void addIfNeeded(
            Map<String, Map<String, Set<String>>> pointToDirToPoints,
            String fromPoint) {
        if(!pointToDirToPoints.containsKey(fromPoint)) {
            HashMap<String, Set<String>> baseDirMap = new HashMap<>();
            baseDirMap.put("N", new HashSet<String>());
            baseDirMap.put("S", new HashSet<String>());
            baseDirMap.put("E", new HashSet<String>());
            baseDirMap.put("W", new HashSet<String>());
            pointToDirToPoints.put(fromPoint, baseDirMap);
        }
    }

}
