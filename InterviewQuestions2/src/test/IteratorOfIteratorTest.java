package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import datastructures.list.IteratorOfIterator;

public class IteratorOfIteratorTest {

    
    @Test
    public void testSuccess() {
        List<Integer> l1 = Arrays.asList(4,5,6), l2 = Arrays.asList(1,2), l3 = Arrays.asList(3), l4 = Arrays.asList(4,5);
        

        List<Object> compList = new LinkedList<Object>();
        compList.add(3);
        compList.add(l4.iterator());
        
        List<Object> iteratorOfIterators = new ArrayList<Object>();
        
        iteratorOfIterators.add(new Integer(55));
        
        List<Object> temp = new ArrayList<Object>();
        temp.addAll(l1);
        
        iteratorOfIterators.add(temp.iterator());
        
        temp = new ArrayList<Object>();
        temp.addAll(l2);
        iteratorOfIterators.add(temp.iterator());
        
        iteratorOfIterators.add(compList.iterator());
        
        temp = new ArrayList<Object>();
        temp.addAll(l3);
        iteratorOfIterators.add(temp.iterator());
        
        iteratorOfIterators.add(new Integer(65));
        
        Iterator iterator = new IteratorOfIterator(iteratorOfIterators.iterator());
        

        while(iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        
    }
}
