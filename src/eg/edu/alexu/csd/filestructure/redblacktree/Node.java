package eg.edu.alexu.csd.filestructure.redblacktree;

public class Node<T extends Comparable <T> , V > implements INode < T  , V >  {

    private boolean color ;
    private INode<T,V> leftChild ;
    private INode<T,V> rightChild ;
    private INode<T,V> parent ;
    private T key ;
    private V value ;

    public Node() {
    }

    public Node(boolean color, INode<T, V> leftChild, INode<T, V> rightChild, INode<T, V> parent, T key, V value) {
        this.color = color;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.parent = parent;
        this.key = key;
        this.value = value;
    }

    public Node(T key, V value) {
        this(RED,null,null,null,key,value);
    }

    @Override
    public void setParent(INode<T, V> parent) {
        this.parent =  parent ;
    }

    @Override
    public INode<T, V> getParent() {
        return this.parent ;
    }

    @Override
    public void setLeftChild(INode<T, V> leftChild) {
        this.leftChild = leftChild ;
    }

    @Override
    public INode<T, V> getLeftChild() {
        return this.leftChild ;
    }

    @Override
    public void setRightChild(INode<T, V> rightChild) {
        this.rightChild = rightChild ;
    }

    @Override
    public INode<T, V> getRightChild() {
        return this.rightChild ;
    }

    @Override
    public T getKey() {
        return this.key ;
    }

    @Override
    public void setKey(T key) {
        this.key = key ;
    }

    @Override
    public V getValue() {
        return this.value ;
    }

    @Override
    public void setValue(V value) {
        this.value = value ;
    }

    @Override
    public boolean getColor() {
        return this.color ;
    }

    @Override
    public void setColor(boolean color) {
        this.color = color ;
    }

    @Override
    public boolean isNull() {
        return this == null ;
    }

}
