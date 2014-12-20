package datastructures.list;

import java.util.Random;

import org.junit.Test;




public class InternalArrayList<E> implements InternalIList<E> {

    @Test
    public void testSimple() {
        
        InternalArrayList<Integer> list = new InternalArrayList<Integer>();
        
        System.out.println("shouldn't contian 4:" + list.cotains(4));
        
        list.add(4);
        System.out.println("should contian 4:" + list.cotains(4));
        printSize(list);
        
        
        Random rand = new Random();
        for(int i = 1; i < 1000; i++) {
            list.add(rand.nextInt(100));
        }
        printSize(list);
        
        for(int i = 1; i < 10000; i++) {
            list.remove(new Integer(rand.nextInt(100)));
        }
        
        for(int i = 0; i < list.size; i++ ) {
            System.out.print(list.get(i) + ", ");
        }
        System.out.println("done");
    }
    
    private void printSize(InternalArrayList<Integer> list) {
        System.out.println("list size is:" + list.size);
    }

    private int size = 0;
    
    private Object[] arr;
    
    public InternalArrayList() {
        arr = new Object[10]; // default size is 10.
    }
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean cotains(Object o) {
        for(int i = 0; i < this.size; i++) {
            if(arr[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Append the element.
     */
    @Override
    public boolean add(E e) {
        if(this.arr.length == this.size) {
            this.adjustArr();
        }
        this.arr[this.size] = e;
        this.size++;
        return true;
    }

    private void adjustArr() {
        // double array if full.
        if(this.arr.length == this.size) {
            doubleArr();
        }
    }

    private void doubleArr() {
        Object[] temp = new Object[this.arr.length * 2];
        for(int i = 0; i < this.arr.length; i++) {
            temp[i] = this.arr[i];
        }
        this.arr = temp;
    }

    @Override
    public boolean remove(Object o) {
        int oAt = this.indexOf(o);
        
        if(oAt == -1) return false;
        
        this.shift(arr, oAt+1, oAt);
        this.arr[this.size - 1] = null;
        this.size--;
        return true;
    }

    private void shift(Object[] arr2, int from, int to) {
        
        if(from > to) {
            if(to + this.size - from >= this.arr.length) {
                doubleArr();
            }
        }
        else {
            if(from + this.size - to >= this.arr.length) {
                doubleArr();
            }
        }
        
        for(; from < this.arr.length && to < this.arr.length; from++, to++) {
            this.arr[to] = this.arr[from];
        }
    }

    @Override
    public E get(int index) {
        rangeCheck(index);
        return getFromArr(index);
    }

    @SuppressWarnings("unchecked")
    private E getFromArr(int index) {
        return (E) this.arr[index];
    }

    @SuppressWarnings("unchecked")
    @Override
    public E set(int index, E element) {
        rangeCheck(index);
        
        Object temp = this.arr[index];
        this.arr[index] = element;
        return (E) temp;
    }

    private void rangeCheck(int index) {
        if(index >= this.size || index < 0) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void add(int index, E element) {
        if(index > this.size || index < 0) {
            throw new IllegalArgumentException();
        }
        
        if(index == this.size) {
            doubleArr();
        }
        this.shift(this.arr, index, index+1);
        this.arr[index] = element;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index) {
        rangeCheck(index);
        Object temp = this.arr[index];
        this.shift(this.arr, index+1, index);
        this.size--;
        return (E) temp;
    }

    @Override
    public int indexOf(Object o) {
        for(int i = 0; i < this.size; i++) {
            if(this.arr[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

}
