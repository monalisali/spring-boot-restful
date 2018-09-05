package com.javalearning.restful.restful;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

public class TvSeriesDto {

    private int id;

    //name不能为空
    @NotNull private String name;

    //seasonCount的最小值为1
    @DecimalMin("1") private  int seasonCount;

    //@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date originRelease;

    //tvCharacters的数量最小值为：2 @Size(min=2)
    //@Valid:级联验证，会去验证 TvCharacterDto中的内容
    @Valid @NotNull @Size(min=2) private List<TvCharacterDto> tvCharacters;



    public TvSeriesDto()
    {

    }

    public  TvSeriesDto(int id, String name,int seasonCount, Date originRelease)
    {
        this.id = id;
        this.name = name;
        this.seasonCount = seasonCount;
        this.originRelease = originRelease;
    }


    public TvSeriesDto(int id, String name,int seasonCount, Date originRelease, List<TvCharacterDto> tvCharacters)
    {
        this.id = id;
        this.name = name;
        this.seasonCount = seasonCount;
        this.originRelease = originRelease;
        this.tvCharacters = tvCharacters;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeasonCount() {
        return seasonCount;
    }

    public void setSeasonCount(int seasonCount) {
        this.seasonCount = seasonCount;
    }

    public Date getOriginRelease() {
        return originRelease;
    }

    public void setOriginRelease(Date originRelease) {
        this.originRelease = originRelease;
    }

    public List<TvCharacterDto> getTvCharacters() {
        return tvCharacters;
    }

    public void setTvCharacters(List<TvCharacterDto> tvCharacters) {
        this.tvCharacters = tvCharacters;
    }

//    @Override
//    public String toString(){
//        return this.getClass().getName() + "{id=" + id + ";name=" + name + "}";
//    }


}