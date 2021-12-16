package edu.usfca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates a Song object, an extension of Entity.
 * This class includes various methods used to facilitate usage of Song.
 * The instance variables that we used in this HW are: name, album, performer, and songID.
 * Includes a method to write to and fetch data from a database using SQL.
 */
public class Song extends Entity {
    protected Album album;
    protected Artist performer;
    protected String vibe;
    protected boolean liked;
    protected int songCounter = 0;
    protected int songID;

    /**
     * Constructor for Song
     * Initialized with blank Album, blank Artist, blank vibe, liked set to false.
     * @param name The name of the Song you would like to construct.
     */
    public Song(String name) {
        super(name);
        album = new Album();
        performer = new Artist();
        vibe = "";
        liked = false;
        songCounter ++;
        songID = songCounter;

    }

    /**
     * Creates an empty Song constructor, to be used when we need to construct a Song object for which we don't have a name yet.
     */
    public Song() {

    }

    /**
     * This method allows you to set the name of the Song object.
     * @param n The String you would like to set as the name.
     */
    public void setName(String n){
        this.name = n;
    }

    /**
     * Allows you to fetch the vibe of the Song
     * @return returns the vibe of the song as a String
     */
    public String getVibe() {
        return vibe;
    }


    /**
     * Allows you to set the vibe of a song
     * @param vibe The String you would like to set as the vibe.
     */
    public void setVibe(String vibe) {
        this.vibe = vibe;
    }

    /**
     * Set the songID, separate from EntityID, the iteration of songID is for Songs only.
     * @param id The int you would like to set as the songID.
     */
    public void setID(int id){
        songID = id;
    }

    /**
     * Fetch the SongID.
     * @return The SongID as an int.
     */
    public int getID(){
        return songID;
    }


    /**
     * Fetches the Album object associated with the Song.
     * @return Album object.
     */
    protected Album getAlbum() {

        return album;
    }

    /**
     * Allows you to set an Album object as the Album of the Song.
     * @param album Album you would like to set.
     */
    protected void setAlbum(Album album) {
        this.album = album;
    }

    /**
     * Fetches the Artist object associated with this Song.
     * @return Artist of the Song.
     */
    public Artist getPerformer() {
        return performer;
    }

    /**
     * Allows you to set the Artist object associated with this Song.
     * @param performer The Artist you would like to set.
     */
    public void setPerformer(Artist performer) {
        this.performer = performer;
    }

    /**
     * Check whether a Song is liked or not.
     * @return The boolean value for whether the Song is liked.
     */
    public boolean isLiked() {
        return liked;
    }

    /**
     * Set whether the Song is liked or not.
     * @param liked The boolean value you want to set for liked.
     */
    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    /**
     * Convert the information held by the Song object to a String.
     * @return The Song and it's Artist, and Album as a single String.
     */
    public String toString() {
        return super.toString() + " " + this.performer + " " + this.album;

    }

    /**
     * This method allows you to write the information associated with the Song object to the songs table in music.db, by using sqlite3.
     */
    public void toSQL(){
        String insertString = "insert into songs values (" + this.songID + ", \'" + this.name + "\', \'" + this.performer.artistID + "\', \'" + this.album.albumID + "\');";

        Connection connectionS = null;
        try {
            // create a database connection
            connectionS = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connectionS.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate(insertString);

//            ResultSet rsSong = statement.executeQuery("select * from songs");
//            while (rsSong.next()) {
//                // read the result set
//                System.out.println("name = " + rsSong.getString("name"));
//                System.out.println("id = " + rsSong.getInt("id"));
//            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connectionS != null)
                    connectionS.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * This method converts the information in the songs table of music.db and returns a list populated by Song objects
     * for each Song in the songs table.
     * @return List of Songs.
     */
    public List<Song> fromSQL(){
        List<Song> export = new ArrayList<>();

        Connection connectionS = null;
        try {
            // create a database connection
            connectionS = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connectionS.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rsSong = statement.executeQuery("select * from songs");
            while (rsSong.next()) {
                Song temp = new Song(rsSong.getString("name"));
                temp.setID(rsSong.getInt("id"));
                int artID = rsSong.getInt("artist");
                int albID = rsSong.getInt("album");

                statement = connectionS.createStatement();
                ResultSet rsArtist = statement.executeQuery("select name from artist where id == " + String.valueOf(artID));
                while (rsArtist.next()){
                    temp.setPerformer(new Artist(rsArtist.getString("name"), artID));
                }


                statement = connectionS.createStatement();
                ResultSet rsAlbum = statement.executeQuery("select name from album where id == " + String.valueOf(albID));
                while(rsAlbum.next()){
                    temp.setAlbum(new Album(rsAlbum.getString("name"), albID));
                }

                export.add(temp);


                //System.out.println("Song: " + temp.name + ", ID: " + temp.songID + "; artist: " + temp.performer.name + "; album: " + temp.album.name);

            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connectionS != null)
                    connectionS.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return export;
    }


}
