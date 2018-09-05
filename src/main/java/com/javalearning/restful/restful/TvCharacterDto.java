package com.javalearning.restful.restful;

import javax.validation.constraints.NotNull;

public class TvCharacterDto {

    private int id;
    private int tvSeriesId;
    @NotNull private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTvSeriesId() {
        return tvSeriesId;
    }

    public void setTvSeriesId(int tvSeriesId) {
        this.tvSeriesId = tvSeriesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
