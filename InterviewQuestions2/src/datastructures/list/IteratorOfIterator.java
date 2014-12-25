package datastructures.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.junit.Test;

public class IteratorOfIterator implements Iterator<Integer>{
    
    
    public IteratorOfIterator(Iterator<Object> mainIterator) {
        this.mainIterator = mainIterator;
        this.iterators = new Stack<Iterator<Object>>();
    }

    private Iterator<Object> mainIterator;
    
    private Iterator<Object> currentIterator;
    
    private Stack<Iterator<Object>> iterators;
    
    private Integer nextValue;
    
    @Override
    public boolean hasNext() {
        if(this.nextValue != null) 
            return true;
        
        
        setNextValidIterator();
        
        
        if(this.nextValue != null) 
            return true;
        else 
            return false;
    }

    @Override
    public Integer next() {
        if(this.nextValue != null) {
            Integer toReturn = this.nextValue;
            this.nextValue = null;
            return toReturn;
        }
        
        setNextValidIterator();
        
        if(this.nextValue != null) {
            Integer toReturn = this.nextValue;
            this.nextValue = null;
            return toReturn;
        }
        else {
            return null;
        }
    }
    
    private void setNextValidIterator() {
        
        while(true) {
            // check currentIterator
            if(currentIterator != null && currentIterator.hasNext()) {
                Object nextVal = currentIterator.next();
                if(nextVal instanceof Integer) {
                    this.nextValue = (Integer) nextVal;
                    return;
                }
                else {
                    iterators.add(currentIterator);
                    currentIterator = (Iterator<Object>) nextVal;
                    continue;
                }
            }
            
            while(!iterators.isEmpty() && !iterators.peek().hasNext()) {
                iterators.pop();
            }
            
            if(iterators.isEmpty() && this.mainIterator.equals(this.currentIterator)) break;
            
            // use from stack if not use the main one.
            if(iterators.isEmpty()) {
                this.currentIterator = this.mainIterator;
            }
            else {
                currentIterator = iterators.pop();
            }
        }
        
    }

    @Override
    public void remove() {
    }
}
