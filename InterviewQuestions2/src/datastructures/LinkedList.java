package datastructures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class LinkedList<T extends Comparable<T>> implements List<T>{
    int size = 0;
    
    Node<T> head;
    
    public static void main(String[] args) {
        System.out.println("Starting out the linkedlist based test.");
        
        LinkedList<Integer> list = new LinkedList<>();
        list.add(4);
        list.add(5);
        list.add(6);

        
        list.remove(0);
        
        
        System.out.println("ListSize:" + list.size());
        
        System.out.println("Cotains4:" + list.contains(4));
        System.out.println("Cotains5:" + list.contains(5));



        System.out.println("Is the list empty:" + list.isEmpty());
        Iterator<Integer> iterator = list.iterator();
        System.out.print("[");
        while(iterator.hasNext()) {
            System.out.print(", " + iterator.next());
        }
        System.out.print("]");

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
    public boolean contains(Object o) {
        if(!(o instanceof Comparable)) {
            return false;
        }
        
        Iterator<T> iterator = this.iterator();
        while(iterator.hasNext()) {
            if(iterator.next().equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator(this.head, this);
    }
    
    private class LinkedListIterator implements Iterator<T> {
        public LinkedListIterator(Node<T> value, LinkedList<T> parent) {
            this.value = value;
            this.parent = parent;
        }

        private Node<T> value;
        private Node<T> previous;
        final LinkedList<T> parent;
        
        @Override
        public boolean hasNext() {
            return !(value == null);
        }

        @Override
        public T next() {
            if(value != null) {
                previous = value;
                value = value.next;
                return previous.value;
            }
            throw new NoSuchElementException("out of new values.");
        }

        @Override
        public void remove() {
            if(previous == null) {
                throw new IllegalStateException();
            }
            this.parent.remove(previous);
            previous = null;
        }
    }
    

    @Override
    public Object[] toArray() {
        Object[] toReturn = new Object[size];
        int i = 0;
        Iterator<T> iterator = this.iterator();
        while(iterator.hasNext()) {
            toReturn[i++] = iterator.next();
        }
        return toReturn;
    }

    @SuppressWarnings({ "unchecked", "hiding" })
    @Override
    public <T> T[] toArray(T[] a) {
        if(a.length < size) {
            return ((T[]) Arrays.copyOf(this.toArray(), size, a.getClass()));
        }
        System.arraycopy(this.toArray(), 0, a, 0, size);
        return a;
    }

    @Override
    public boolean add(T e) {
        if(this.head == null) {
            this.head = new Node<T>(e);
            this.size++;
            return true;
        }
        
        Node<T> current = head; 
        while(current.next != null) {
            current = current.next;
        }
        current.next = new Node<T>(e);
        this.size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        
        if(head == null) return false;
        
        if(this.head.value.equals(o)) {
            head.next = head;
            return true;
        }
        Node<T> current = this.head.next, previous = this.head;
        while(!(current.next == null)) {
            if(current.value.equals(o)) {
                previous.next = current.next;
                this.size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object object : c) {
            if(!this.contains(object)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        
        Node<T> current = this.head;
        while(current.next != null) current = current.next;
        for (T value : c) {
            current.next = new Node<T>(value);
            current = current.next;
        }
        this.size += c.size();
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        rangeCheck(index);
        
        // loop up
        Node<T> current = this.head;
        for(; index > 0; index--) {
            current = current.next;
        }
        Node<T> tailList = current.next;
        
        for (T t : c) {
            current.next = new Node<T>(t);
        }
        current.next = tailList;
        this.size += c.size();
        return true;
    }

    private void rangeCheck(int index) {
        if(index < 0 || index > size) throw new IndexOutOfBoundsException();
    }


    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object object : c) {
            this.remove(object);
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean retainAll(Collection<?> c) {
        if(c.size() == 0) {
            this.head = null;
            this.size = 0;
            return true;
        }
        
        Node<T> first = new Node<T>(null);
        Node<T> current = first;
        for (Object object : c) {
            
            current.next = new Node<T>((T)object);
            current = current.next;
        }
        this.head = first.next;
        this.size = c.size();
        return true;
    }

    @Override
    public void clear() {
        this.head = null;
        this.size = 0;
    }

    @Override
    public T get(int index) {
        rangeCheck(index);
        
        Node<T> current = this.head;
        for(; index > 0; index--) {
            current = current.next;
        }
        return current.value;
    }

    @Override
    public T set(int index, T element) {
        rangeCheck(index);
        Node<T> toReplace = this.head;
        for(; index > 0; index--) {
            toReplace = toReplace.next;
        }
        toReplace.value = element;
        return element;
    }

    @Override
    public void add(int index, T element) {
        
        rangeCheck(index);
        Node<T> toAddAt = this.head;
        for(; index > 0; index--) {
            toAddAt = toAddAt.next;
        }
        Node<T> tailList = toAddAt.next;
        toAddAt.next = new Node<T>(element);
        toAddAt.next.next = tailList;
        this.size++;
    }

    @Override
    public T remove(int index) {
        rangeCheck(index);
        
        if(this.size ==0) throw new IndexOutOfBoundsException();
        
        if(index == 0) {
            T toReturn = this.head.value;
            this.head = this.head.next;
            this.size--; 
            return toReturn;
        }
        
        Node<T> previous = this.head;
        Node<T> current = this.head.next;
        while(index > 1) {
            previous = current;
            current = current.next;
            index--;
        }
        this.size --;
        previous.next = current.next;
        return previous.value;
    }

    @Override
    public int indexOf(Object o) {
        Node<T> current = this.head;
        for(int i = 0; i < this.size; i++, current= current.next) {
            if(current.value.equals(o)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }
    
    private class Node<T extends Comparable<T>> {
        public Node(T value) {
            this.value = value;
        }
        public Node<T> next;
        public T value;
    }

}
