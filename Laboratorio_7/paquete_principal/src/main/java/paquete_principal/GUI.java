package paquete_principal;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {
    private JTextField txtCodigo, txtNombres, txtApellidos, txtTelefono, txtCorreo, txtDireccion, txtCodigoPostal, txtBuscar;
    private JButton btnAgregar, btnBuscarLineal, btnBuscarEncadenamiento;
    private JTextArea txtAreaResultados, txtAreaTiempos;

    private LinearProbingHashTable linearProbingTable;
    private ChainingHashTable chainingTable;
    private List<Cliente> allClients; // Para mantener el mismo conjunto de datos

    public GUI() {
        setTitle("Comparación de Tablas Hash");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Usar un layout nulo para posicionamiento absoluto (no recomendado para proyectos grandes)

        // Inicializar tablas y lista de clientes
        int tableCapacity = 100; // Capacidad de la tabla hash (ajustar según sea necesario)
        linearProbingTable = new LinearProbingHashTable(tableCapacity);
        chainingTable = new ChainingHashTable(tableCapacity);
        allClients = new ArrayList<>();

        // Componentes de la Interfaz (ejemplo básico, necesitarás más campos y un diseño más estructurado)
        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(20, 20, 80, 25);
        add(lblCodigo);
        txtCodigo = new JTextField();
        txtCodigo.setBounds(100, 20, 150, 25);
        add(txtCodigo);

        JLabel lblNombres = new JLabel("Nombres:");
        lblNombres.setBounds(20, 50, 80, 25);
        add(lblNombres);
        txtNombres = new JTextField();
        txtNombres.setBounds(100, 50, 150, 25);
        add(txtNombres);

        JLabel lblApellidos = new JLabel("Apellidos:");
        lblApellidos.setBounds(20, 80, 80, 25);
        add(lblApellidos);
        txtApellidos = new JTextField();
        txtApellidos.setBounds(100, 80, 150, 25);
        add(txtApellidos);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(20, 110, 80, 25);
        add(lblTelefono);
        txtTelefono = new JTextField();
        txtTelefono.setBounds(100, 110, 150, 25);
        add(txtTelefono);

        JLabel lblCorreo = new JLabel("Correo:");
        lblCorreo.setBounds(20, 140, 80, 25);
        add(lblCorreo);
        txtCorreo = new JTextField();
        txtCorreo.setBounds(100, 140, 150, 25);
        add(txtCorreo);

        JLabel lblDireccion = new JLabel("Dirección:");
        lblDireccion.setBounds(20, 170, 80, 25);
        add(lblDireccion);
        txtDireccion = new JTextField();
        txtDireccion.setBounds(100, 170, 150, 25);
        add(txtDireccion);

        JLabel lblCodigoPostal = new JLabel("C. Postal:");
        lblCodigoPostal.setBounds(20, 200, 80, 25);
        add(lblCodigoPostal);
        txtCodigoPostal = new JTextField();
        txtCodigoPostal.setBounds(100, 200, 150, 25);
        add(txtCodigoPostal);

        btnAgregar = new JButton("Agregar Cliente");
        btnAgregar.setBounds(20, 240, 150, 25);
        add(btnAgregar);

        JLabel lblBuscar = new JLabel("Buscar por Nombres/Apellidos:");
        lblBuscar.setBounds(20, 280, 200, 25);
        add(lblBuscar);
        txtBuscar = new JTextField();
        txtBuscar.setBounds(220, 280, 150, 25);
        add(txtBuscar);

        btnBuscarLineal = new JButton("Buscar (Sondeo Lineal)");
        btnBuscarLineal.setBounds(20, 310, 180, 25);
        add(btnBuscarLineal);

        btnBuscarEncadenamiento = new JButton("Buscar (Encadenamiento)");
        btnBuscarEncadenamiento.setBounds(210, 310, 180, 25);
        add(btnBuscarEncadenamiento);

        txtAreaResultados = new JTextArea();
        txtAreaResultados.setEditable(false);
        JScrollPane scrollResultados = new JScrollPane(txtAreaResultados);
        scrollResultados.setBounds(20, 350, 750, 150);
        add(scrollResultados);

        txtAreaTiempos = new JTextArea();
        txtAreaTiempos.setEditable(false);
        JScrollPane scrollTiempos = new JScrollPane(txtAreaTiempos);
        scrollTiempos.setBounds(20, 510, 750, 100);
        add(scrollTiempos);


        // Acciones de los botones
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = txtCodigo.getText();
                String nombres = txtNombres.getText();
                String apellidos = txtApellidos.getText();
                String telefono = txtTelefono.getText();
                String correo = txtCorreo.getText();
                String direccion = txtDireccion.getText();
                String codigoPostal = txtCodigoPostal.getText();

                // Validación básica de campos
                if (codigo.isEmpty() || nombres.isEmpty() || apellidos.isEmpty()) {
                    JOptionPane.showMessageDialog(GUI.this, "Código, Nombres y Apellidos son obligatorios.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Cliente cliente = new Cliente(codigo, nombres, apellidos, telefono, correo, direccion, codigoPostal);
                allClients.add(cliente);

                // ¡MODIFICACIÓN CLAVE!
                // La clave para hashing al insertar debe ser la concatenación de nombres y apellidos,
                // convertida a minúsculas para consistencia con la búsqueda.
                String keyForHashing = (nombres + apellidos).toLowerCase();

                linearProbingTable.insert(keyForHashing, cliente);
                chainingTable.insert(keyForHashing, cliente);

                txtAreaResultados.append("Cliente agregado: " + cliente.toString() + "\n");
                clearInputFields();
            }
        });

        btnBuscarLineal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = txtBuscar.getText().trim();
                if (searchTerm.isEmpty()) {
                    txtAreaResultados.setText("Por favor, ingrese un nombre o apellido para buscar (Sondeo Lineal).");
                    txtAreaTiempos.append("Búsqueda Sondeo Lineal abortada: campo vacío.\n");
                    return;
                }
                
                // ¡MODIFICACIÓN CLAVE!
                // Convertir el término de búsqueda a minúsculas para que coincida con la lógica de comparación
                // en las tablas hash (que usan .toLowerCase().equals())
                String searchKey = searchTerm.toLowerCase();

                long startTime = System.nanoTime();
                Cliente foundCliente = linearProbingTable.search(searchKey); // Pasa la clave de búsqueda convertida
                long endTime = System.nanoTime();
                long duration = endTime - startTime;

                txtAreaResultados.setText("Búsqueda por Sondeo Lineal para '" + searchTerm + "':\n");
                if (foundCliente != null) {
                    txtAreaResultados.append("Encontrado: " + foundCliente.toString() + "\n");
                } else {
                    txtAreaResultados.append("No encontrado.\n");
                }
                txtAreaTiempos.append("Sondeo Lineal - Tiempo de búsqueda: " + duration + " nanosegundos\n");
            }
        });

        btnBuscarEncadenamiento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = txtBuscar.getText().trim();
                if (searchTerm.isEmpty()) {
                    txtAreaResultados.setText("Por favor, ingrese un nombre o apellido para buscar (Encadenamiento).");
                    txtAreaTiempos.append("Búsqueda Encadenamiento abortada: campo vacío.\n");
                    return;
                }

                // ¡MODIFICACIÓN CLAVE!
                // Convertir el término de búsqueda a minúsculas
                String searchKey = searchTerm.toLowerCase();

                long startTime = System.nanoTime();
                Cliente foundCliente = chainingTable.search(searchKey); // Pasa la clave de búsqueda convertida
                long endTime = System.nanoTime();
                long duration = endTime - startTime;

                txtAreaResultados.setText("Búsqueda por Encadenamiento para '" + searchTerm + "':\n");
                if (foundCliente != null) {
                    txtAreaResultados.append("Encontrado: " + foundCliente.toString() + "\n");
                } else {
                    txtAreaResultados.append("No encontrado.\n");
                }
                txtAreaTiempos.append("Encadenamiento - Tiempo de búsqueda: " + duration + " nanosegundos\n");
            }
        });
    }

    private void clearInputFields() {
        txtCodigo.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtCodigoPostal.setText("");
    }

    public void compareAsymptoticNotation() {
        txtAreaTiempos.append("\n--- Comparación de Notación Asintótica ---\n");
        txtAreaTiempos.append("Inserción y Búsqueda (Caso Promedio):\n");
        txtAreaTiempos.append("  - Sondeo Lineal: O(1) ideal, pero O(N) en este diseño de búsqueda.\n"); // Ajustado
        txtAreaTiempos.append("  - Encadenamiento (con BST): O(log k) ideal, pero O(N*log k) en este diseño de búsqueda.\n"); // Ajustado
        txtAreaTiempos.append("Inserción y Búsqueda (Peor Caso):\n");
        txtAreaTiempos.append("  - Sondeo Lineal: O(n) (cuando la tabla está casi llena o hay muchas colisiones consecutivas)\n");
        txtAreaTiempos.append("  - Encadenamiento (con BST): O(N*k) (si el BST degenera a una lista enlazada y se busca en todos los buckets)\n");
        txtAreaTiempos.append("Nota: 'n' es el número total de elementos, 'k' es el número de elementos en el BST de una posición.\n");
        txtAreaTiempos.append("Debido a la forma en que se realiza la búsqueda por nombre/apellido (que no es la clave directa de hash), el rendimiento real puede degradarse a O(N).\n"); // Añadido
    }
}