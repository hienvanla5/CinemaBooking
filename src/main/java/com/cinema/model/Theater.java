package com.cinema.model;

/**
 * Represents a theater where movies are screened.
 * A theater has a unique identifier, a name, and a seating layout
 * defined by the number of rows and columns.
 */
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

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
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