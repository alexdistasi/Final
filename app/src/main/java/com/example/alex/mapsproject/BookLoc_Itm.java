package com.example.alex.mapsproject;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Alex on 11/29/2016.
 */
public class BookLoc_Itm {
    //Members
    private int _id;
    private LatLng bookLoc;
    private String bookLocStr;

    private double lat;
    private double longi;
    private String title;
    private String genre;
    private String author;

    public BookLoc_Itm(){
    }

    // public BookLoc_Itm(int idIn, double latIn, double longIn, String titleIn, String genreIn, String authorIn){
    public BookLoc_Itm(int idIn, double latIn, double longIn, String titleIn, String genreIn, String authorIn){
        _id=idIn;

        lat = latIn;
        longi = longIn;
        title = titleIn;
        genre = genreIn;
        author = authorIn;
    }

    public int getId() {
        return _id;
    }
    public void setId(int idIn) {
        _id = idIn;
    }

    public double getLat() {
        return lat;
    }
    public void setLat(double latIn) {
        lat = latIn;
    }

    public double getLongi() {
        return longi;
    }
    public void setLongi(double longIn) {
        longi = longIn;
    }


    public void setBookLoc(LatLng spLocIn){
        bookLoc = spLocIn;
    }
    public LatLng getBookLoc(){
        return bookLoc;
    }


    public void setTitle(String titleIn){
        title = titleIn;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String authorIn){
        author = authorIn;
    }

    public String getGenre(){
        return genre;
    }

    public void setGenre(String genreIn){
        genre = genreIn;
    }

    public String testDB(){
        String res = title + " " + author + " " + genre + " " + lat + " " + longi;
        return res;
    }



}
