package com.nominaEmpresa.nominaEmpresa.Interfaz;

import com.nominaEmpresa.nominaEmpresa.factory.BonificacionBanda;
import com.nominaEmpresa.nominaEmpresa.factory.ValorNomina;
import com.nominaEmpresa.nominaEmpresa.model.*;
import com.nominaEmpresa.nominaEmpresa.services.ProcesadorJson;
import jakarta.xml.bind.JAXBException;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;

public class VentanaCalculoNomina extends JFrame implements ActionListener {
    private JButton botonCalNom, botonGuardar, botonVolver, botonCrearNuevo,botonEliminar, botonBuscar;
    private ProcesadorJson procesadorJson;
    private JTable table1;
    private DefaultTableModel modelo1;
    private CardLayout cardLayout;
    private JTextField busquedaJT;
    private JPanel contenedor;
    private Map<String, JComponent> camposDto = new HashMap<>();
    private  EntradaWrapper empleadoSeleccionado;
    private TableRowSorter<TableModel> sorter;

    public VentanaCalculoNomina() {
        procesadorJson = new ProcesadorJson();
        this.cardLayout = new CardLayout();
        this.contenedor = new JPanel(cardLayout);

        panelSeleccionCalculoNomina();

    }

    public void panelSeleccionCalculoNomina() {
        setTitle("Nomina Empleados");
        setSize(900, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelCalculoNomina = new JPanel();
        panelCalculoNomina.setLayout(new BorderLayout());

        botonCalNom = new JButton("Calcular Nomina");
        botonCalNom.addActionListener(this);

        botonCrearNuevo = new JButton("Crear nuevo");
        botonCrearNuevo.addActionListener(this);

        botonBuscar = new JButton("Buscar");
        botonBuscar.addActionListener(this);

        JPanel panelPrincipal1 = new JPanel(new BorderLayout());
        JPanel panelBotones1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones1.add(botonCalNom);
        panelBotones1.add(botonCrearNuevo);


        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel label1 = new JLabel("Ingrese valor");
        busquedaJT = new JTextField("");
        busquedaJT.setPreferredSize(new Dimension(120,20));
        panelBusqueda.add(label1);
        panelBusqueda.add(busquedaJT);
        panelBusqueda.add(botonBuscar);

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.X_AXIS));
        add(contenedor, BorderLayout.CENTER);


        String[] columnas1 = {"ID", "Nombre", "Apellido", "Telefono", "Direccion", "Banda", "Horas Trabajadas", "Dias Incapacidad", "Dias Vacaciones", "Valor Hora", "Modificar", "Eliminar"};
        modelo1 = new DefaultTableModel(columnas1, 0);
        table1 = new JTable(modelo1);
        table1.setShowGrid(true);
        table1.setGridColor(Color.BLUE);
        table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(table1);
        panelPrincipal1.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal1.add(panelBotones1, BorderLayout.SOUTH);
        panelPrincipal1.add(panelBusqueda, BorderLayout.NORTH);

        DocumentoEntrada documento = procesadorJson.leerDocumento();
        List<EntradaWrapper> empleados = documento.getListEmpleados();
        for (EntradaWrapper empleadoi : empleados) {
            DatosEmpleados datos = empleadoi.getDatosEmpleados();
            DatosNomina dNomina = empleadoi.getDatosNomina();
            modelo1.addRow(new Object[]{datos.getId(), datos.getNombre(), datos.getApellido(), datos.getTelefono(), datos.getDireccion().getResidencia(), datos.getBanda(), dNomina.getHorasT(), dNomina.getDiasI(), dNomina.getDiasV(), dNomina.getValHora(), ""});
        }
        sorter = new TableRowSorter<>(modelo1);
        table1.setRowSorter(sorter);

        ajustarAnchoColumnas(table1);
        contenedor.add(panelPrincipal1, "panel1");
        cardLayout.show(contenedor, "panel1");

        panelCalculoNomina.revalidate();
        panelCalculoNomina.repaint();
        setLocationRelativeTo(null);
        setVisible(true);


        TableColumn columnaAccionModificar = table1.getColumn("Modificar");
        RenderizadorVentana rendererEditar = new RenderizadorVentana("/iconos/edit.png");
        columnaAccionModificar.setCellRenderer(rendererEditar);

        // Poner el editor para que maneje el clic
        columnaAccionModificar.setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            JButton botonEditar = new JButton();
            {
                ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/edit.png"));
                Image imagen = icono.getImage().getScaledInstance(14, 14, Image.SCALE_SMOOTH);
                botonEditar.setIcon(new ImageIcon(imagen));
                botonEditar.setBorderPainted(false);
                botonEditar.setContentAreaFilled(false);
                botonEditar.setFocusPainted(false);

                botonEditar.addActionListener(evt -> {
                    int fila = table1.getEditingRow();
                    if(fila >= 0 && fila < empleados.size()){
                        EntradaWrapper empleadoSeleccionado = empleados.get(fila);
                        panelModificar(empleadoSeleccionado);
                        cardLayout.show(contenedor, "panel3");
                    }
                    fireEditingStopped(); // importante para finalizar edición
                });
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return botonEditar;
            }

           @Override
            public Object getCellEditorValue() {
                return ""; // valor que quedará en la celda después de editar
           }
        });

        TableColumn columnaAccionEliminar = table1.getColumn("Eliminar");
        TableCellRenderer rendererEliminar = new RenderizadorVentana("/iconos/eliminar.png");
        columnaAccionEliminar.setCellRenderer(rendererEliminar);

        // Poner el editor para que maneje el clic
        columnaAccionEliminar.setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            JButton botonEliminar = new JButton();
            {
                ImageIcon icono = new ImageIcon(getClass().getResource("/iconos/eliminar.png"));
                Image imagen = icono.getImage().getScaledInstance(14, 14, Image.SCALE_SMOOTH);
                botonEliminar.setIcon(new ImageIcon(imagen));
                botonEliminar.setBorderPainted(false);
                botonEliminar.setContentAreaFilled(false);
                botonEliminar.setFocusPainted(false);

                botonEliminar.addActionListener(evt -> {
                    int fila = table1.getEditingRow();
                    if(fila >= 0 && fila < empleados.size()){
                        EntradaWrapper empleadoSeleccionado = empleados.get(fila);
                        panelEliminar(empleadoSeleccionado);
                        cardLayout.show(contenedor, "panel5");
                    }
                    fireEditingStopped(); // importante para finalizar edición
                });
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return botonEliminar;
            }

            @Override
            public Object getCellEditorValue() {
                return ""; // valor que quedará en la celda después de editar
            }
        });
    }
    public JPanel panelFormulario(Map<String, JComponent> campos, EntradaWrapper empleado){
        try {
            JPanel panelFormulario = new JPanel();
            panelFormulario.setLayout(new BoxLayout(panelFormulario, BoxLayout.Y_AXIS));

            JTextField id = new JTextField();
            if(empleado != null && empleado.getDatosEmpleados()!= null){
                id.setText(empleado.getDatosEmpleados().getId());
            }
            campos.put("id", id);
            panelFormulario.add(crearFilaCampo("ID", id));

            JTextField nombre = new JTextField();
            if(empleado != null && empleado.getDatosEmpleados() != null){
                nombre.setText(empleado.getDatosEmpleados().getNombre());
            }
            campos.put("nombre", nombre);
            panelFormulario.add(crearFilaCampo("Nombre", nombre));

            JTextField apellido = new JTextField();
            if(empleado != null && empleado.getDatosEmpleados() != null){
                apellido.setText(empleado.getDatosEmpleados().getApellido());
            }
            campos.put("apellido", apellido);
            panelFormulario.add(crearFilaCampo("Apellidos", apellido));

            JTextField telefono = new JTextField();
            if(empleado != null && empleado.getDatosEmpleados() != null){
                telefono.setText(empleado.getDatosEmpleados().getTelefono());
            }
            campos.put("telefono", telefono);
            panelFormulario.add(crearFilaCampo("Telefono", telefono));

            JTextField correo = new JTextField();
            if(empleado != null && empleado.getDatosEmpleados() != null){
                correo.setText(empleado.getDatosEmpleados().getCorreo());
            }
            campos.put("correo", correo);
            panelFormulario.add(crearFilaCampo("Correo", correo));

            JTextField direccionRes = new JTextField();
            if(empleado != null && empleado.getDatosEmpleados() != null){
                direccionRes.setText(empleado.getDatosEmpleados().getDireccion().getResidencia());
            }
            campos.put("direccionRes", direccionRes);
            panelFormulario.add(crearFilaCampo("Direccion(residencia)", direccionRes));

            JTextField direccionBar = new JTextField();
            if(empleado != null && empleado.getDatosEmpleados() != null){
                direccionBar.setText(empleado.getDatosEmpleados().getDireccion().getBarrio());
            }
            campos.put("direccionBar", direccionBar);
            panelFormulario.add(crearFilaCampo("Direccion(barrio)", direccionBar));

            JTextField direccionCiu = new JTextField();
            if(empleado != null && empleado.getDatosEmpleados() != null){
                direccionCiu.setText(empleado.getDatosEmpleados().getDireccion().getCiudad());
            }
            campos.put("direccionCiu", direccionCiu);
            panelFormulario.add(crearFilaCampo("Direccion(ciudad)", direccionCiu));

            MaskFormatter mask = new MaskFormatter("##/##/####");
            JFormattedTextField fechaNacimientoField = new JFormattedTextField(mask);
            fechaNacimientoField.setColumns(10);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            if(empleado != null && empleado.getDatosEmpleados() != null) {
                if (empleado.getDatosEmpleados().getFechaDeNac() != null) {
                    String fechaTexto = empleado.getDatosEmpleados().getFechaDeNac().format(formatter);
                    fechaNacimientoField.setText(fechaTexto);
                }
            }
            campos.put("fechaNacimiento", fechaNacimientoField);
            panelFormulario.add(crearFilaCampo("F/N (dd/MM/yyyy):", fechaNacimientoField));

            JComboBox<Cargo> cargoCombo = new JComboBox<>(Cargo.values());
            if(empleado != null && empleado.getDatosEmpleados() != null) {
                if (empleado.getDatosEmpleados().getCargo() != null) {
                    cargoCombo.setSelectedItem(empleado.getDatosEmpleados().getCargo());
                }
            }
            campos.put("cargo", cargoCombo);
            panelFormulario.add(crearFilaCampo("Cargo:", cargoCombo));

            JTextField area = new JTextField();
            if(empleado != null && empleado.getDatosEmpleados() != null) {
                area.setText(empleado.getDatosEmpleados().getArea());
            }
            campos.put("area", area);
            panelFormulario.add(crearFilaCampo("Area", area));

            JComboBox<Banda> bandaCombo = new JComboBox<>(Banda.values());
            if(empleado != null && empleado.getDatosEmpleados() != null) {
                if (empleado.getDatosEmpleados().getBanda() != null) {
                    bandaCombo.setSelectedItem(empleado.getDatosEmpleados().getBanda());
                }
            }
            campos.put("banda", bandaCombo);
            panelFormulario.add(crearFilaCampo("Banda:", bandaCombo));

            DatosNomina nominaMod = null;
            if (empleado != null && empleado.getDatosNomina() != null) {
                nominaMod = empleado.getDatosNomina();
            }

            JTextField valorH = new JTextField();
            if(nominaMod != null) {
                valorH.setText(String.valueOf(nominaMod.getValHora()));
            }
            campos.put("valHora", valorH);
            panelFormulario.add(crearFilaCampo("Valor Trabajadas:", valorH));

            JTextField horasT = new JTextField();
            if(nominaMod != null){
                horasT.setText(String.valueOf(nominaMod.getHorasT()));
            }
            campos.put("horasT", horasT);
            panelFormulario.add(crearFilaCampo("Horas Trabajadas:", horasT));

            JTextField diasI = new JTextField();
            if(nominaMod != null){
                diasI.setText(String.valueOf(nominaMod.getDiasI()));
            }
            campos.put("diasI", diasI);
            panelFormulario.add(crearFilaCampo("Días Inasistidos:", diasI));

            JTextField diasV = new JTextField();
            if(nominaMod != null){
                diasV.setText(String.valueOf(nominaMod.getDiasV()));
            }
            campos.put("diasV", diasV);
            panelFormulario.add(crearFilaCampo("Días Vacaciones:", diasV));
            return panelFormulario;
        }catch (ParseException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public void panelModificar(EntradaWrapper empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
        setTitle("EDICION DE DATOS DEL EMPLEADO");
        setSize(900, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(contenedor, BorderLayout.CENTER);

        camposDto = new HashMap<>();
        JPanel panelCamposDto = panelFormulario(camposDto, empleadoSeleccionado);

        add(panelCamposDto);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(new JScrollPane(panelCamposDto), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botonGuardar = new JButton("Guardar");
        botonGuardar.addActionListener(this);
        botonVolver = new JButton("Cancelar");
        botonVolver.addActionListener(this);

        panelBotones.add(botonVolver);
        panelBotones.add(botonGuardar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        contenedor.add(panelPrincipal, "panel3");
        cardLayout.show(contenedor, "panel3");
        contenedor.revalidate();
        contenedor.repaint();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void panelEliminar(EntradaWrapper empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
        DatosEmpleados empleados = empleadoSeleccionado.getDatosEmpleados();

        setTitle("Eliminar empleado");
        setSize(990, 140);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelEliminarDatos = new JPanel();
        panelEliminarDatos.setLayout(new BoxLayout(panelEliminarDatos, BoxLayout.Y_AXIS));
        add(contenedor, BorderLayout.CENTER);

        panelEliminarDatos.add(crearFilaDto(empleados));
        JLabel label = new JLabel("Esta seguro que desea eliminar este empleado");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setAlignmentX(Component.TOP_ALIGNMENT);
        panelEliminarDatos.add(label);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(new JScrollPane(panelEliminarDatos), BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        botonEliminar = new JButton("Eliminar");
        botonEliminar.addActionListener(this);
        botonVolver = new JButton("Cancelar");
        botonVolver.addActionListener(this);

        panelBotones.add(botonVolver);
        panelBotones.add(botonEliminar);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        contenedor.add(panelPrincipal, "panel5");
        cardLayout.show(contenedor, "panel5");
        contenedor.revalidate();
        contenedor.repaint();

        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void panelCrearDto(EntradaWrapper empleadoNuevo){
        try{
            setTitle("EDICION DE DATOS DEL EMPLEADO");
            setSize(900, 200);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            if(empleadoNuevo == null) {
                empleadoNuevo = new EntradaWrapper();
            }

            camposDto = new HashMap<>();
            JPanel panelFormulario = panelFormulario(camposDto,empleadoNuevo);

            JPanel panelPrincipal = new JPanel(new BorderLayout());
            panelPrincipal.add(new JScrollPane(panelFormulario), BorderLayout.CENTER);

            JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
            botonGuardar = new JButton("Guardar");
            botonGuardar.addActionListener(this);
            botonVolver = new JButton("Cancelar");
            botonVolver.addActionListener(this);

            panelBotones.add(botonVolver);
            panelBotones.add(botonGuardar);

            panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

            contenedor.add(panelPrincipal, "panel4");
            cardLayout.show(contenedor, "panel4");
            contenedor.revalidate();
            contenedor.repaint();

            setLocationRelativeTo(null);
            setVisible(true);
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void panelCalculoNominaCalculada() throws JAXBException {
        botonCalNom.setVisible(false);

        JPanel panelCalculoNomina2 = new JPanel();
        setTitle("Calculo Nomina Empleados");
        setSize(900, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panelCalculoNomina2.setLayout(new BoxLayout(panelCalculoNomina2, BoxLayout.X_AXIS));

        contenedor.add(panelCalculoNomina2, "panel2");
        add(contenedor, BorderLayout.CENTER);

        //getContentPane().removeAll();

        String[] columnas = {"ID", "Nombre", "Apellido", "Telefono", "Direccion", "Valor Nomina", "Bonificacion Anual"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable table = new JTable(modelo);
        table.setShowGrid(true);
        table.setGridColor(Color.BLUE);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(table);
        panelCalculoNomina2.add(scrollPane);


        DocumentoEntrada documento = procesadorJson.leerDocumento();
        List<EntradaWrapper> empleados = documento.getListEmpleados();
        for (EntradaWrapper empleadoi : empleados) {
            DatosEmpleados datos = empleadoi.getDatosEmpleados();
            ValorNomina valorNomina = new ValorNomina(empleadoi);
            SalidaWrapper salida = new SalidaWrapper(empleadoi.getDatosEmpleados(), valorNomina);
            BonificacionBanda bonificacionBanda = new BonificacionBanda(salida);
            salida.setBonificacionBanda(bonificacionBanda);
            modelo.addRow(new Object[]{datos.getId(), datos.getNombre(), datos.getApellido(), datos.getTelefono(), datos.getDireccion().getResidencia(), salida.getValorNomina().valorNomina(), bonificacionBanda.getValorBanda()});
        }

        ajustarAnchoColumnas(table);
        cardLayout.show(contenedor, "panel2");
        panelCalculoNomina2.revalidate();
        panelCalculoNomina2.repaint();

        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void ajustarAnchoColumnas(JTable table) {
        TableColumnModel columnModel = table.getColumnModel();

        for (int col = 0; col < table.getColumnCount(); col++) {
            int anchoMaximo = 50; // Ancho mínimo

            // Medir el ancho del encabezado
            TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
            Component headerComp = headerRenderer.getTableCellRendererComponent(
                    table, table.getColumnModel().getColumn(col).getHeaderValue(), false, false, 0, col);
            anchoMaximo = headerComp.getPreferredSize().width;

            // Medir el ancho del contenido de cada celda
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, col);
                Component c = cellRenderer.getTableCellRendererComponent(
                        table, table.getValueAt(row, col), false, false, row, col);
                anchoMaximo = Math.max(anchoMaximo, c.getPreferredSize().width);
            }

            // Agregar margen extra
            columnModel.getColumn(col).setPreferredWidth(anchoMaximo + 10);
        }
    }

    public void actionPerformed(ActionEvent e) {
        try {
            DocumentoEntrada doc = procesadorJson.leerDocumento();
            if (e.getSource() == botonCalNom) {
                panelCalculoNominaCalculada();

            } else if (e.getSource() == botonGuardar) {
                List<EntradaWrapper> empleados = doc.getListEmpleados();
                if (empleados == null) {
                    empleados = new ArrayList<>();
                }

                if (empleadoSeleccionado != null) {
                    guardarDtoMod(camposDto, this.empleadoSeleccionado);
                    for (int i = 0; i < empleados.size(); i++) {
                        String idActual = empleados.get(i).getDatosEmpleados().getId();
                        String idSeleccionado = empleadoSeleccionado.getDatosEmpleados().getId();

                        if (idActual.equals(idSeleccionado)) {
                            empleados.set(i, empleadoSeleccionado);
                            break;
                        }
                    }
                } else {
                    EntradaWrapper empleadoNuevo = guardarDtoNuevo(camposDto, new EntradaWrapper());
                    empleados.add(empleadoNuevo);
                }
                doc.setListEmpleados(empleados);
                procesadorJson.escribirDocumento(doc);
                System.out.println("Guardado y documento actualizado correctamente.");
                panelSeleccionCalculoNomina();
            } else if (e.getSource() == botonVolver) {
                panelSeleccionCalculoNomina();
            } else if (e.getSource() == botonCrearNuevo) {
                EntradaWrapper empleadoNuevo = new EntradaWrapper();
                panelCrearDto(empleadoNuevo);
            } else if (e.getSource() == botonEliminar) {
                List<EntradaWrapper> empleados = doc.getListEmpleados();
                for (int i = 0; i < empleados.size(); i++) {
                    String idActual = empleados.get(i).getDatosEmpleados().getId();
                    String idSeleccionado = empleadoSeleccionado.getDatosEmpleados().getId();

                    if (idActual.equals(idSeleccionado)) {
                        empleados.remove(i);
                        break;
                    }
                }
                doc.setListEmpleados(empleados);
                procesadorJson.escribirDocumento(doc);
                JOptionPane.showMessageDialog(null, "Empleado Eliminado");
                panelSeleccionCalculoNomina();
            }else if(e.getSource() == botonBuscar) {
                String idBusqueda = busquedaJT.getText().trim();
                if (idBusqueda.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + idBusqueda));
                }
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private JPanel crearFilaCampo(String labelTexto, JComponent campo) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel etiqueta = new JLabel(labelTexto);
        etiqueta.setPreferredSize(new Dimension(120, 25));
        campo.setPreferredSize(new Dimension(150, 25));
        fila.add(etiqueta);
        fila.add(campo);

        return fila;
    }
    private JPanel crearFilaDto(DatosEmpleados empleados) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel label0 = new JLabel("ID ");
        JTextField jTextField0 = new JTextField(String.valueOf(empleados.getId()));
        jTextField0.setPreferredSize(new Dimension(150, 25));
        jTextField0.setEditable(false);
        JLabel label1 = new JLabel("Nombre ");
        JTextField jTextField = new JTextField(empleados.getNombre());
        jTextField.setPreferredSize(new Dimension(150, 25));
        JLabel label2 = new JLabel("Apellido ");
        JTextField jTextField2 = new JTextField(empleados.getApellido());
        jTextField2.setPreferredSize(new Dimension(150, 25));
        JLabel label3 = new JLabel("Correo ");
        JTextField jTextField3 = new JTextField(empleados.getCorreo());
        jTextField3.setPreferredSize(new Dimension(150, 25));
        JLabel label4 = new JLabel("Telefono ");
        JTextField jTextField4 = new JTextField(empleados.getTelefono());
        jTextField4.setPreferredSize(new Dimension(150, 25));
        fila.add(label0);fila.add(jTextField0);
        fila.add(label1);fila.add(jTextField);
        fila.add(label2);fila.add(jTextField2);
        fila.add(label3);fila.add(jTextField3);
        fila.add(label4);fila.add(jTextField4);
        return fila;
    }

    private String obtenerValorCampo(Map<String, JComponent> campos, String clave) {
        JComponent comp = campos.get(clave);
        if (comp == null) return "";

        if (comp instanceof JTextField) {
            return ((JTextField) comp).getText().trim();
        }

        if (comp instanceof JComboBox) {
            Object seleccionado = ((JComboBox<?>) comp).getSelectedItem();
            return seleccionado != null ? seleccionado.toString() : "";
        }
        return "";
    }
    public EntradaWrapper guardarDtoNuevo(Map<String, JComponent> camposDto, EntradaWrapper empleadoNuevo){
        try{
            empleadoNuevo = new EntradaWrapper();
            empleadoNuevo.setDatosEmpleados(new DatosEmpleados());
            empleadoNuevo.setDatosNomina(new DatosNomina());

            String id = obtenerValorCampo(camposDto,"id");
            String nombre = obtenerValorCampo(camposDto,"nombre");
            String apellido = obtenerValorCampo(camposDto,"apellido");
            String telefono = obtenerValorCampo(camposDto,"telefono");
            String correo = obtenerValorCampo(camposDto,"correo");
            String direccionRes  = obtenerValorCampo(camposDto,"direccionRes");
            String direccionBar  = obtenerValorCampo(camposDto,"direccionBar");
            String direccionCiu  = obtenerValorCampo(camposDto,"direccionCiu");

            String fechaText = obtenerValorCampo(camposDto, "fechaNacimiento");
            LocalDate fechaNacimiento = null;

            if (fechaText != null && !fechaText.isEmpty() && !fechaText.contains("_")) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                try {
                    fechaNacimiento = LocalDate.parse(fechaText, formatter);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use dd/MM/yyyy.", "Error de fecha", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            } else {
                JOptionPane.showMessageDialog(null, "La fecha está incompleta o tiene formato inválido. Use dd/MM/yyyy.", "Error de fecha", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            Cargo cargoSeleccionado = null;
            JComponent compCargo = camposDto.get("cargo");
            if (compCargo instanceof JComboBox) {
                cargoSeleccionado = (Cargo) ((JComboBox<?>) compCargo).getSelectedItem();
            }

            String area = obtenerValorCampo(camposDto,"area");

            Banda bandaSeleccionada = null;
            JComponent compBanda = camposDto.get("banda");
            if (compBanda instanceof JComboBox) {
                bandaSeleccionada = (Banda) ((JComboBox<?>) compBanda).getSelectedItem();
            }

            long valHora = 0;
            String valHora1 = obtenerValorCampo(camposDto,"valorH");
            if(!valHora1.isEmpty()) valHora = Long.parseLong(valHora1);

            int horasT = 0;
            String horasT1 = obtenerValorCampo(camposDto,"horasT");
            if(!horasT1.isEmpty()) horasT = Integer.parseInt(horasT1);

            int diasI = 0;
            String diasI1 = obtenerValorCampo(camposDto,"diasI");
                if(!diasI1.isEmpty()) diasI = Integer.parseInt(diasI1);


            int diasV = 0;
            String diasV1 = obtenerValorCampo(camposDto,"diasV");
            if(!diasV1.isEmpty()) diasV = Integer.parseInt(diasV1);

            //***********************************************************************************
            DatosNomina nomina = empleadoNuevo.getDatosNomina();
            DatosEmpleados empleado = empleadoNuevo.getDatosEmpleados();
            Direccion direccion1 = new Direccion();

            empleado.setId(id);
            empleado.setNombre(nombre);
            empleado.setApellido(apellido);
            empleado.setTelefono(telefono);
            empleado.setCorreo(correo);
            direccion1.setResidencia(direccionRes);
            direccion1.setBarrio(direccionBar);
            direccion1.setCiudad(direccionCiu);
            empleado.setDireccion(direccion1);
            empleado.setFechaDeNac(fechaNacimiento);
            empleado.setCargo(cargoSeleccionado);
            empleado.setArea(area);
            empleado.setBanda(bandaSeleccionada);

            nomina.setValHora(valHora);
            nomina.setHorasT(horasT);
            nomina.setDiasI(diasI);
            nomina.setDiasV(diasV);

            empleadoNuevo.setDatosEmpleados(empleado);
            empleadoNuevo.setDatosNomina(nomina);

            return empleadoNuevo;

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese solo números válidos.");
            return null;
        }
    }
    public EntradaWrapper guardarDtoMod(Map<String, JComponent> camposDto, EntradaWrapper empleadoSeleccionado){
        empleadoSeleccionado = this.empleadoSeleccionado;
        try{
            long valHora = 0;
            String valorH1 = obtenerValorCampo(camposDto,"valHora");
            if(!valorH1.isEmpty()) valHora = Long.parseLong(valorH1);

            int horasT = 0;
            String horasT1 = obtenerValorCampo(camposDto,"horasT");
            if(!horasT1.isEmpty()) horasT = Integer.parseInt(horasT1);

            int diasI = 0;
            String diasI1 = obtenerValorCampo(camposDto,"diasI");
            if(!diasI1.isEmpty()) diasI = Integer.parseInt(diasI1);


            int diasV = 0;
            String diasV1 = obtenerValorCampo(camposDto,"diasV");
            if(!diasV1.isEmpty()) diasV = Integer.parseInt(diasV1);

            //***********************************************************************************
            DatosNomina nomina = empleadoSeleccionado.getDatosNomina();

            nomina.setValHora(valHora);
            nomina.setHorasT(horasT);
            nomina.setDiasI(diasI);
            nomina.setDiasV(diasV);

            empleadoSeleccionado.setDatosNomina(nomina);

            return empleadoSeleccionado;

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese solo números válidos.");
            return null;
        }
    }
}


