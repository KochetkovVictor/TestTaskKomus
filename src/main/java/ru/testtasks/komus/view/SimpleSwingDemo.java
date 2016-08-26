package ru.testtasks.komus.view;

import com.opencsv.CSVReader;

import com.opencsv.CSVWriter;
import org.slf4j.LoggerFactory;
import ru.testtasks.komus.model.SimpleTableData;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.LogManager;


public class SimpleSwingDemo extends JFrame {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SimpleSwingDemo.class);

    private List<SimpleTableData> lines = new ArrayList<>();
    private JTable table;
    private JScrollPane jsp;

    private SimpleSwingDemo() {

        super("test GUI");
        super.setSize(200, 100);
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Options");

        JMenuItem openFileItem = new JMenuItem("Open from File");
        JMenuItem editFileItem = new JMenuItem("Edit");
        JMenuItem saveFileItem = new JMenuItem("Save  to File");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(openFileItem);
        fileMenu.add(editFileItem);
        fileMenu.add(saveFileItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);

        exitItem.addActionListener(e -> {
            LOG.info("Application is closed");
            System.exit(0);
        });

        openFileItem.addActionListener(e -> {
            openFile();
        });

        editFileItem.addActionListener(e -> {
                    LOG.info("The model is set to be editable");
                    ((SimpleDataTableModel) table.getModel()).setEditable();
                }
        );

        saveFileItem.addActionListener(e -> {
            saveFile();
        });

        this.setVisible(true);
    }

    private void openFile() {

        if (table != null) {
            this.remove(jsp);
            jsp = new JScrollPane();
            ((SimpleDataTableModel) table.getModel()).getSimpleTableDataList().clear();
            this.repaint();
            this.setSize(new Dimension(1000, 100));
        }

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv", "txt");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showDialog(null, "Open a inFile");

        if (returnValue == JFileChooser.APPROVE_OPTION) {

            File inFile = fileChooser.getSelectedFile();

            try {
                CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(new FileInputStream(inFile), "UTF-8")), ',');
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    SimpleTableData std = new SimpleTableData();
                    std.setField1(nextLine[0]);
                    std.setField2(nextLine[1]);
                    std.setField3(nextLine[2]);
                    std.setIntField1(Integer.valueOf(nextLine[3].trim()));
                    std.setField4(nextLine[4]);
                    std.setField5(nextLine[5]);
                    std.setIntField2(Integer.valueOf(nextLine[6].trim()));
                    std.setDoubleField(Double.valueOf(nextLine[7].trim()));
                    lines.add(std);
                }
                LOG.info("Open a file " + inFile.getAbsolutePath());
                reader.close();
            } catch (IOException ioe) {
                LOG.debug(ioe.getMessage());
            } catch (Exception exc) {
                LOG.debug("The reason of exception is " + exc.toString());
                JOptionPane.showMessageDialog(this, "Wrong inFile encoding or inFile format");
            }

            TableModel model = new SimpleDataTableModel(lines);
            table = new JTable(model);
            RowSorter<TableModel> sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);
            this.setSize(1000, 200);
            table.setPreferredScrollableViewportSize(new Dimension(950, 100));
            jsp = new JScrollPane(table);
            this.add(jsp);

        }
    }

    private void saveFile() {
        TableModel model = table.getModel();
        lines = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            SimpleTableData std = new SimpleTableData();
            std.setField1((String) model.getValueAt(i, 0));
            std.setField2((String) model.getValueAt(i, 1));
            std.setField3((String) model.getValueAt(i, 2));
            std.setIntField1((Integer) model.getValueAt(i, 3));
            std.setField4((String) model.getValueAt(i, 4));
            std.setField5((String) model.getValueAt(i, 5));
            std.setIntField2((Integer) model.getValueAt(i, 6));
            std.setDoubleField((Double) model.getValueAt(i, 7));
            lines.add(std);
        }

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv", "txt");
        fileChooser.setFileFilter(filter);
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

            try (Writer fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileChooser.getSelectedFile()), "UTF-8"))) {
                CSVWriter writer = new CSVWriter(fw);
                List<String[]> list = toStringArray(lines);
                writer.writeAll(list);
                writer.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
                LOG.debug(ioe.getMessage());
                JOptionPane.showMessageDialog(this, "Sorry! file is corrupted");
            }
        }
        lines.clear();
        LOG.info("Save the model to file " + fileChooser.getSelectedFile().getAbsolutePath());
    }

    private List<String[]> toStringArray(List<SimpleTableData> tableData) {
        List<String[]> result = new ArrayList<>();
        for (SimpleTableData std : tableData) {
            result.add(new String[]{std.getField1(),
                    std.getField2(), std.getField3(),
                    std.getIntField1() + "",
                    std.getField4(), std.getField5(),
                    std.getIntField2() + "",
                    std.getDoubleField() + ""});
        }
        return result;
    }

    public static void main(String... args) {
        SwingUtilities.invokeLater(SimpleSwingDemo::new);
    }
}
