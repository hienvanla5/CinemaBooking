package com.cinema.model;

public class Showtime {

    private int id;
    private int movieId;
    private int theaterId;
    private String startTime;

    public Showtime(int id, int movieId, int theaterId, String startTime) {
        this.id = id;
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.startTime = startTime;
    }

    public int getId() {
        return id;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getTheaterId() {
        return theaterId;
    }

    public String getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return "Showtime{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", theaterId=" + theaterId +
                ", startTime='" + startTime + '\'' +
                '}';
    }
}
