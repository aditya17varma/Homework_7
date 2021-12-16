package edu.usfca;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

/**
 * This class implements a switch case that lets you run the program in the terminal.
 */
public class Shell {
    /**
     * Library into which the database is loaded.
     */
    static Library l1 = new Library();
    /**
     * The Playlist.
     */
    static Playlist p1 = new Playlist();
    /**
     * HashMap to load Artists from the artist table in the database. Key = Artist name, Value = Artist object.
     */
    static HashMap<String, Artist> artistMap = new HashMap<>();
    /**
     * HashMap to load Albums from the album table in the database. Key = Album name, Value = Album object.
     */
    static HashMap<String, Album> albumMap = new HashMap<>();

    /**
     * This method loads all the songs from the song table into a Library.
     */
    private static void loadLibrary(){
        List<Song> importSongs = new Song().fromSQL();
        for (Song song: importSongs){
            l1.addSongs(song);
        }
    }

    /**
     * This method implements a HashTable with all the Artists in the database along with their artistID.
     * The name of the Artist is used as a key to access the Artist object.
     * This method loads data from the artist table on each call.
     * The size of this HashMap is used to properly index additions to the artist table in the database.
     */
    private static void hashArtist(){
        List<Artist> importArtists = new Artist().fromSQL();
        for (Artist artist: importArtists){
            artistMap.put(artist.name, artist);
        }
    }

    /**
     * This method implements a HashTable with all the Albums in the database along with their albumID.
     * The name of the Album is used as a key to access the Album object.
     * This method load data from the albums table on each call.
     * The size of this HashMap is used to properly index additions to the albums table in the database.
     */
    private static void hashAlbum(){
        List<Album> importAlbums = new Album().fromSQL();
        for (Album album: importAlbums){
            albumMap.put(album.name, album);
        }
    }


    /**
     * Main function that contains the switch-case to run on the terminal.
     * The switch-case allows the user to access the multitude of options available within the program.
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        boolean condition = true;

        System.out.println("Greetings!!");
        loadLibrary();
        hashAlbum();
        hashArtist();
        System.out.println("The songs from \"music.db\" have been loaded into the library.");


        while (condition) {
            System.out.println("Please select an option:");
            System.out.println(
                    "1. List all songs in library.\n" +
                    "2. List all artists in library.\n" +
                    "3. List all albums in library.\n" +

                    "4. Add song to library.\n" +
                    "5. Delete song from library.\n" +
                    "6. Add artist to library.\n" +
                    "7. Add album to library.\n" +

                    "8. Create a playlist of songs by a certain artist.\n" +
                    "9. List songs in a playlist\n" +
                    "10. Add a song to a playlist.\n" +
                    "11. Delete a song from playlist.\n" +

                    "12. Write the playlist out to a XML file.\n" +
                    "13. Write the library out to a XML file.\n" +
                    "14. Stop the program!");

            int selection;
            selection = Integer.parseInt(input.nextLine());

            switch (selection) {

                case 1:
                    //List all songs in library
                    for(Song s: l1.librarySongs){
                        System.out.println(s.name);
                    }
                    break;

                case 2:
                    //List all artists in library
                    for(String key: artistMap.keySet()){
                        System.out.println(key);
                    }
                    break;

                case 3:
                    //List all albums in library
                    for(String key: albumMap.keySet()){
                        System.out.println(key);
                    }
                    break;

                case 4:
                    //Add song to library
                    //Scanner cs4 = new Scanner(System.in);
                    System.out.println("What is the name of the song?");
                    String songName = input.nextLine();

                    if (l1.containsSong(songName)){
                        System.out.println("That song already exists in the library!!");
                    } else {
                        Song temp = new Song(songName);
                        int libSize = l1.librarySongs.size();
                        temp.songID = libSize + 1;

                        System.out.println("If you know the artist? Type \"yes\".");
                        if(input.nextLine().equals("yes")){
                            System.out.println("Type the artist name: \"artist\"");
                            String songArt = input.nextLine();
                            if (artistMap.containsKey(songArt)){
                                temp.getPerformer().setName(songArt);
                                temp.toSQL();
                            } else {
                                Artist tempArt = new Artist(songArt);
                                int artSize = artistMap.size();
                                tempArt.artistID = artSize + 1;
                                temp.toSQL();
                            }
                        } else {
                            System.out.println("Accessing MusicBrainz, please wait.");
                            String[] info = MusicBrainz.songMB(songName);
                            temp.setName(info[0]);
                            temp.getPerformer().setName(info[1]);
                            temp.getAlbum().setName(info[2]);
                            //Add artist to artist table
                            String tempArt = info[1];
                            if(artistMap.containsKey(tempArt)){
                                temp.getPerformer().setID(artistMap.get(tempArt).artistID);
                            } else{
                                Artist tempA = new Artist(tempArt);
                                //add to sql
                                int artSize = artistMap.size();
                                tempA.artistID = artSize + 1;
                                tempA.toSQL();
                            }
                            hashArtist();

                            //Add album to album table
                            String tempAlb = info[2];
                            if(albumMap.containsKey(tempAlb)){
                                temp.getAlbum().setID(albumMap.get(tempAlb).albumID);
                            } else{
                                Album tempAl = new Album(tempAlb);
                                //add to sql
                                int albSize = albumMap.size();
                                tempAl.albumID = albSize + 1;
                                tempAl.toSQL();
                            }
                            hashAlbum();

                            System.out.println("Song data added from MusicBrainz");
                            temp.toSQL();
                        }

                    }
                    //cs4.close();
                    loadLibrary();
                    break;


                case 5:
                    //Delete song from library
                    //Scanner cs5 = new Scanner(System.in);
                    System.out.println("What is the name of the song you would like to delete?");
                    String delSong = input.nextLine();
                    if(l1.containsSong(delSong)){
                        String delID = String.valueOf(l1.getSong(delSong).songID);
                        String insertString = "delete from songs where id = " + delID + ";";
                        Connection connectionS = null;
                        try {
                            // create a database connection
                            connectionS = DriverManager.getConnection("jdbc:sqlite:music.db");
                            Statement statement = connectionS.createStatement();
                            statement.setQueryTimeout(30);  // set timeout to 30 sec.

                            statement.executeUpdate(insertString);
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
                            break;
                        }

                    } else {
                        System.out.println("That song is not in the library!");
                    }
                    //cs5.close();
                    loadLibrary();
                    break;


                case 6:
                    //Add artist
                    System.out.println("What is the name of the artist?");
                    String artName = input.nextLine();
                    if(artistMap.containsKey(artName)){
                        System.out.println("That artist is already in the library!!");
                    } else{
                        Artist temp = new Artist(MusicBrainz.artistMB(artName));
                        //add to sql
                        int artSize = artistMap.size();
                        temp.artistID = artSize + 1;
                        temp.toSQL();
                    }
                    hashArtist();
                    break;

                case 7:
                    //Add album
                    System.out.println("What is the name of the album?");
                    String albName = input.nextLine();
                    if(albumMap.containsKey(albName)){
                        System.out.println("That album is already in the library!!");
                    } else{
                        Album temp = new Album(MusicBrainz.albumMB(albName));
                        //add to sql
                        int albSize = albumMap.size();
                        temp.albumID = albSize + 1;
                        temp.toSQL();
                    }
                    hashAlbum();
                    break;

                case 8:
                    //Create Playlist
                    System.out.println("What is the name of the artist you would like to create a playlist with?");
                    System.out.println("Type \"artist\"");
                    String artPlay = input.nextLine();
                    if(artistMap.containsKey(artPlay)){
                        for(Song song: l1.librarySongs){
                            if (song.performer.name.equals(artPlay)){
                                p1.addSong(song);
                            }
                        }
                        System.out.println("Playlist created!!\n");
                    } else{
                        System.out.println("That artist is not in the library!!");
                    }
                    break;

                case 9:
                    //List songs in playlist
                    if(p1.listOfSongs.size() > 0){
                        String playArt = p1.listOfSongs.get(0).performer.name;
                        System.out.printf("Here are all the songs by %s: \n", playArt);
                        for(Song song: p1.listOfSongs){
                            System.out.println(song.name);
                        }
                        System.out.println();
                    } else {
                        System.out.println("Create a playlist first!!");
                    }
                    break;

                case 10:
                    //Add song to playlist
                    System.out.println("What is the name of the song you would like to add to the playlist?");
                    String sPlay = input.nextLine();
                    if (l1.containsSong(sPlay)){
                        p1.addSong(l1.getSong(sPlay));
                        break;
                    } else{
                        System.out.println("That song needs to be added to the library first!!");
                    }
                    break;

                case 11:
                    //Delete song from playlist
                    System.out.println("What is the name of the song you would like to remove from the playlist?");
                    String pDel = input.nextLine();
                    if(l1.containsSong(pDel)){
                        Song temp = l1.getSong(pDel);
                        if(p1.listOfSongs.contains(temp)){
                            p1.deleteSong(temp);
                            break;
                        } else{
                            System.out.println("That song is not in the playlist!!");
                            break;
                        }
                    } else {
                        System.out.println("That song is not in the library!!");
                    }
                    break;

                case 12:
                    //Playlist to XML
                    if(p1.listOfSongs.size() > 0){
                        String xmlText = p1.toXML();
                        File output = new File("playlist.xml");
                        PrintWriter out = new PrintWriter(output);
                        out.println(xmlText);
                        System.out.println("Playlist XML created!!\n");
                        out.close();
                    } else{
                        System.out.println("The playlist is empty!!");
                    }
                    break;

                case 13:
                    //Write library to xml
                    String libXMLText = l1.toXML();
                    File outputLib = new File("library.xml");
                    PrintWriter out2 = new PrintWriter(outputLib);
                    out2.println(libXMLText);
                    System.out.println("Library XML created!!\n");
                    out2.close();
                    break;

                case 14:
                    //Terminate
                    System.out.println("Thank you!");
                    condition = false;
                    break;
            }
        }
    }

}
