package edu.usfca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Playlist extends Entity {
    protected ArrayList<Song> listOfSongs;

    public Playlist() {
        listOfSongs = new ArrayList<>();
    }

    public void addSong(Song s) {
        listOfSongs.add(s);
    }

    public ArrayList<Song> getPlaylistSongs(){
        return listOfSongs;
    }

    public void deleteSong(Song s) {
        listOfSongs.remove(s);
    }

    public Playlist sortLikedFirst(){
        ArrayList<Song> likes = new ArrayList<>();
        ArrayList<Song> notLiked = new ArrayList<>();
        Playlist returnPlaylist = new Playlist();

        for(Song song: listOfSongs){
            if (song.isLiked()){
                likes.add(song);
            } else {
                notLiked.add(song);
            }
        }
        for (Song song: likes){
            returnPlaylist.addSong(song);
        }
        for (Song song: notLiked){
            returnPlaylist.addSong(song);
        }

        return returnPlaylist;
    }

    public Playlist shuffle(){
        Playlist shuffled = new Playlist();
        for (Song song: listOfSongs){
            shuffled.addSong(song);
        }

        Collections.shuffle(shuffled.listOfSongs);
        return shuffled;
    }

    public Playlist shuffleVibe(Library lib, String vibe){
        Playlist shuffled = new Playlist();
        for (Song song: lib.librarySongs){
            if (song.getVibe().equals(vibe)){
                shuffled.addSong(song);
            }
        }
        return shuffled;

    }


    public Playlist mergePlaylist(Playlist other){
        Set<Song> setSongs = new HashSet<>(listOfSongs);
        setSongs.addAll(other.listOfSongs);

        Playlist merged = new Playlist();
        for(Song song: setSongs){
            merged.addSong(song);
        }
        return merged;
    }

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
