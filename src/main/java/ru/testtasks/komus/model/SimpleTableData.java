package ru.testtasks.komus.model;


import org.apache.commons.collections.ArrayStack;

import java.util.ArrayList;
import java.util.List;

public class SimpleTableData {

    private String field1;
    private String field2;
    private String field3;
    private Integer intField1;
    private String field4;
    private String field5;
    private Integer intField2;
    private Double doubleField;

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public int getIntField1() {
        return intField1;
    }

    public void setIntField1(int intField1) {
        this.intField1 = intField1;
    }

    public String getField4() {
        return field4;
    }

    public void setField4(String field4) {
        this.field4 = field4;
    }

    public String getField5() {
        return field5;
    }

    public void setField5(String field5) {
        this.field5 = field5;
    }

    public int getIntField2() {
        return intField2;
    }

    public void setIntField2(int intField2) {
        this.intField2 = intField2;
    }

    public double getDoubleField() {
        return doubleField;
    }

    public void setDoubleField(double doubleField) {
        this.doubleField = doubleField;
    }

    @Override
    public String toString() {
        return "SimpleTableData{" +
                "field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", field3='" + field3 + '\'' +
                ", intField1=" + intField1 +
                ", field4='" + field4 + '\'' +
                ", field5='" + field5 + '\'' +
                ", intField2=" + intField2 +
                ", doubleField=" + doubleField +
                '}';
    }
}
