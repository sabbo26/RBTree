package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;

public class RBtree<T extends Comparable<T>, V> implements IRedBlackTree<T,V> {

    private INode<T,V> root ;

    public RBtree() {
    }

    public RBtree(INode<T, V> root) {
        this.root = root;
    }

    @Override
    public INode<T, V> getRoot() {
        return this.root ;
    }

    @Override
    public boolean isEmpty() {
        return root == null ;
    }

    @Override
    public void clear() {
        this.root = null ;
    }

    @Override
    public V search(T key) {
        if(key == null)
            throw new RuntimeErrorException(new Error("search with null isn't allowed"));
        INode<T,V> current = this.root ;
        while(current != null ){
            if(key.compareTo(current.getKey()) == 0)
            {
                return current.getValue();
            }
            else if ( key.compareTo(current.getKey()) < 0 )
            {
                current = current.getLeftChild();
            }
            else
            {
                current = current.getRightChild();
            }
        }
        return null ;
    }

    @Override
    public boolean contains(T key) {
        return search(key) != null ;
    }

    @Override
    public void insert(T key, V value) {
        if(key == null || value == null)
            throw new RuntimeErrorException(new Error("search with null isn't allowed"));
        INode<T,V> extra = new Node<>(key,value);
        if(this.root == null )                       // if the tree is empty insert in the root
        {
            extra.setColor(INode.BLACK);
            this.root = extra ;
            return;
        }
        INode<T,V> current = this.root ;
        while(true){
            checkColorTopDown(current);
            if(key.compareTo(current.getKey()) == 0)
            {
                current.setValue(value);
                return;
            }
            else if (key.compareTo(current.getKey()) < 0)
            {
                if(current.getLeftChild() == null )
                {
                    current.setLeftChild(extra);
                    extra.setParent(current);
                    checkViolationRules(extra);
                    return;
                }
                else
                    current = current.getLeftChild();
            }
            else{
                if(current.getRightChild() == null )
                {
                    current.setRightChild(extra);
                    extra.setParent(current);
                    checkViolationRules(extra);
                    return;
                }
                else
                    current = current.getRightChild();
            }
        }
    }

    @Override
    public boolean delete(T key) {
        if(key == null)
            throw new RuntimeErrorException(new Error("search with null isn't allowed"));
        INode<T,V> current = this.root ;
        while(true){
            if (current == null )
                return false ;
            if(key.compareTo(current.getKey()) == 0)
            {
                break ;
            }
            else if ( key.compareTo(current.getKey()) < 0 )
            {
                current = current.getLeftChild();
            }
            else
            {
                current = current.getRightChild();
            }
        }
        anotherDelete(current);
        return true ;
    }

    private void anotherDelete (INode<T,V> current ){
        if(current.getLeftChild() != null && current.getRightChild() != null){
            INode<T,V> replacement = findSuccessor(current);
            current.setKey(replacement.getKey());
            current.setValue(replacement.getValue());
            anotherDelete(replacement);
        }
        else if(current.getLeftChild() != null || current.getRightChild() != null){
            INode<T,V> replacement = null ;
            if (current.getRightChild() != null )
            {
                replacement = current.getRightChild();
            }
            else if (current.getLeftChild() != null )
            {
                replacement = current.getLeftChild();
            }
            if(current == this.root){
                replacement.setParent(null);
                this.root = replacement;
                this.root.setColor(INode.BLACK);
                return;
            }
            replacement.setParent(current.getParent());
            if(current == current.getParent().getLeftChild()){
                current.getParent().setLeftChild(replacement);
            }
            else
                current.getParent().setRightChild(replacement);
            replacement.setColor(INode.BLACK);
            return;
        }
        else
        {
            if(current == this.root){
                this.root = null ;
                return ;
            }
            if(current.getColor() == INode.BLACK){
                removeDoubleBlack(current , true );
            }
            else{
                if(current == current.getParent().getLeftChild())
                    current.getParent().setLeftChild(null);
                else
                    current.getParent().setRightChild(null);
            }
        }
    }

    private void removeDoubleBlack ( INode<T,V> current , boolean isNull ){
        INode<T,V> w = getSibling(current);
        if(w != null && w.getColor() == INode.RED ){
            if(current == current.getParent().getLeftChild()){
                rotateLeft(current.getParent());
                w.getLeftChild().setColor(!w.getLeftChild().getColor());
            }
            else{
                rotateRight(current.getParent());
                w.getRightChild().setColor(!w.getRightChild().getColor());
            }
            w.setColor(!w.getColor());
            removeDoubleBlack(current,isNull);
        }
        else if ( w != null && w.getColor() == INode.BLACK  &&( (w.getLeftChild() != null && w.getLeftChild().getColor() == INode.RED) || (w.getRightChild() != null && w.getRightChild().getColor() == INode.RED)) ){
            if(w == w.getParent().getLeftChild()  ){
                if(w.getLeftChild() != null && w.getLeftChild().getColor() == INode.RED ){
                    if (isNull)
                        w.getParent().setRightChild(null);
                    rotateRight(w.getParent());
                    if(w.getRightChild().getColor() == INode.RED){
                        w.setColor(INode.RED);
                        w.getLeftChild().setColor(INode.BLACK);
                        w.getRightChild().setColor(INode.BLACK);
                    }
                    else{
                        w.getLeftChild().setColor(INode.BLACK);
                    }
                }
                else if (w.getRightChild() != null && w.getRightChild().getColor() == INode.RED){
                    w.setLeftChild(w.getRightChild());
                    w.setRightChild(null);
                    V temp = w.getValue();
                    T tempo = w.getKey();
                    w.setKey(w.getLeftChild().getKey());
                    w.setValue(w.getLeftChild().getValue());
                    w.getLeftChild().setValue(temp);
                    w.getLeftChild().setKey(tempo);
                    if (isNull)
                        w.getParent().setRightChild(null);
                    rotateRight(w.getParent());
                    if(w.getRightChild().getColor() == INode.RED){
                        w.setColor(INode.RED);
                        w.getLeftChild().setColor(INode.BLACK);
                        w.getRightChild().setColor(INode.BLACK);
                    }
                    else{
                        w.getLeftChild().setColor(INode.BLACK);
                    }
                }
            }
            else if (w == w.getParent().getRightChild()){
                if(w.getRightChild() != null && w.getRightChild().getColor() == INode.RED){
                    if (isNull)
                        w.getParent().setLeftChild(null);
                    rotateLeft(w.getParent());
                    if(w.getLeftChild().getColor() == INode.RED){
                        w.setColor(INode.RED);
                        w.getLeftChild().setColor(INode.BLACK);
                        w.getRightChild().setColor(INode.BLACK);
                    }
                    else{
                        w.getRightChild().setColor(INode.BLACK);
                    }
                }
                else if (w.getLeftChild() != null && w.getLeftChild().getColor() == INode.RED){
                    w.setRightChild(w.getLeftChild());
                    w.setLeftChild(null);
                    V temp = w.getValue();
                    T tempo = w.getKey();
                    w.setKey(w.getRightChild().getKey());
                    w.setValue(w.getRightChild().getValue());
                    w.getRightChild().setValue(temp);
                    w.getRightChild().setKey(tempo);
                    if (isNull)
                        w.getParent().setLeftChild(null);
                    rotateLeft(w.getParent());
                    if(w.getLeftChild().getColor() == INode.RED){
                        w.setColor(INode.RED);
                        w.getLeftChild().setColor(INode.BLACK);
                        w.getRightChild().setColor(INode.BLACK);
                    }
                    else{
                        w.getRightChild().setColor(INode.BLACK);
                    }
                }
            }
        }
        else if (w != null && w.getColor() == INode.BLACK){
            w.setColor(INode.RED);
            if(w == w.getParent().getRightChild() && isNull )
                w.getParent().setLeftChild(null);
            else if ( w == w.getParent().getLeftChild() && isNull )
                w.getParent().setRightChild(null);
            if(w.getParent().getColor() == INode.BLACK && w.getParent() != this.root ){
                removeDoubleBlack(w.getParent(),false);
            }
            else if (w.getParent().getColor() == INode.RED)
                w.getParent().setColor(INode.BLACK);
        }
    }

    private void checkCase (INode<T,V> x , INode<T,V> xp , INode<T,V> w )
    {
        if( x != null  && x.getColor() == INode.RED){
            x.setColor(INode.BLACK);
            return;
        }
        else if (x == null || x.getColor() == INode.BLACK){
            if(w != null && w.getColor()== INode.RED ){
                w.setColor(INode.BLACK);
                xp.setColor(INode.RED);
                if ( x == xp.getRightChild()){
                    rotateRight(xp);
                    w = xp.getLeftChild();
                }
                else{
                    rotateLeft(xp);
                    w = xp.getRightChild();
                }
                checkCase(x,xp,w);
                return;
            }
            else if (w != null && w.getColor() == INode.BLACK){
                if((w.getLeftChild()== null || w.getLeftChild().getColor()==INode.BLACK)
                        && (w.getRightChild() == null || w.getRightChild().getColor() == INode.BLACK)){
                    w.setColor(INode.RED);
                    x = xp ;
                    if(x.getColor() == INode.RED){
                        x.setColor(INode.BLACK);
                        return;
                    }
                    else{
                        xp = xp.getParent();
                        if(xp == null)
                            return ;
                        if(x == xp.getLeftChild()){
                            w = xp.getRightChild();
                        }
                        else
                            w = xp.getLeftChild();
                        checkCase(x,xp,w);
                        return;
                    }
                } else if (  (x == xp.getLeftChild() && w.getLeftChild() != null && w.getLeftChild().getColor() == INode.RED && (w.getRightChild() == null || w.getRightChild().getColor() == INode.BLACK))
                        || (x == xp.getRightChild() && w.getRightChild() != null && w.getRightChild().getColor() == INode.RED && (w.getLeftChild() == null || w.getLeftChild().getColor() == INode.BLACK) )) {
                    if(w.getLeftChild() == null || w.getLeftChild().getColor()==INode.BLACK)
                        w.getRightChild().setColor(INode.BLACK);
                    else
                        w.getLeftChild().setColor(INode.BLACK);
                    w.setColor(INode.RED);
                    if(x == xp.getRightChild()){
                        rotateLeft(w);
                        w = xp.getLeftChild();
                    }
                    else{
                        rotateRight(w);
                        w = xp.getRightChild();
                    }
                    checkCase(x,xp,w);
                    return;
                }
                else if ( (x == xp.getLeftChild() && w.getRightChild() != null && w.getRightChild().getColor()==INode.RED) ||
                        (x == xp.getRightChild() && w.getLeftChild() != null && w.getLeftChild().getColor()==INode.RED) ){
                    w.setColor(xp.getColor());
                    xp.setColor(INode.BLACK);
                    if(x == xp.getLeftChild()){
                        w.getRightChild().setColor(INode.BLACK);
                        rotateLeft(xp);
                    }
                    else{
                        w.getLeftChild().setColor(INode.BLACK);
                        rotateRight(xp);
                    }
                }
            }
        }
    }

    private INode<T,V> findSuccessor (INode<T,V> topNode)
    {
       INode<T,V> current = topNode.getRightChild();
       while(current.getLeftChild() != null){
           current = current.getLeftChild();
       }
       return current ;
    }

    private void rotateRight(INode<T,V> topNode){
        if(topNode.getLeftChild() == null )              // the top node must have left child
            return;
        if(topNode != this.root)                                              // check if top node has parent
        {
            if(topNode == topNode.getParent().getLeftChild()){
                topNode.getParent().setLeftChild(topNode.getLeftChild());
                topNode.getLeftChild().setParent(topNode.getParent());
            }
            else{
                topNode.getParent().setRightChild(topNode.getLeftChild());
                topNode.getLeftChild().setParent(topNode.getParent());
            }
        }
        topNode.setParent(topNode.getLeftChild());
        topNode.setLeftChild(topNode.getLeftChild().getRightChild());
        if(topNode.getLeftChild() != null)
            topNode.getLeftChild().setParent(topNode);
        topNode.getParent().setRightChild(topNode);
        if(topNode.getParent().getParent() == topNode)                         // when the top node doesn't have parent change the root after rotation
        {
            topNode.getParent().setParent(null);
            this.root = topNode.getParent();
        }
    }

    private void rotateLeft (INode<T,V> topNode){
        if(topNode.getRightChild() == null )
            return;
        if(topNode != this.root)
        {
            if(topNode == topNode.getParent().getRightChild()){
                topNode.getParent().setRightChild(topNode.getRightChild());
            }
            else{
                topNode.getParent().setLeftChild(topNode.getRightChild());
            }
            topNode.getRightChild().setParent(topNode.getParent());
        }
        topNode.setParent(topNode.getRightChild());
        topNode.setRightChild(topNode.getRightChild().getLeftChild());
        if(topNode.getRightChild() != null)
            topNode.getRightChild().setParent(topNode);
        topNode.getParent().setLeftChild(topNode);
        if(topNode.getParent().getParent() == topNode)
        {
            topNode.getParent().setParent(null);
            this.root = topNode.getParent();
        }
    }

    private void checkViolationRules (INode<T,V> topNode){
        if(topNode.getColor()== INode.RED && topNode.getParent().getColor() == INode.RED){
            topNode.getParent().getParent().setColor(!topNode.getParent().getParent().getColor());
            if( topNode.getParent() == topNode.getParent().getParent().getLeftChild() )
            {
                if(topNode == topNode.getParent().getLeftChild())
                {
                    topNode.getParent().setColor(!topNode.getParent().getColor());
                    rotateRight(topNode.getParent().getParent());
                }
                else
                {
                    topNode.setColor(!topNode.getColor());
                    rotateLeft(topNode.getParent());
                    rotateRight(topNode.getParent());
                }
            }
            else
            {
                if(topNode == topNode.getParent().getRightChild())
                {
                    topNode.getParent().setColor(!topNode.getParent().getColor());
                    rotateLeft(topNode.getParent().getParent());
                }
                else
                {
                    topNode.setColor(!topNode.getColor());
                    rotateRight(topNode.getParent());
                    rotateLeft(topNode.getParent());
                }
            }
        }
    }

    private void checkColorTopDown (INode<T,V> topNode){

        if(topNode.getRightChild() != null && topNode.getRightChild().getColor() == INode.RED && topNode.getLeftChild() != null && topNode.getLeftChild().getColor() == INode.RED
        && topNode.getColor() == INode.BLACK )   //check conditions for black parent with two red children
        {
            topNode.getRightChild().setColor(INode.BLACK);
            topNode.getLeftChild().setColor(INode.BLACK);
            if(topNode == this.root)
                return ;
            topNode.setColor(INode.RED);
            checkViolationRules(topNode);
        }
    }

    private INode<T,V> getSibling (INode<T,V> node){
        if(node == node.getParent().getLeftChild())
            return node.getParent().getRightChild();
        return node.getParent().getLeftChild();
    }

    public static void main(String[] args) {
        RBtree<Integer,Integer> sayed = new RBtree<>();
        sayed.insert(10,5);
        sayed.insert(9,5);
        sayed.insert(11,5);
        sayed.insert(12,5);
        sayed.delete(9);
        sayed.delete(10);
    }

}
