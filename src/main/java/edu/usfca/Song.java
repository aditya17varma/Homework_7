package edu.usfca;

import java.sql.*;


public class Song extends Entity {
    protected Album album;
    protected Artist performer;
    protected String vibe;
    protected boolean liked;
    protected int songCounter = 0;
    protected int songID;

    public Song(String name) {
        super(name);
        album = new Album();
        performer = new Artist();
        vibe = "";
        liked = false;
        songCounter ++;
        songID = songCounter;

    }

    public Song() {

    }

    public String getVibe() {
        return vibe;
    }

    public void setVibe(String vibe) {
        this.vibe = vibe;
    }

    public void setID(int id){
        songID = id;
    }

    public int getID(){
        return songID;
    }


    protected Album getAlbum() {

        return album;
    }

    protected void setAlbum(Album album) {
        this.album = album;
    }

    public Artist getPerformer() {
        return performer;
    }

    public void setPerformer(Artist performer) {
        this.performer = performer;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public String toString() {
        return super.toString() + " " + this.performer + " " + this.album;

    }

    public void toSQL(){
        String insertString = "insert into songs values (" + this.songID + ", \'" + this.name + "\', \'" + this.performer.artistID + "\', \'" + this.album.albumID + "\');";

        Connection connectionS = null;
        try {
            // create a database connection
            connectionS = DriverManager.getConnection("jdbc:sqlite:music.db");
            Statement statement = connectionS.createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate(insertString);

            ResultSet rsSong = statement.executeQuery("select * from songs");
            while (rsSong.next()) {
                // read the result set
                System.out.println("name = " + rsSong.getString("name"));
                System.out.println("id = " + rsSong.getInt("id"));
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
    }

    public void fromSQL(){

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


                System.out.println("Song: " + temp.name + ", ID: " + temp.songID + "; artist: " + temp.performer.name + "; album: " + temp.album.name);

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
    }


}
