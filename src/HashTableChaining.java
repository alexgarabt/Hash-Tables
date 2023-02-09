import org.jetbrains.annotations.NotNull;

public class HashTableChaining<K,V> {
    // Chaining table that stores pairs key-value.
    //Intern class that represents a node of a simple linked list.
    private class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private int m;             // Size of the table.
    private int n;             // Number of elements.
    private double maxL;       // Maximum Load factor.
    private Node<K, V>[] table; // Hash Table (array of pairs key-value).

    // Constructor with default values.
    public HashTableChaining() {
        this(16, 2.5);
    }

    // Constructor: m0 ‐ initial size.
    // maxL ‐ max load factor
    public HashTableChaining(int m0, double maxL) {
        this.maxL = maxL;
        this.m = m0;
        table = new Node[m];
        for (int i = 0; i < m; i++) table[i] = null;
        this.n = 0;
    }

    // Returns the index corresponding to that key using the hash.
    protected int index(@NotNull K key) {
        return Math.abs(key.hashCode()) % m;
    }

    /**
     * Returns the value associated with the provide key.
     * @param key associated to the value.
     * @return V value, if there is a Node with that key.
     *         else returns null.
     */
    public V get(K key) {
        int i = index(key); //Get the index of the key.

        // Search in the ith list.
        Node<K,V> tmpNode = table[i];
        while(tmpNode != null && !tmpNode.key.equals(key)) tmpNode = tmpNode.next;
        return (tmpNode == null) ? null : tmpNode.value;
    }

    /**
     * Creates a node with the given parameters and insert it in the table.
     * @param key
     * @param value
     */
    public void insert(K key, V value) {
        n++;                                // Increase n.
        if((1.0*n)/m > maxL) restructure(); //Check the Load factor.
        int i = index(key);                 //Get the index corresponding to that key.

        //Insert the Node at the beginning of the ith list.
        table[i] = new Node(key, value, table[i]);
    }

    /**
     * Deletes form the table, the pair (k,v) with that key.
     * @param key of the element to pair to delete.
     * @return
     */
    public boolean delete(K key) {
        int i = index(key); //Get the corresponding index to that key.

        // Search the element in the ith list in the table.
        Node<K,V> lastNode = null;
        Node<K,V> newNode = table[i];
        while(newNode != null && !newNode.key.equals(key)) {
            lastNode = newNode;
            newNode = newNode.next;
        }

        if(newNode == null) { return false; }
        //Check special(the first has been deleted )
        if(lastNode == null) table[i] = newNode.next; else lastNode.next = newNode.next;

        n=n-1; //Decrease the number of elements
        return true;
    }

    /**
     * It doubles the size of the table.
     */
    protected void restructure() {
        Node<K, V>[] tmpTable = table; //Save the last table.
        n = 0;m = 2 * m;               // Double the size of the last table.
        table = new Node[m];           //Create a new one.
        //Insert the saved elements in the new table.
        for (int i = 0; i < m; i++) table[i] = null;
        // Insert the saved elements.
        for (int i = 0; i < tmpTable.length; i++) {
            Node<K, V> node = tmpTable[i];
            while (node != null) {
                insert(node.key, node.value);
                node = node.next;
            }
        }
    }
    
}

