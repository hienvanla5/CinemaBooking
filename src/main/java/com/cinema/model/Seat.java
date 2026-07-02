package com.cinema.model;

public class Seat {

    private int id;
    private int theaterId;
    private int row;
    private int column;

    public Seat(int id, int theaterId, int row, int column) {
        this.id = id;
        this.theaterId = theaterId;
        this.row = row;
        this.column = column;
    }

    public int getId() {
        return id;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", theaterId=" + theaterId +
                ", row=" + row +
                ", column=" + column +
                '}';
    }
}
