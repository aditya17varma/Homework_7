package edu.usfca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates an Artist object, an extension of Entity.
 * This class includes various methods used to facilitate usage of Artist.
 * The instance variables that we used in this HW are: name, and albumID.
 * Includes a method to write to and fetch data from a database using SQL.
 */
public class Artist extends Entity {

    /**
     * List of Songs performed by the Artist.
     */
    protected ArrayList<Song> songs;
    /**
     * List of Albums performed by the Artist.
     */
    protected ArrayList<Album> albums;
    /**
     * Iterator to keep track of the artistID.
     */
    protected int artistCounter = 0;
    /**
     * ID given to count the number of Artists and keep track of index in database.
     */
    protected int artistID;

    /**
     * Constructor for Artist.
     * @param name The name of the Artist you would like to construct.
     */
    public Artist(String name) {
        super(name);
        artistCounter ++;
        artistID = 0;
    }

    /**
     * Creates an empty Artist constructor, to be used when we need to construct an Artist object for which we don't have a name yet.
     */
    public Artist() {

    }

    /**
     * Constructor for Artist, when a particular ArtistID is to be used.
     * @param name The name of the Artist you would like to construct.
     * @param id The ArtistID you would like to set.
     */
    public Artist(String name, int id){
        super(name);
        artistID = id;
    }

    /**
     * Set the artistID, separate from EntityID, the iteration of artistID is for Artists only.
     * @param id The int you would like to set as the artistID.
     */
    public void setID(int id){
        this.artistID = id;
    }

//    public int getID(){
//        return this.artistID;
//    }
//
//    protected ArrayList<Song> getSongs() {
//        return songs;
//    }
//
//    protected void setSongs(ArrayList<Song> songs) {
//        this.songs = songs;
//    }
//
//    protected ArrayList<Album> getAlbums() {
//        return albums;
//    }
//
//    protected void setAlbums(ArrayList<Album> albums) {
//        this.albums = albums;
//    }
//
//    public void addSong(Song s) {
//        songs.add(s);
//    }

    /**
     * This method allows you to write the information associated with the Artist object to the artist table in music.db, by using sqlite3.
     */
    public void toSQL(){
        String insertString = "insert into artist (id, name) values (" + this.artistID + ", \'" + this.name + "\' );";

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
     * This method converts the information in the artist table of music.db and returns a list populated by Artist objects
     * for each Artist in the artist table.
     * @return List of Artists.
     */
    public List<Artist> fromSQL(){
        Connection connectionS = null;
        List<Artist> export = new ArrayList<>();
        try {
            // create a database connection
            connectionS = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connectionS.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            ResultSet rsSong = statement.executeQuery("select * from artist");
            while (rsSong.next()) {
                Artist temp = new Artist(rsSong.getString("name"));
                temp.setID(rsSong.getInt("id"));
                //System.out.println("Artist: " + temp.name + ", ID: " + temp.artistID + "; type: " + temp.getClass());
                export.add(temp);
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
