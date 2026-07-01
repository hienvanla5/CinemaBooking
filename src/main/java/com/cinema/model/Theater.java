package com.cinema.model;

public class Theater {

    private int id;
    private String name;
    private int totalRows;
    private int totalColumns;

    public Theater(int id, String name, int totalRows, int totalColumns) {
        this.id = id;
        this.name = name;
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    @Override
    public String toString() {
        return "Theater{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", totalRows=" + totalRows +
                ", totalColumns=" + totalColumns +
                '}';
    }
}
