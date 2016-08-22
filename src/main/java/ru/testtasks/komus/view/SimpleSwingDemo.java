package ru.testtasks.komus.view;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.testtasks.komus.model.SimpleTableData;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleSwingDemo {

    private File file;

    private SimpleSwingDemo() {
        JFrame jfr = new JFrame("test GUI");
        jfr.setSize(200, 300);
        jfr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Options");

        JMenuItem openFile = new JMenuItem("Open file");
        JMenuItem editFile = new JMenuItem("Edit file");
        JMenuItem exit = new JMenuItem("Exit");

        fileMenu.add(openFile);
        fileMenu.add(editFile);
        fileMenu.addSeparator();
        fileMenu.add(exit);

        menuBar.add(fileMenu);
        jfr.setJMenuBar(menuBar);
        exit.addActionListener(e -> System.exit(0));
        openFile.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv");
            fileChooser.setFileFilter(filter);
            int returnValue = fileChooser.showDialog(null, "Open a file");
            if (returnValue == JFileChooser.APPROVE_OPTION) {

                file = fileChooser.getSelectedFile();
                jfr.setJMenuBar(menuBar);
                try {
                    Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(new FileReader(file));
                    List<SimpleTableData> stds = new ArrayList<>();
                    for (CSVRecord record : records) {
                        SimpleTableData std = new SimpleTableData();
                        System.out.println(record.get(0));
                        std.setNumberOfLicence(record.get(0));
                        std.setParentProgram(record.get(1));
                        std.setFirm(record.get(2));
                        std.setProduct(record.get(3));
                        std.setProductKey(record.get(4));
                        std.setType(record.get(5));
                        std.setActivations(record.get(6));
                        std.setWorkingPlaces(Integer.valueOf(record.get(7)));
                        std.setStatus(record.get(8));
                        stds.add(std);
                    }

                    stds.forEach(std -> System.out.println());
                } catch (IOException exc) {
                    System.out.println("YEYEYEE");
                    exc.printStackTrace();
                }
            }
        });


        Object[] tales = {"Row1", "row2", "rOw3"};
        Object[][] data = new Object[50][3];
        data[0][0] = "first,first";
        data[0][1] = "first,second";
        data[1][0] = "second,first";
        JTable table = new JTable(data, tales);
        JScrollPane jsp = new JScrollPane(table);

        jfr.add(jsp);


        jfr.setVisible(true);
    }

    public static void main(String... args) {
        SwingUtilities.invokeLater(SimpleSwingDemo::new);
    }
}
