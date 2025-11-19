/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.msoft.carehomeapp.model;

/**
 *
 * @author lucas
 */
public class Song {
    private String genre;
    private String artist;
    private String title;
    private String album;
    
    public Song(String title, String artist, String album, String genre){
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.genre = genre;
    };
    
    public Song(){};
    
    public String getGenre(){
      return genre;  
    };
    
    public String getArtist(){
        return artist;
    }
    
    public String getTitle(){
        return title;
    }
    
    public String getAlbum(){
        return album;
    }
    
}
