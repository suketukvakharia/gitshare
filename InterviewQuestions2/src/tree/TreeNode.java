package tree;


public class TreeNode<T extends Comparable<T>> implements ITreeNode<T> {
    ITreeNode<T> left, right;
    T value;
    @Override
    public ITreeNode<T> getLeft() {
        return this.left;
    }
    @Override
    public void setLeft(ITreeNode<T> left) {
        this.left = left;
    }
    @Override
    public ITreeNode<T> getRight() {
        return this.right;
    }
    @Override
    public void setRight(ITreeNode<T> right) {
        this.right = right;
    }
    @Override
    public T getValue() {
        return this.value;
    }
    @Override
    public void setValue(T value) {
        this.value = value;
    }
}
