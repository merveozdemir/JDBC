package com.uniyaz;

import java.math.BigDecimal;

public class Movie {
    String movie;
    BigDecimal budget;

    public Movie(String movie, BigDecimal budget) {
        this.movie = movie;
        this.budget = budget;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
}
