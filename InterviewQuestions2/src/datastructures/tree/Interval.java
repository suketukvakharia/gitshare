package datastructures.tree;

public class Interval {
    
    @Override
    public String toString() {
        return "{" + from + "," + to + "}";
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + from;
        result = prime * result + to;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Interval other = (Interval) obj;
        if (from != other.from)
            return false;
        if (to != other.to)
            return false;
        return true;
    }


    public Interval(int from, int to) {
        this.from = from;
        this.to = to;
    }


    public final int from, to;
}