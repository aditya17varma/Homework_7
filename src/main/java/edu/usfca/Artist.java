package edu.usfca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Artist extends Entity {

    protected ArrayList<Song> songs;
    protected ArrayList<Album> albums;
    protected int artistCounter = 0;
    protected int artistID;

    public Artist(String name) {
        super(name);
        artistCounter ++;
        artistID = 0;
    }

    public Artist() {

    }

    public Artist(String name, int id){
        super(name);
        artistID = id;
    }

    public void setID(int id){
        this.artistID = id;
    }

    public int getID(){
        return this.artistID;
    }

    protected ArrayList<Song> getSongs() {
        return songs;
    }

    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    protected ArrayList<Album> getAlbums() {
        return albums;
    }

    protected void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
    }

    public void addSong(Song s) {
        songs.add(s);
    }

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
