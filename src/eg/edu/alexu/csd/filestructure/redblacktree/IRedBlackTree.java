package eg.edu.alexu.csd.filestructure.redblacktree;

public interface IRedBlackTree<T extends Comparable<T>, V> {

    /**
     * return the root of the given Red black tree.
     * @return root.
     */
    INode<T, V> getRoot();

    /**
     * return whether the given tree isEmpty or not.
     * @return boolean represent the state of the tree.
     */
    boolean isEmpty();

    /**
     * Clear all keys in the given tree.
     */
    void clear();

    /**
     * return the value associated with the given key or null if no value is found.
     * @param key to search.
     * @return value associated with this key.
     */
    V search(T key);

    /**
     * return true if the tree contains the given key and false otherwise.
     * @param key to search.
     * @return found key in tree or not.
     */
    boolean contains(T key);

    /**
     * Insert the given key in the tree while maintaining the red black tree properties.
     * If the key is already present in the tree, update its value.
     * @param key to be inserted
     * @param value the associated value with the given key
     */
    void insert(T key, V value);

    /**
     * Delete the node associated with the given key.
     * Return true in case of success and false otherwise.
     * @param key to be deleted.
     * @return true in case of success and false otherwise.
     */
    boolean delete(T key);




}
