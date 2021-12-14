package edu.usfca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Album extends Entity {
    protected ArrayList<Song> songs;
    protected Artist artist;
    protected int albumcounter = 0;
    protected int albumID;

    public Album(String name) {
        super(name);
        artist = new Artist("");
        songs = new ArrayList<>();
        albumcounter ++;
        albumID = 0;
    }

    public Album() {

    }

    public Album(String s, int id){
        super(s);
        albumID = id;
    }

    public String getName() {
        System.out.println("this is an album" + super.getName());
        return name;
    }

    public void setID(int id) {
        this.albumID = id;
    }

    public int getID() {
        return this.albumID;
    }

    public boolean equals(Album otherAlbum) {
        if ((this.artist.equals(otherAlbum.getArtist())) &&
                (this.name.equals(otherAlbum.getName()))) {
            return true;
        } else {
            return false;
        }
    }


    protected ArrayList<Song> getSongs() {
        return songs;
    }

    protected void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

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

