package paquete_principal;

public class ChainingHashTable implements HashTable {
    private BinarySearchTree[] table;
    private int capacity;
    private int size;
    private long lastOperationTime;

    public ChainingHashTable(int capacity) {
        this.capacity = capacity;
        this.table = new BinarySearchTree[capacity];
        for (int i = 0; i < capacity; i++) {
            table[i] = new BinarySearchTree(); // Inicializa cada posición con un BST vacío
        }
        this.size = 0;
    }

    // Función Hash: Toma la clave que será usada para dispersar el elemento
    private int hash(String key) {
        if (key == null || key.isEmpty()) {
            return 0; // Manejo básico para claves nulas o vacías
        }
        return Math.abs(key.hashCode() % capacity);
    }

    @Override
    public void insert(String keyForHashing, Cliente value) {
        long startTime = System.nanoTime();
        int index = hash(keyForHashing); // Usa la clave generada para el hashing
        table[index].insert(value); // Delega la inserción al BST
        size++;
        lastOperationTime = System.nanoTime() - startTime;
    }

    @Override
    public Cliente search(String key) { // 'key' es el searchTerm del usuario (ej. "Juan" o "Perez", ya en minúsculas)
        long startTime = System.nanoTime();
        
        // ¡CAMBIO CLAVE AQUÍ!
        // Similar al sondeo lineal, si el hash(searchTerm) no lleva al bucket correcto
        // (porque se usó una clave diferente en la inserción), debemos buscar en todos los buckets.
        // Esto hace que la búsqueda en el peor caso sea O(N * k) donde k es el tamaño del BST en el bucket.
        for (int i = 0; i < capacity; i++) {
            Cliente found = table[i].search(key); // Delega la búsqueda al BST en cada bucket
            if (found != null) {
                lastOperationTime = System.nanoTime() - startTime;
                return found;
            }
        }
        
        lastOperationTime = System.nanoTime() - startTime;
        return null; // Cliente no encontrado en ningún bucket
    }

    @Override
    public long getLastOperationTime() {
        return lastOperationTime;
    }

    public int getSize() {
        return size;
    }
}