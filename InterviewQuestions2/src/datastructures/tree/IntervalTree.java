package datastructures.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntervalTree {

    private static Logger logger = LogManager.getLogger(IntervalTree.class);

    private IntervalTreeNode rootNode;
    
    public void addIntervals(List<Interval> toAdd) {
        this.rootNode = this.getIntervalTreeNode(toAdd);
    }
    
    private IntervalTreeNode getIntervalTreeNode(List<Interval> toAdd) {
        if(toAdd.isEmpty()) {
            return null;
        }
        int rangeLow = Integer.MAX_VALUE, rangeHigh = Integer.MIN_VALUE;
        
        for(Interval curInterval : toAdd) {
            if(rangeLow > curInterval.from) {
                rangeLow = curInterval.from;
            }
            if(rangeHigh < curInterval.to){
                rangeHigh = curInterval.to;
            }
        }
        
        int centerPoint = (rangeLow + rangeHigh) /2;
        
        List<Interval> intersectingIntervals = new ArrayList<Interval>(), 
                        leftInterval = new ArrayList<Interval>(), 
                        rightInterval = new ArrayList<Interval>();
        
        for(Interval curInterval: toAdd) {
            if(curInterval.from <= centerPoint && curInterval.to >= centerPoint) {
                intersectingIntervals.add(curInterval);
            }
            else if(curInterval.from < centerPoint && curInterval.to < centerPoint) {
                leftInterval.add(curInterval);
            }
            else {
                rightInterval.add(curInterval);
            }
        }
        
        List<Interval> sortedTo = new ArrayList<Interval>(intersectingIntervals);
        return new IntervalTreeNode(centerPoint, intersectingIntervals, sortedTo, 
                this.getIntervalTreeNode(leftInterval),
                this.getIntervalTreeNode(rightInterval));
        
    }
    
    
    public int getIntersections(Interval intervald) {
        return getIntersections(intervald, this.rootNode);
    }
    private int getIntersections(Interval interval,
            IntervalTreeNode node) {
        if(node == null) {
            return 0;
        }
        
        int currentIntersections = node.getIntersections(interval);
        
        if(node.centerPoint > interval.to) {
            currentIntersections += this.getIntersections(interval, node.left);
        }
        else if(node.centerPoint < interval.from){
            currentIntersections += this.getIntersections(interval, node.right);
        } else {
            currentIntersections += this.getIntersections(interval, node.left);
            currentIntersections += this.getIntersections(interval, node.right);
        }
        
        return currentIntersections;
    }

    private class IntervalTreeNode {
        public IntervalTreeNode(int centerPoint, List<Interval> sortedFrom,
                List<Interval> sortedTo, IntervalTreeNode left,
                IntervalTreeNode right) {
            
            logger.debug("Creating Interval Tree Node. CenterPoint:" + centerPoint + " with intervals:" + sortedFrom);
            this.centerPoint = centerPoint;
            Collections.sort(sortedFrom, new FromComparator());
            Collections.sort(sortedTo, new ToComparator());
            this.sortedFrom = sortedFrom;
            this.sortedFrom = sortedFrom;
            this.sortedTo = sortedTo;
            this.left = left;
            this.right = right;
        }
        
        public int getIntersections(Interval interval) {
            
            logger.debug("Getting Intersections for interval:" + interval + " for IntervalTreeNode with center" + this.centerPoint + " from intervals:" + sortedFrom + " and to:" + sortedTo);
            
            // if this goes over the center point then it intersects all points from here.
            if(interval.from <= this.centerPoint && interval.to >= this.centerPoint) {
                logger.debug("returning:" + this.sortedFrom.size() + " as the number of intersections");
                return this.sortedFrom.size();
            }
            
            // if not then see how many intervals actually cross it.
            
            Set<Interval> intersecting = new HashSet<>();
            if(interval.from < this.centerPoint) {
                int fromIndex = Collections.binarySearch(this.sortedFrom, interval, new FromComparator());
                if(fromIndex < 0) fromIndex = (fromIndex + 1) * -1;
                logger.debug("FromIndex from < centerPoint:" + fromIndex);
                for(int i = 0; i < fromIndex && i < this.sortedFrom.size(); i++) {
                    intersecting.add(this.sortedFrom.get(i));
                }
                
                while(fromIndex >= 0 && fromIndex < this.sortedFrom.size() && this.sortedFrom.get(fromIndex).from == interval.from) {
                    intersecting.add(this.sortedFrom.get(fromIndex));
                    fromIndex--;
                }
            }
            else {
                int fromIndex = Collections.binarySearch(this.sortedTo, new Interval(0, interval.from), new ToComparator());
                if(fromIndex < 0) fromIndex = (fromIndex + 1) * -1;
                logger.debug("FromIndex from > centerPoint:" + fromIndex);
                for(int i = fromIndex; i < this.sortedTo.size(); i++) 
                    intersecting.add(this.sortedTo.get(i));
            }
            
            if(interval.to < this.centerPoint) {
                int fromIndex = Collections.binarySearch(this.sortedFrom, new Interval(interval.to, 0), new FromComparator());
                if(fromIndex < 0) fromIndex = (fromIndex + 1) * -1;
                logger.debug("FromIndex to < centerPoint:" + fromIndex);
                for(int i = 0; i < fromIndex && i < this.sortedFrom.size(); i++) {
                    intersecting.add(this.sortedFrom.get(i));
                }
                
                while(fromIndex >= 0 && fromIndex < this.sortedFrom.size() && this.sortedFrom.get(fromIndex).from == interval.to) {
                    intersecting.add(this.sortedFrom.get(fromIndex));
                    fromIndex--;
                }
            }
            else {
                int fromIndex = Collections.binarySearch(this.sortedTo, interval, new ToComparator());
                if(fromIndex < 0) fromIndex = (fromIndex + 1) * -1;
                logger.debug("FromIndex to > centerPoint:" + fromIndex);
                for(int i = fromIndex; i < this.sortedTo.size(); i++) 
                    intersecting.add(this.sortedTo.get(i));
            }
            
            logger.debug("returning:" + intersecting.size() + " as the number of intersections");
            return intersecting.size();
        }

        private int centerPoint;
        List<Interval> sortedFrom, sortedTo;
        IntervalTreeNode left, right;
    }
    
    private class FromComparator implements Comparator<Interval> {
        @Override
        public int compare(Interval o1, Interval o2) {
            return o1.from - o2.from;
        }
    }
    
    private class ToComparator implements Comparator<Interval> {
        @Override
        public int compare(Interval o1, Interval o2) {
            return o1.to - o2.to;
        }
    }
}
