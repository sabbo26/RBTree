package eg.edu.alexu.csd.filestructure.redblacktree;

import javax.management.RuntimeErrorException;
import java.util.*;

public class TreeMap<T extends Comparable<T>,V> implements ITreeMap<T,V> {

    public IRedBlackTree<T,V> RBtree ;
    private int size = 0;
    private boolean isfound = false;

    public TreeMap()
    {
        RBtree = new RBtree<T,V>();
    }

    public TreeMap(IRedBlackTree<T, V> RBtree) {
        this.RBtree = RBtree;
    }


    @Override
    public Map.Entry<T, V> ceilingEntry(T key) {
        if(RBtree.getRoot()==null || key==null)
            throw new RuntimeErrorException(new Error("Key not found"));
        INode<T,V> temp = RBtree.getRoot();
        while(true){
            if(temp == null)
                return null ;
            if (key.compareTo(temp.getKey()) == 0 )
                break ;
            else if (key.compareTo(temp.getKey()) > 0 ){
                temp = temp.getRightChild();
            }
            else
                temp = temp.getLeftChild();
        }
        if (temp.getRightChild() != null){
            temp = temp.getRightChild();
            while (temp.getLeftChild() != null){
                temp = temp.getLeftChild() ;
            }
        }
        return Map.entry(temp.getKey(),temp.getValue());
    }

    @Override
    public T ceilingKey(T key) {
        if(RBtree.getRoot()==null || key==null)
            throw new RuntimeErrorException(new Error("Key not found"));
        INode<T,V> temp = RBtree.getRoot();
        while(true){
            if(temp == null)
                return null ;
            if (key.compareTo(temp.getKey()) == 0 )
                break ;
            else if (key.compareTo(temp.getKey()) > 0 ){
                temp = temp.getRightChild();
            }
            else
                temp = temp.getLeftChild();
        }
        if (temp.getRightChild() != null ){
            temp = temp.getRightChild();
            while (temp.getLeftChild() != null){
                temp = temp.getLeftChild() ;
            }
            return temp.getKey() ;
        }
        return temp.getKey();
    }

    @Override
    public void clear()
    {
        RBtree.clear();
        size=0;
    }

    @Override
    public boolean containsKey(T key) {

        return RBtree.contains(key);
    }

    @Override
    public boolean containsValue(V value) {
        if(value == null)
            throw new RuntimeErrorException(new Error("Value not found"));
        boolean flag=false;
        PreOrder(RBtree.getRoot(),value);
        if(isfound)
        {
            flag = true;
            isfound = false;
        }
        return flag;
    }

    @Override
    public Set<Map.Entry<T, V>> entrySet() {
        if(RBtree.getRoot().isNull())
            return null;
        Set<Map.Entry<T,V>> s = new LinkedHashSet<>();
        InOrder1(RBtree.getRoot(),s);
        return s;
    }

    @Override
    public Map.Entry<T, V> firstEntry() {
        INode<T,V> current = RBtree.getRoot();
        if(current==null)
            return null;

        while (current.getLeftChild()!=null)
            current = current.getLeftChild();
        return new AbstractMap.SimpleEntry<T,V>(current.getKey(),current.getValue());
    }

    @Override
    public T firstKey() {
        INode<T,V> current = RBtree.getRoot();
        if(current==null)
            return null;

        while (current.getLeftChild()!=null)
            current = current.getLeftChild();
        return current.getKey();
    }

    @Override
    public Map.Entry<T, V> floorEntry(T key) {
        if(RBtree.getRoot()==null || key==null)
            throw new RuntimeErrorException(new Error("Key not found"));
        INode<T,V> temp = RBtree.getRoot();
        while(true){
            if(temp == null)
                return null ;
            if (key.compareTo(temp.getKey()) == 0 )
                break ;
            else if (key.compareTo(temp.getKey()) > 0 ){
                temp = temp.getRightChild();
            }
            else
                temp = temp.getLeftChild();
        }
        if (temp.getLeftChild() != null){
            temp = temp.getLeftChild();
            while (temp.getRightChild() != null){
                temp = temp.getRightChild() ;
            }
        }
        return Map.entry(temp.getKey(),temp.getValue());
    }

    @Override
    public T floorKey(T key) {
        if(RBtree.getRoot()==null || key==null)
            throw new RuntimeErrorException(new Error("Key not found"));
        INode<T,V> temp = RBtree.getRoot();
        while(true){
            if(temp == null)
                return null ;
            if (key.compareTo(temp.getKey()) == 0 )
                break ;
            else if (key.compareTo(temp.getKey()) > 0 ){
                temp = temp.getRightChild();
            }
            else
                temp = temp.getLeftChild();
        }
        if (temp.getLeftChild() != null ){
            temp = temp.getLeftChild();
            while (temp.getRightChild() != null){
                temp = temp.getRightChild() ;
            }
            return temp.getKey() ;
        }
        return temp.getKey();
    }

    @Override
    public V get(T key) {
        return RBtree.search(key);
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey) {
        if (RBtree.getRoot()==null || toKey == null)
            return null;
        ArrayList<Map.Entry<T, V>> arr = new ArrayList<>();
        InOrder2(RBtree.getRoot(),toKey,arr,false);
        if(arr.size() == 0)
            return null;
        else
            return arr;
    }

    @Override
    public ArrayList<Map.Entry<T, V>> headMap(T toKey, boolean inclusive) {
        if (RBtree.getRoot()==null || toKey == null)
            return null;
        ArrayList<Map.Entry<T, V>> arr = new ArrayList<>();
        InOrder2(RBtree.getRoot(),toKey,arr,inclusive);
        if(arr.size() == 0)
            return null;
        else
            return arr;
    }

    @Override
    public Set<T> keySet() {
        if (RBtree.getRoot()==null)
            return null;
        Set<T> s = new LinkedHashSet<>();
        InOrder3(RBtree.getRoot(),s);
        return s;
    }

    @Override
    public Map.Entry<T, V> lastEntry() {
        INode<T,V> current = RBtree.getRoot();
        if(current==null)
            return null;

        while (current.getRightChild()!=null)
            current = current.getRightChild();
        return new AbstractMap.SimpleEntry<T,V>(current.getKey(),current.getValue());
    }

    @Override
    public T lastKey() {
        INode<T,V> current = RBtree.getRoot();
        if(current==null)
            return null;

        while (current.getRightChild()!=null)
            current = current.getRightChild();
        return current.getKey();
    }

    @Override
    public Map.Entry<T, V> pollFirstEntry() {
        INode<T,V> current = RBtree.getRoot();
        if(current==null)
            return null;

        while (current.getLeftChild()!=null)
            current = current.getLeftChild();

        RBtree.delete(current.getKey());
        size--;
        return new AbstractMap.SimpleEntry<T,V>(current.getKey(),current.getValue());
    }

    @Override
    public Map.Entry<T, V> pollLastEntry() {
        INode<T,V> current = RBtree.getRoot();
        if(current==null)
            return null;

        while (current.getRightChild()!=null)
            current = current.getRightChild();

        RBtree.delete(current.getKey());
        size--;
        return new AbstractMap.SimpleEntry<T,V>(current.getKey(),current.getValue());
    }

    @Override
    public void put(T key, V value) {
        if(!RBtree.contains(key))
            size++;
        RBtree.insert(key,value);
    }

    @Override
    public void putAll(Map<T, V> map) {
        if(map == null)
            throw new RuntimeErrorException(new Error("map is null"));
        for(Map.Entry<T,V> entry : map.entrySet())
        {
            RBtree.insert(entry.getKey(),entry.getValue());
            size++;
        }
    }

    @Override
    public boolean remove(T key) {
        size--;
        return RBtree.delete(key);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Collection<V> values() {
        if (RBtree.getRoot().isNull())
            return null;
        Set<V> s = new LinkedHashSet<>();
        InOrder4(RBtree.getRoot(),s);
        return s;
    }

    public void PreOrder(INode<T,V> root,V value)
    {
        if(root != null)
        {
            if(root.getValue().toString().equals( value.toString()) && !isfound)
                isfound = true;
            PreOrder(root.getLeftChild(),value);
            PreOrder(root.getRightChild(),value);
        }
    }
    public void InOrder1 (INode<T,V> root ,Set<Map.Entry<T, V>> s)  // to entrySet Method
    {
        if(root != null)
        {
            InOrder1(root.getLeftChild(),s);
            s.add(new AbstractMap.SimpleEntry<T,V>(root.getKey(),root.getValue()));
            InOrder1(root.getRightChild(),s);
        }
    }

    public void InOrder2 (INode<T,V> root ,T toKey ,ArrayList<Map.Entry<T, V>> arr,boolean inclusive )  // to headMap Method
    {
        if(root != null)
        {
            InOrder2(root.getLeftChild(),toKey,arr,inclusive);
            if(inclusive)
            {
                if (root.getKey().compareTo(toKey) < 0 || root.getKey().compareTo(toKey) == 0)
                    arr.add(new AbstractMap.SimpleEntry<T, V>(root.getKey(), root.getValue()));
            }
            else
            {
                if (root.getKey().compareTo(toKey) < 0)
                    arr.add(new AbstractMap.SimpleEntry<T, V>(root.getKey(), root.getValue()));
            }
            InOrder2(root.getRightChild(),toKey,arr,inclusive);
        }
    }
    public void InOrder3 (INode<T,V> root ,Set<T> s)  // to KeySet Method
    {
        if(root != null)
        {
            InOrder3(root.getLeftChild(),s);
            s.add(root.getKey());
            InOrder3(root.getRightChild(),s);
        }
    }
    public void InOrder4 (INode<T,V> root ,Set<V> s)  // to values Method
    {
        if(root != null)
        {
            InOrder4(root.getLeftChild(),s);
            s.add(root.getValue());
            InOrder4(root.getRightChild(),s);
        }
    }



}