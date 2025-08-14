package com.nominaEmpresa.nominaEmpresa.Interfaz;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class RenderizadorVentana implements TableCellRenderer {
    private final ImageIcon icono;

    public RenderizadorVentana(String rutaIcono) {
        ImageIcon original = new ImageIcon(getClass().getResource(rutaIcono));
        Image img = original.getImage().getScaledInstance(14, 14, Image.SCALE_SMOOTH);
        icono = new ImageIcon(img);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton boton = new JButton();
            boton.setIcon(icono);
            boton.setBorderPainted(false);
            boton.setContentAreaFilled(false);
            boton.setFocusPainted(false);
            boton.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return boton;
        }
    }



