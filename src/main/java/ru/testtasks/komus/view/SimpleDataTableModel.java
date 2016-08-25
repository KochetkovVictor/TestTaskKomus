package ru.testtasks.komus.view;

import ru.testtasks.komus.model.SimpleTableData;

import javax.swing.table.AbstractTableModel;
import java.util.List;


class SimpleDataTableModel extends AbstractTableModel {

    private List<SimpleTableData> simpleTableDataList;

    SimpleDataTableModel(List<SimpleTableData> simpleTableDataList) {
        super();
        this.simpleTableDataList = simpleTableDataList;
    }

    @Override
    public int getRowCount() {
        return simpleTableDataList.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return simpleTableDataList.get(rowIndex).getField1();
            case 1:
                return simpleTableDataList.get(rowIndex).getField2();
            case 2:
                return simpleTableDataList.get(rowIndex).getField3();
            case 3:
                return simpleTableDataList.get(rowIndex).getIntField1();
            case 4:
                return simpleTableDataList.get(rowIndex).getField4();
            case 5:
                return simpleTableDataList.get(rowIndex).getField5();
            case 6:
                return simpleTableDataList.get(rowIndex).getIntField2();
            case 7:
                return simpleTableDataList.get(rowIndex).getDoubleField();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Field1";
            case 1:
                return "Field2";
            case 2:
                return "Field3";
            case 3:
                return "IntField1";
            case 4:
                return "Field4";
            case 5:
                return "Field5";
            case 6:
                return "IntField2";
            case 7:
                return "DoubleField";
            default:
                return "Other Column";
        }
    }

    @Override
    public void fireTableDataChanged() {
        super.fireTableDataChanged();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0:
                return simpleTableDataList.get(0).getField1().getClass();
            case 1:
                return simpleTableDataList.get(0).getField2().getClass();
            case 2:
                return simpleTableDataList.get(0).getField3().getClass();
            case 3:
                return Integer.class;
            case 4:
                return simpleTableDataList.get(0).getField4().getClass();
            case 5:
                return simpleTableDataList.get(0).getField5().getClass();
            case 6:
                return Integer.class;
            case 7:
                return Double.class;
            default:
                return null;
        }
    }

    List<SimpleTableData> getSimpleTableDataList() {
        return simpleTableDataList;
    }
}
