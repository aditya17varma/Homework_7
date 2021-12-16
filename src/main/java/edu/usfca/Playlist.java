package edu.usfca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * This class creates an ArrayList that holds Song objects, to act as a playlist.
 * Songs can be added, and removed from the playlist.
 * Includes a method to export the playlist to an XML file.
 */
public class Playlist extends Entity {
    /**
     * ArrayList of all the songs in the Playlist.
     */
    protected ArrayList<Song> listOfSongs;

    /**
     * /**
     * Constructor for the Playlist class.
     */
    public Playlist() {
        listOfSongs = new ArrayList<>();
    }

    /**
     * This method allows you to add a Song to the ArrayList within the Playlist.
     * @param s The Song you would like to add.
     */
    public void addSong(Song s) {
        listOfSongs.add(s);
    }

    /**
     * This method allows you to access all the Songs in the Playlist.
     * @return Returns an ArrayList containing all the Songs in the Playlist.
     */
    public ArrayList<Song> getPlaylistSongs(){
        return listOfSongs;
    }

    /**
     * This method allows you to remove a Song from the Playlist.
     * @param s The Song you would like to remove from the Playlist.
     */
    public void deleteSong(Song s) {
        listOfSongs.remove(s);
    }


//    public Playlist sortLikedFirst(){
//        ArrayList<Song> likes = new ArrayList<>();
//        ArrayList<Song> notLiked = new ArrayList<>();
//        Playlist returnPlaylist = new Playlist();
//
//        for(Song song: listOfSongs){
//            if (song.isLiked()){
//                likes.add(song);
//            } else {
//                notLiked.add(song);
//            }
//        }
//        for (Song song: likes){
//            returnPlaylist.addSong(song);
//        }
//        for (Song song: notLiked){
//            returnPlaylist.addSong(song);
//        }
//
//        return returnPlaylist;
//    }

//    public Playlist shuffle(){
//        Playlist shuffled = new Playlist();
//        for (Song song: listOfSongs){
//            shuffled.addSong(song);
//        }
//
//        Collections.shuffle(shuffled.listOfSongs);
//        return shuffled;
//    }
//
//    public Playlist shuffleVibe(Library lib, String vibe){
//        Playlist shuffled = new Playlist();
//        for (Song song: lib.librarySongs){
//            if (song.getVibe().equals(vibe)){
//                shuffled.addSong(song);
//            }
//        }
//        return shuffled;
//
//    }


    /**
     * This method allows you to merge two Playlists, no duplicates are contained in the merged Playlist.
     * @param other The second Playlist you would like to merge with.
     * @return Returns a merged Playlist.
     */
    public Playlist mergePlaylist(Playlist other){
        Set<Song> setSongs = new HashSet<>(listOfSongs);
        setSongs.addAll(other.listOfSongs);

        Playlist merged = new Playlist();
        for(Song song: setSongs){
            merged.addSong(song);
        }
        return merged;
    }

    /**
     * This method writes the Playlist as an XML file.
     * @return An XML file reflecting the Songs in the Playlist.
     */
    public String toXML(){
        StringBuilder XMLString = new StringBuilder();
        XMLString.append("<playlist>\n  <songs>");
        for (Song song: listOfSongs){
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
