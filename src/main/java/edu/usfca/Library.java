package edu.usfca;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class creates an ArrayList that holds Song objects, to act as a library.
 * Songs can be added, and removed from the library.
 * Includes a method to export the library to an XML file.
 */
public class Library {
    public ArrayList<Song> librarySongs;
    private ArrayList<Song> likedSongs;

    /**
     * Constructor for the Library class.
     */
    public Library() {
        librarySongs = new ArrayList<>();
    }

    /**
     * This method returns the ArrayList containing the Songs
     * @return Returns an ArrayList of Songs.
     */
    public ArrayList<Song> getSongs() {
        return librarySongs;
    }

    /**
     * This method allows you to access a song in the library with the song's name.
     * @param s The name of the Song as a string.
     * @return Returns the Song object in the library that matches the String provided.
     */
    public Song getSong(String s){
        for (Song song: librarySongs){
            if (song.name.equals(s)){
                return song;
            }
        }
        return null;
    }

    /**
     * This method allows you to add a Song to the Library.
     * @param s The Song you want to add to the Library.
     */
    public void addSongs(Song s) {
        librarySongs.add(s);
    }

    /**
     * This method allows you to delete a Song from the Library.
     * @param s The Song you want to delete.
     */
    public void deleteSong(Song s){
        librarySongs.remove(s);
    }

    /**
     * This method allows you to check whether or not a Song is contained in the Library.
     * @param s The Song you wish to check for.
     * @return A boolean reflecting the result of the search.
     */

    public boolean containsSong(String s){
        for (Song song: librarySongs){
            if (song.name.equals(s)){
                return true;
            }
        }
        return false;
    }

    /**
     * This method gives an ArrayList of all the Songs in the Library whose 'liked' parameter is 'true'.
     * @return An ArrayList of all Liked songs.
     */
    public ArrayList<Song> getLiked() {
        likedSongs = new ArrayList<>();
        for (Song song: librarySongs){
            if (song.isLiked()){
                likedSongs.add(song);
            }
        }
        return likedSongs;
    }

    /**
     * This method writes the Library as an XML file.
     * @return An XML file reflecting the Songs in the Library.
     */
    public String toXML(){
        StringBuilder XMLString = new StringBuilder();
        XMLString.append("<library>\n  <songs>");
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
        XMLString.append("</songs></library>");

        return XMLString.toString();
    }


}
