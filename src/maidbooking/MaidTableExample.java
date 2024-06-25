import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class MaidTableExample {
    private static Map<String, List<String>> maidData; // Assume this is your maid data

    public static void main(String[] args) {
        fetchDataFromDatabase();

        DefaultTableModel model = createTableModel();
        JTable jTable = new JTable(model);
        jTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        jTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JButton(), createTable()));
        JScrollPane jScrollPane = new JScrollPane(jTable);

        JFrame frame = new JFrame("Maid Table Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(jScrollPane);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    private static void fetchDataFromDatabase() {
        // Your logic to fetch data from the database
    }

    private static DefaultTableModel createTableModel() {
        // Sort the entries based on the Rating column in descending order
        List<Map.Entry<String, List<String>>> sortedEntries = maidData.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getValue().get(1), Comparator.reverseOrder())) // Assuming rating is at index 1
                .collect(Collectors.toList());

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Rating");
        model.addColumn("Budget");
        model.addColumn("Action");

        // Populate the table model with sorted data
        for (Map.Entry<String, List<String>> entry : sortedEntries) {
            List<String> details = entry.getValue();
            model.addRow(new Object[]{entry.getKey(), details.get(1), details.get(2), "Button"});
        }

        return model;
    }

    private static JTable createTable() {
        JTable jTable = new JTable();
        jTable.setModel(createTableModel());
        return jTable;
    }

    private static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    private static class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private final JButton button;
        private final JTable table;

        public ButtonEditor(JButton button, JTable table) {
            this.button = button;
            this.button.addActionListener(this);
            this.table = table;
        }

        @Override
        public Object getCellEditorValue() {
            return button.getText();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            String name = (String) table.getModel().getValueAt(row, 0);
            String rating = (String) table.getModel().getValueAt(row, 1);
            String budget = (String) table.getModel().getValueAt(row, 2);

            // Perform any actions based on the clicked row's data
            JOptionPane.showMessageDialog(null, "Button clicked for: Name=" + name + ", Rating=" + rating + ", Budget=" + budget);
            fireEditingStopped();
        }
    }
}
