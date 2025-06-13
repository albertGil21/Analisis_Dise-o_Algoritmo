package paquete_principal;

public interface HashTable {
    void insert(String key, Cliente value);
    Cliente search(String key);
    long getLastOperationTime(); // Para medir el tiempo de la última operación
}