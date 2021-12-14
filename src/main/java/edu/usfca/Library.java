package edu.usfca;

import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    public ArrayList<Song> librarySongs;
    private ArrayList<Song> likedSongs;

    public Library() {
        librarySongs = new ArrayList<>();
    }

    public ArrayList<Song> getSongs() {
        return librarySongs;
    }


    public Song getSong(String s){
        for (Song song: librarySongs){
            if (song.name.equals(s)){
                return song;
            }
        }
        return null;
    }

    public void addSongs(Song s) {
        librarySongs.add(s);
    }

    public void deleteSong(Song s){
        librarySongs.remove(s);
    }

    public boolean findSong(Song s) {
        return (librarySongs.contains(s));
    }

    public ArrayList<Song> getLiked() {
        likedSongs = new ArrayList<>();
        for (Song song: librarySongs){
            if (song.isLiked()){
                likedSongs.add(song);
            }
        }
        return likedSongs;
    }

    public String toXML(){
        StringBuilder XMLString = new StringBuilder();
        XMLString.append("<playlist>\n  <songs>");
        for (Song song: librarySongs){
            XMLString.append("\n\t<song id=" + song.songID + ">");
            XMLString.append("\n\t\t<title>" + song.name + "</title>");
            XMLString.append("\n\t\t<artist id=" + song.performer.artistID + ">");
            XMLString.append("\n\t\t" + song.performer.name);
            XMLString.append("\n\t\t</artist>");
            XMLString.append("\n\t\t<album id=" + song.album.albumID + ">");
            XMLString.append("\n\t\t" + song.album.name);
            XMLString.append("\n\t\t</album>");
            XMLString.append("\n  </song>");

        }
        XMLString.append("</songs></playlist>");

        return XMLString.toString();
    }


}
