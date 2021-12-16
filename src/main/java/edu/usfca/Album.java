package edu.usfca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates an Album object, an extension of Entity.
 * This class includes various methods used to facilitate usage of Album.
 * The instance variables that we used in this HW are: name, Artist, and albumID.
 * Includes a method to write to and fetch data from a database using SQL.
 */
public class Album extends Entity {
    /**
     * List of all the songs on the Album.
     */
    protected ArrayList<Song> songs;
    /**
     * The Artist that performed the Album.
     */
    protected Artist artist;
    /**
     * Iterator to keep track of albumID.
     */
    protected int albumcounter = 0;
    /**
     * ID given to count the number of Albums and keep track of index in the database.
     */
    protected int albumID;

    /**
     * Constructor for Album.
     * Initialized with blank Artist, and empty list of songs.
     * @param name The name of the Artist you would like to construct.
     */
    public Album(String name) {
        super(name);
        artist = new Artist("");
        songs = new ArrayList<>();
        albumcounter ++;
        albumID = 0;
    }
    /**
     * Creates an empty Album constructor, to be used when we need to construct an Album object for which we don't have a name yet.
     */
    public Album() {

    }

    /**
     * Constructs an Album object when the name is given, and a particular albumID is to be used.
     * @param s The name of the Album.
     * @param id The albumID of the Album.
     */
    public Album(String s, int id){
        super(s);
        albumID = id;
    }

    /**
     * Fetches the name of the Album object.
     * @return The name of the album as a String.
     */
    public String getName() {
        System.out.println("this is an album" + super.getName());
        return name;
    }

    /**
     * Set the AlbumID, separate from EntityID, the iteration of AlbumID is for Albums only.
     * @param id The int you would like to set as the albumID.
     */
    public void setID(int id) {
        this.albumID = id;
    }

//    public int getID() {
//        return this.albumID;
//    }
//
//    public boolean equals(Album otherAlbum) {
//        if ((this.artist.equals(otherAlbum.getArtist())) &&
//                (this.name.equals(otherAlbum.getName()))) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//
//    protected ArrayList<Song> getSongs() {
//        return songs;
//    }
//
//    protected void setSongs(ArrayList<Song> songs) {
//        this.songs = songs;
//    }

    /**
     * Fetches the Artist associated with this Album
     * @return The Artist of the Album.
     */
    public Artist getArtist() {
        return artist;
    }

    /**
     * Allows you to set the Artist for the Album.
     * @param artist The Artist you would like to set for this Album.
     */
    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    /**
     * This method allows you to write the information associated with the Album object to the album table in music.db, by using sqlite3.
     */
    public void toSQL(){
        String insertString = "insert into album (id, name) values (" + this.albumID + ", \'" + this.name + "\' );";

        Connection connection = null;
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate(insertString);
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * This method converts the information in the album table of music.db and returns a list populated by Album objects
     * for each Album in the album table.
     * @return List of Albums.
     */
    public List<Album> fromSQL(){
        List<Album> export = new ArrayList<>();
        Connection connectionAl = null;
        try {
            // create a database connection
            connectionAl = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connectionAl.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rsSong = statement.executeQuery("select * from album");
            while (rsSong.next()) {
                Album temp = new Album(rsSong.getString("name"));
                temp.setID(rsSong.getInt("id"));
                //System.out.println("Album: " + temp.name + ", ID: " + temp.albumID + "; type: " + temp.getClass());
                export.add(temp);
            }
        } catch (SQLException e) {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connectionAl != null)
                    connectionAl.close();
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }
        return export;
    }
}

