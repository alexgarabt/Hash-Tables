import org.jetbrains.annotations.NotNull;

/**
 * This class is a implementation of hash table using the method of open addressing to resolve
 * the problem of hash functions that arent perfect spread.
 *
 * The collision problem is solved by establishing a
 * sequence of positions (scan path) in which you can
 * find an element in the table.
 *
 * Using consecutive probing with double hashing and lazy delete strategy.
 *
 * Each table cell can be in 3 different states:
 * Busy: Contains an element, cannot be inserted into it.
 * Empty: Can be inserted into it, stops scanning.
 * Deleted: It can be inserted into it, it does not stop the exploration.
 *
 * Stores Nodes of pair key-values.
 * @param <K> Type of the keys objects.
 * @param <V> Type of the values objects.
 */
public class HashTableOpenAddressing<K,V> {

    /**
     * Intern class that represents a pair key-value.
     * @param <K> Key object type.
     * @param <V> Value object type.
     */
    private class Pair<K, V> {
        K key;
        V value;

        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private int m;              //Size of the table.
    private int n;              //Number of elements.
    private double maxL;        // Maximum load factor.
    private Pair<K, V>[] table; //Table of pairs key-value.
    //If a pair exists but the key is null it means that lazy delete strategy has been used.

    /**
     * Constructor with default values.
     * m0 = initial size (power of two).
     * maxL = maximum load factor  (maxL < 1)
     */
    public HashTableOpenAddressing() {
        this(16, 0.6);
    }

    /**
     * Constructor
     *
     * @param m0   initial size (power of two).
     * @param maxL maximum load factor  (maxL < 1)
     */
    public HashTableOpenAddressing(int m0, double maxL) {
        this.maxL = maxL;
        this.m = m0;
        table = new Pair[m];
        for (int i = 0; i < m; i++) table[i] = null;
        this.n = 0;
    }

    /**
     * Returns the corresponding index for the provide key.
     * Time Complexity: O(1)
     * @param key
     * @return Corresponding index.
     */
    protected int index(@NotNull K key) {
        return Math.abs(key.hashCode()) % m;
    }

    /**
     * Calculate the scan jump.
     * Time Complexity: O(1)
     * @param c
     * @return The scan Jump.
     */
    protected int jump(@NotNull K c) {
        int s = Math.abs(c.hashCode()) / m;
        return (s % 2 == 0) ? s + 1 : s;
    }

    /**
     * It doubles the size of the table.
     * Time Complexity O(n), but garantices (n) insertions in O(1).
     */
    protected void restructure() {
        Pair<K,V>[] tmp = table; // Save the last table.
        n = 0; m = 2*m;          // Double size.
        table = new Pair[m];      // Create a new one.
        for(int i = 0; i < m; i++) table[i] = null;
        // Recorremos la tabla anterior insertando elementos
        for(int i = 0; i < tmp.length; i++) {
            Pair<K,V> pair = tmp[i];
            if(pair != null && pair.key != null) {
                insert(pair.key, pair.value);
            }
        }
    }

    /**
     * Returns the value associated with the provide key.
     * Time Complexity: O(1)
     * @param key associated to the value.
     * @return V value, if there is a Node with that key.
     *         else returns null.
     */
    public V get(K key) {

        int i = index(key); // Get the corresponding index.
        int d = jump(key);  // Calculate the jump.

        //Explore the table until the position is null or found
        // a null key.(lazy delete)

        while(table[i] != null &&
                (table[i].key == null ||
                        !table[i].key.equals(key))) {
            i = (i+d) % m;
        }
        return (table[i] == null) ? null : table[i].value;
    }

    /**
     * Insert the pair k-v in the table, in the corresponding position.
     * Time Complexity: O(1)
     * @param key
     * @param value
     */
    public void insert(K key, V value) {

        n++; if(1.0*n/m > maxL) restructure(); // Increase n and check the load factor.
        int i = index(key);                    //Get the index.
        int d = jump(key);                     //Calculate the jump
        //Explore the table until the position is null or found
        // A null pair finish the scan, but one null key no.(lazy delete)
        while(table[i] != null && table[i].key != null) i = (i+d) % m;
        // Insertar clave en posición
        if(table[i] == null) {
            table[i] = new Pair(key,value);
        } else { //Lazy delete, recycle the pair.
            table[i].key = key; table[i].value = value;
        }
    }

    /**
     * Deletes form the table, the pair (k,v) with that key.
     * Equivalent to a successful search.
     * Use strategy of lazy delete to avoid errors.
     * Time Complexity: O(1)
     * @param key of the element to pair to delete.
     * @return True, if the pair k-v with the provide key was deleted successfully.
     *         False, else.
     */
    public boolean remove(K key) {

        int i = index(key); // Get the corresponding index.
        int d = jump(key);  // Calculate the jump.

        //Explore the table until the position is null or found
        // A null pair finish the scan, but one null key no.(lazy delete)

        while(table[i] != null &&
                (table[i].key == null || !table[i].key.equals(key))) {
            i = (i+d) % m;
        }
        if(table[i] == null) { return false; }
        table[i].key = null; // Lazy delete.
        n=n-1;
        return true;
    }
}
