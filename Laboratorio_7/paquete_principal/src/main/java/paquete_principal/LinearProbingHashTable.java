package paquete_principal;

public class LinearProbingHashTable implements HashTable {
    private Cliente[] table;
    private int capacity;
    private int size; // Current number of elements
    private long lastOperationTime;
    // Marca para elementos eliminados, útil si implementaras la eliminación,
    // pero por ahora nos ayuda a "saltar" espacios lógicamente vacíos
    private final Cliente DELETED = new Cliente("DELETED_CODE", "DELETED", "DELETED", null, null, null, null);

    public LinearProbingHashTable(int capacity) {
        this.capacity = capacity;
        this.table = new Cliente[capacity];
        this.size = 0; // Initialize size
    }

    // Función Hash: Toma la 'key' (que será la clave de hashing o el searchTerm)
    private int hash(String key) {
        if (key == null || key.isEmpty()) {
            return 0; // Manejo básico para claves nulas o vacías
        }
        return Math.abs(key.hashCode() % capacity);
    }

    @Override
    public void insert(String keyForHashing, Cliente value) {
        long startTime = System.nanoTime();
        int index = hash(keyForHashing); // Usa la clave completa (nombres + apellidos) para el hash inicial

        // En sondeo lineal, buscamos la próxima posición disponible.
        // Manejamos el caso de que la tabla esté llena o muy densa.
        int startIndex = index;
        int attempts = 0;
        do {
            if (table[index] == null || table[index] == DELETED) {
                // Posición vacía o marcada como eliminada, insertamos aquí
                table[index] = value;
                size++;
                lastOperationTime = System.nanoTime() - startTime;
                return;
            }
            // Si el cliente ya existe (ej. mismo código), no lo reinsertamos.
            // Esto es importante para evitar bucles infinitos si intentamos insertar el mismo elemento
            // y la tabla está llena.
            if (table[index].getCodigo().equals(value.getCodigo())) {
                lastOperationTime = System.nanoTime() - startTime;
                return; // Cliente ya existe, no se inserta
            }

            index = (index + 1) % capacity; // Sondeo lineal
            attempts++;
            // Evita bucles infinitos si la tabla está completamente llena o si hemos revisado todas las posiciones.
            if (attempts >= capacity) {
                System.err.println("Advertencia: Tabla Lineal llena o muy densa, no se pudo insertar: " + value.getNombres());
                lastOperationTime = System.nanoTime() - startTime;
                return;
            }
        } while (index != startIndex || attempts == 0); // Continúa hasta dar la vuelta completa o encontrar espacio

        // Si el bucle termina sin encontrar espacio, la tabla está llena.
        System.err.println("Error: Tabla Lineal llena, no se pudo insertar: " + value.getNombres());
        lastOperationTime = System.nanoTime() - startTime;
    }

    @Override
    public Cliente search(String key) { // 'key' aquí es el searchTerm del usuario (ej. "Juan" o "Perez", ya en minúsculas)
        long startTime = System.nanoTime();
        
        // ¡CAMBIO CLAVE AQUÍ!
        // Como la clave de hash en inserción ("juanperez") no es la misma que la clave de búsqueda ("juan"),
        // NO PODEMOS CONFIAR EN EL HASH INICIAL para encontrar la cadena de sondeo correcta.
        // Por lo tanto, debemos iterar por TODA la tabla y verificar cada cliente.
        // Esto hace que la búsqueda en el peor caso sea O(N), no O(1) como se esperaría idealmente para Hash.
        for (int i = 0; i < capacity; i++) {
            if (table[i] == null) {
                // Si encontramos un null, no hay más elementos en esta dirección de sondeo
                // si la tabla estuviera perfectamente llena no encontraría null.
                // Sin embargo, como estamos haciendo un recorrido completo, no saltamos nada.
                continue; // Saltar posiciones null, buscar en el resto
            }

            // Ignorar celdas marcadas como DELETED (si implementaras delete)
            if (table[i] != DELETED) {
                // Comparamos el `key` de búsqueda (ya en minúsculas)
                // con los nombres o apellidos del `Cliente` almacenado (convertidos a minúsculas para comparación).
                if (table[i].getNombres().toLowerCase().equals(key) ||
                    table[i].getApellidos().toLowerCase().equals(key)) {
                    lastOperationTime = System.nanoTime() - startTime;
                    return table[i]; // Cliente encontrado
                }
            }
        }
        lastOperationTime = System.nanoTime() - startTime;
        return null; // Cliente no encontrado
    }

    @Override
    public long getLastOperationTime() {
        return lastOperationTime;
    }

    public int getSize() {
        return size;
    }
}