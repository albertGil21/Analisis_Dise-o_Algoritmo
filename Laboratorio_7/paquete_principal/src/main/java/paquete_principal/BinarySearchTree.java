package paquete_principal;

public class BinarySearchTree {
    class Node {
        Cliente cliente;
        Node left, right;

        public Node(Cliente cliente) {
            this.cliente = cliente;
            left = right = null;
        }
    }

    Node root;

    public BinarySearchTree() {
        root = null;
    }

    // Inserta un cliente en el árbol, ordenado por código (asumido único)
    public void insert(Cliente cliente) {
        root = insertRec(root, cliente);
    }

    private Node insertRec(Node root, Cliente cliente) {
        if (root == null) {
            return new Node(cliente);
        }

        // Si el código ya existe, no se inserta (se considera un duplicado).
        // Puedes agregar una lógica para actualizar si lo prefieres.
        if (root.cliente.getCodigo().equals(cliente.getCodigo())) {
            return root;
        }

        int comparison = cliente.getCodigo().compareTo(root.cliente.getCodigo());
        if (comparison < 0) {
            root.left = insertRec(root.left, cliente);
        } else { // comparison > 0 (ir a la derecha)
            root.right = insertRec(root.right, cliente);
        }
        return root;
    }

    // Busca un cliente por nombre o apellido (recorrido del árbol)
    // 'key' es el searchTerm del usuario (ya en minúsculas)
    public Cliente search(String key) {
        return searchRec(root, key);
    }

    private Cliente searchRec(Node node, String key) {
        if (node == null) {
            return null;
        }

        // Comprueba si el cliente en el nodo actual coincide con la clave de búsqueda
        // Comparación insensible a mayúsculas/minúsculas y usando .equals() porque 'key' ya está en minúsculas
        if (node.cliente.getNombres().toLowerCase().equals(key) ||
            node.cliente.getApellidos().toLowerCase().equals(key)) {
            return node.cliente;
        }

        // Debido a que la clave de búsqueda (nombre/apellido) no es la clave de ordenación (código),
        // debemos buscar en ambos subárboles.
        Cliente foundInLeft = searchRec(node.left, key);
        if (foundInLeft != null) {
            return foundInLeft;
        }

        Cliente foundInRight = searchRec(node.right, key);
        return foundInRight;
    }
}