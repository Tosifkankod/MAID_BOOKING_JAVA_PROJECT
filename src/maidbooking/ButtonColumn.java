/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maidbooking;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.*;
/**
 *
 * @author fisot
 */
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.*;

public class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
    private final JButton renderButton;
    private final JButton editButton;
    private Object editorValue;
    private JTable table;
    private DefaultTableModel model;

    public ButtonColumn(JTable table, DefaultTableModel model, int column) {
        this.table = table;
        this.model = model;
        renderButton = new JButton("Click");
        editButton = new JButton("Click");
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);
    }

    @Override
    public Object getCellEditorValue() {
        return editorValue;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
        // Here, you can perform the desired action when the button is clicked.
        // You can access the row and column using table.getSelectedRow() and table.getSelectedColumn().
        // Perform the database query or any other action you want.
        int row = table.convertRowIndexToModel(table.getEditingRow());
        int column = table.convertColumnIndexToModel(table.getEditingColumn());
        
        // Access data for the clicked row
        String name = (String) model.getValueAt(row, 0);
        String rating = (String) model.getValueAt(row, 1);
        String budget = (String) model.getValueAt(row, 2);

        // Perform your database query or any other action here using the retrieved data (name, rating, budget).
        // Example: Execute a query with the selected row data.
        System.out.println("Clicked button for row " + row + ": Name=" + name + ", Rating=" + rating + ", Budget=" + budget);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return renderButton;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        editorValue = value;
        return editButton;
    }
}
