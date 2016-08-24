package ru.testtasks.komus.view;

import com.opencsv.CSVReader;

import com.opencsv.CSVWriter;
import ru.testtasks.komus.model.SimpleTableData;
import ru.testtasks.komus.utils.FileEncodingConverter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import java.io.*;
import java.util.ArrayList;

import java.util.List;

public class SimpleSwingDemo extends JFrame {

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

        JMenuItem saveFileItem = new JMenuItem("Save  to File");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(openFileItem);

        fileMenu.add(saveFileItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);

        exitItem.addActionListener(e -> System.exit(0));

        openFileItem.addActionListener(e -> {
            openFile();
        });

        saveFileItem.addActionListener(e -> {
            saveFile();
        });

        this.setVisible(true);
    }

    private void openFile() {
        if (table !=null)
        {
            System.out.println("table"+table.getColumnName(1));
        }

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv", "txt");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showDialog(null, "Open a inFile");

        if (returnValue == JFileChooser.APPROVE_OPTION) {

            File inFile = fileChooser.getSelectedFile();

            try {

                CSVReader reader = new CSVReader(new FileReader(FileEncodingConverter.convertToUtf8(inFile)), ',');

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
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ArrayIndexOutOfBoundsException aioube) {
                JOptionPane.showMessageDialog(this, "Wrong inFile encoding or inFile format");
            }
            Object[][] data = new Object[lines.size()][SimpleTableData.getNumberOfFields()];

            for (int j = 0; j < lines.size(); j++) {
                data[j] = lines.get(j).toObjectArray();
            }
            Object[] tales = {"Field1", "Field2", "Field3", "IntField1", "Field4", "Field5", "IntField2", "DoubleField"};

            table = new JTable(data, tales);
            jsp = new JScrollPane(table);
            this.add(jsp);

        }
    }

    private void saveFile() {
        TableModel model = table.getModel();
        lines.clear();
        for (int i = 0; i < model.getRowCount(); i++) {
            SimpleTableData std = new SimpleTableData();
            std.setField1((String) model.getValueAt(i, 0));
            std.setField2((String) model.getValueAt(i, 1));
            std.setField3((String) model.getValueAt(i, 2));
            std.setIntField1(Integer.valueOf(((String) model.getValueAt(i, 3)).trim()));
            std.setField4((String) model.getValueAt(i, 4));
            std.setField5((String) model.getValueAt(i, 5));
            std.setIntField2(Integer.valueOf(((String) model.getValueAt(i, 6)).trim()));
            std.setDoubleField(Double.valueOf(((String) model.getValueAt(i, 7)).trim()));
            lines.add(std);
        }
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

            try (FileWriter fw = new FileWriter(fileChooser.getSelectedFile())) {
                CSVWriter writer = new CSVWriter(fw);
                List<String[]> list = toStringArray(lines);
                writer.writeAll(list);
                writer.close();
            } catch (IOException ioe) {
                JOptionPane.showMessageDialog(this, "Sorry! file is corrupted");
            }
        }

    }

    private List<String[]> toStringArray(List<SimpleTableData> tableData) {
        List<String[]> result=new ArrayList<>();
        for (SimpleTableData std : lines) {
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
