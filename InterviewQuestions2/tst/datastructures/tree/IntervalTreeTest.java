package datastructures.tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;







import java.util.Set;

import math.GenerateRandom;

import org.junit.Assert;
import org.junit.Test;

import datastructures.tree.Interval;
import datastructures.tree.IntervalTree;

public class IntervalTreeTest {

    
    @Test 
    public void testSimple(){
        
        Interval simple = new Interval(0,1);
        List<Interval> toTest = new ArrayList<>();
        
        toTest.add(simple);
        
        IntervalTree tree = new IntervalTree();
        
        tree.addIntervals(toTest);
        
        Assert.assertEquals(1, tree.getIntersections(simple));
    }
    
    @Test 
    public void testSimpleZeroReturn(){
        
        Interval simple = new Interval(9,10);
        
        List<Interval> toTest = new ArrayList<>();
        
        toTest.add(new Interval(-1,0));
        toTest.add(new Interval(3,5));
        toTest.add(new Interval(2,5));
        
        IntervalTree tree = new IntervalTree();
        
        tree.addIntervals(toTest);
        
        Assert.assertEquals(0, tree.getIntersections(simple));
    }
    
    @Test
    public void testOnRandomDataSet() throws InterruptedException {
        List<Interval> intervals = generateRandomIntervals(100);
        
        Set<Interval> dedupeSet = new HashSet<>(intervals);
        
        intervals = new ArrayList<Interval>(dedupeSet);
        
        System.out.println(intervals.toString());
        IntervalTree tree = new IntervalTree();
        tree.addIntervals(intervals);
        for(Interval current: intervals) {
            
            System.out.println("Testing for interval" + current);
            int expected = getIntersectionsFor(intervals, current);
            
            int actual = tree.getIntersections(current);
            System.out.println("good");
            Assert.assertEquals(expected, actual);
        }
    }
    
    private int getIntersectionsFor(List<Interval> allIntervals, Interval interval) {
        
        int toReturn = 0;
        
        for(Interval currentInterval: allIntervals) {
            
            if(currentInterval.from <= interval.from && currentInterval.to >= interval.from) {
                toReturn++;
            }
            else if(currentInterval.from <= interval.to && currentInterval.to >= interval.to) {
                toReturn++;
            }
            else if(interval.from <= currentInterval.from && interval.to >= currentInterval.to){
                toReturn++;
            }
        }
        return toReturn;
        
    }
    
    private List<Interval> generateRandomIntervals(int count) {
        int upto = 1000;
        List<Interval> toReturn = new ArrayList<Interval>();
        
        for(int i = 0; i < count; i++) {
            
            int from = GenerateRandom.next(30)%upto;
            
            int to = GenerateRandom.next(30)%upto + from;
            toReturn.add(new Interval(from, to));
        }
        return toReturn;
    }
}
