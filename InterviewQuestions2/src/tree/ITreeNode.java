package tree;

public interface ITreeNode<T extends Comparable<T>> {
    public ITreeNode<T> getLeft();
    public void setLeft(ITreeNode<T> left);
    
    public ITreeNode<T> getRight();
    public void setRight(ITreeNode<T> right);

    public T getValue();
    public void setValue(T value);
}
