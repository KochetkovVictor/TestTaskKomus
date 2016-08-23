package ru.testtasks.komus.view;

import com.opencsv.CSVReader;

import ru.testtasks.komus.model.SimpleTableData;
import ru.testtasks.komus.utils.FileEncodingConverter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
            FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Files", "csv", "txt");
            fileChooser.setFileFilter(filter);
            int returnValue = fileChooser.showDialog(null, "Open a file");
            if (returnValue == JFileChooser.APPROVE_OPTION) {

                file = fileChooser.getSelectedFile();
                List<SimpleTableData>  lines=new ArrayList<>();
                try {

                    CSVReader reader = new CSVReader(new FileReader(FileEncodingConverter.convertToUtf8(file)), ',');

                    String[] nextLine;
                    while ((nextLine = reader.readNext()) != null) {
                        SimpleTableData std=new SimpleTableData();
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
                } catch (IOException ex) {

                    ex.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException aioube) {
                    JOptionPane.showMessageDialog(jfr, "Wrong file encoding");
                }
                lines.forEach(System.out::println);
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
