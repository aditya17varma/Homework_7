package edu.usfca;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Parser {
    XMLParser xmlP;
    static Library l1;
    static Playlist p1 = new Playlist();
    static Playlist p2 = new Playlist();
    static Playlist mergedPlaylist = new Playlist();
    static HashMap<String, Playlist> playlistMap = new HashMap<>();
    static HashMap<String, Artist> artistMap = new HashMap<>();
    static HashMap<String, Album> albumMap = new HashMap<>();

    public Parser() {
        xmlP = new XMLParser();
    }


    private static void loadLibrary(){
        List<Song> importSongs = new Song().fromSQL();
        for (Song song: importSongs){
            l1.addSongs(song);
        }
    }

    private static void hashArtist(){
        List<Artist> importArtists = new Artist().fromSQL();
        for (Artist artist: importArtists){
            artistMap.put(artist.name, artist);
        }
    }

    private static void hashAlbum(){
        List<Album> importAlbums = new Album().fromSQL();
        for (Album album: importAlbums){
            albumMap.put(album.name, album);
        }
    }

//    public void parseXML(String filename){
//        xmlP.parse(filename);
//    }


    public static void main(String[] args) throws FileNotFoundException {
        Parser p = new Parser();
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

                    "8. Create a playlist.\n" +
                    "9. List songs in a playlist\n" +
                    "10. Add a song to a playlist.\n" +
                    "11. Delete a song from playlist.\n" +
                    "12. Create a playlist of songs with a certain artist.\n" +

                    "13. Write the playlist out to a XML file.\n" +
                    "14. Write the library out to a XML file.\n" +
                    "15. Stop the program!");

            int selection = 0;
            selection = input.nextInt();

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

                case 3:
                    //List all albums in library
                    for(String key: albumMap.keySet()){
                        System.out.println(key);
                    }

                case 4:
                    //Add song to library
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
                            } else {
                                Artist tempArt = new Artist(songArt);
                                int artSize = artistMap.size();
                                tempArt.artistID = artSize + 1;
                            }
                        } else {
                            String[] info = MusicBrainz.songMB(songName);
                            temp.getPerformer().setName(info[1]);
                            temp.getAlbum().setName(info[2]);
                        }
                        temp.toSQL();
                    }
                    loadLibrary();

                    break;


                case 5:
                    //Delete song from library
                    System.out.println("What is the name of the song you would like to delete?");
                    String delSong = input.nextLine();
                    if(l1.containsSong(delSong)){
                        String insertString = "delete from songs where name = " + delSong + ";";
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
                    break;



                case 2:
                    //songs in playlist
                    System.out.println("Which playlist would you like to see? Type \"Playlist p1\" or \"Playlist p2\".");
                    Scanner playS = new Scanner(System.in);
                    String case2Input = playS.nextLine();
                    if (case2Input.equals("Playlist p1")){
                        ArrayList<Song> p1Songs = p1.getPlaylistSongs();
                        for(Song p1Song: p1Songs){
                            System.out.println(p1Song.name);
                        }
                    } else if (case2Input.equals("Playlist p2")){
                        ArrayList<Song> p2Songs = p1.getPlaylistSongs();
                        for(Song p2Song: p2Songs){
                            System.out.println(p2Song.name);
                        }
                    } else {
                        System.out.println("Choose the correct playlist");
                    }
                    playS.close();
                    break;

                case 3:
                    //Add song to playlist
                    System.out.println("What is the name of the song?");
                    Scanner in1 = new Scanner(System.in);
                    String songName = in1.nextLine();
                    System.out.println("Type \"p1\" or \"p2\" to choose which playlist to add to.");
                    String case3Input = in1.nextLine();
                    if (case3Input.equals("p1")){
                        try{
                            if (filename.contains(".xml")) {
                                p1.addSong(p.xmlP.xmlLibrary.getSong(songName));
                                System.out.println("Song has been added to Playlist p1.");
                            }
                        } catch (InputMismatchException e){
                            System.out.println("Song not in library");
                        }
                    } else if(case3Input.equals("p2")){
                        try{
                            if (filename.contains(".xml")) {
                                p2.addSong(p.xmlP.xmlLibrary.getSong(songName));
                                System.out.println("Song has been added to Playlist p2.");
                            }
                        } catch (InputMismatchException e){
                            System.out.println("Song not in library");
                        }
                    } else {
                        System.out.println("Please type \"p1\" or \"p2\"");
                    }
                    in1.close();
                    break;

                case Playlist:
                    String playlistName = selectPlaylist();
                    if(!playlistMap.containsKey(playlistName)){
                        System.out.println("Wanna add? Type \"yes\"");
                        if (scanner.nextLine.equals("yes")){
                            tempPlaylist = new Playlist();
                            tempPlaylist.addSong();
                            playlistMap.put(playlistName, tempPlaylist);
                            System.out.println(Playlist created!);
                            break;
                        }
                        break;
                    }
                    //if playlist exists
                    playlistMap.get(playlistName).addSong();
                    System.out.println("Song added to playlist");
                    break;


                case 4:
                    //merge two playlist
                    System.out.println("Merge playlist p1 and p2?");
                    mergedPlaylist= p1.mergePlaylist(p2);
                    System.out.println("p1 and p2 have been merged!");
                    System.out.println("The contents of the merged playlist:");
                    for(Song mergedS: mergedPlaylist.listOfSongs){
                        System.out.println(mergedS.name);
                    }
                    break;

                case 5:
                    //Delete song from playlist
                    System.out.println("What is the name of the song you would like to delete from playlist p1?");
                    Scanner in3 = new Scanner(System.in);
                    String delSong = in3.nextLine();
                    if (filename.contains(".xml")) {
                        p1.deleteSong(p.xmlP.xmlLibrary.getSong(delSong));
                        System.out.println("Song has been deleted from Playlist p1.");
                    }
                    in3.close();
                    break;

                case 6:
                    //sort playlist
                    Playlist sorted = p1.sortLikedFirst();
                    System.out.println("Playlist p1 has been sorted with liked songs first.");
                    for (Song song : sorted.listOfSongs) {
                        System.out.println(song.name);
                    }
                    break;

                case 7:
                    //shuffle the playlist
                    Playlist shuffled = p1.shuffle();
                    System.out.println("Playlist p1 has been shuffled.");
                    for (Song song : shuffled.listOfSongs) {
                        System.out.println(song.name);
                    }
                    break;

                case 8:
                    //Create a playlist of songs based on vibe
                    System.out.println("What vibe do you want to create a playlist with?");
                    Scanner inVibe = new Scanner(System.in);
                    String vibe = inVibe.nextLine();
                    try{
                        if (filename.contains(".xml")) {
                            Playlist vibes = p1.shuffleVibe(p.xmlP.xmlLibrary, vibe);
                            for (Song vibeSong : vibes.listOfSongs) {
                                System.out.println(vibeSong.name + " vibe: " + vibeSong.getVibe());
                            }
                        }
                    } catch (NullPointerException e){
                        System.out.println("Vibe not found in library!");
                    }
                    inVibe.close();
                    break;

                case 9:
                    //Write playlist to xml
                    String xmlText = p1.toXML();
                    File output = new File("output.xml");
                    PrintWriter out = new PrintWriter(output);
                    out.println(xmlText);
                    out.close();
                    break;

                case 10:
                    //Write library to xml
                    if (filename.contains(".xml")) {
                        String libXMLText = p.xmlP.xmlLibrary.toXML();
                        File outputLib = new File("output.xml");
                        PrintWriter out2 = new PrintWriter(outputLib);
                        out2.println(libXMLText);
                        out2.close();
                    }
                    break;

                case 11:
                    //Terminate
                    System.out.println("Thank you!");
                    condition = false;
                    break;


                //System.out.println(p.xmlP.xmlLibrary.getSongs());
                //System.out.println(p.jsonP.jsonLibrary.getSongs());


            }
        }
    }

    private static String selectPlaylist(){
        Scanner sSP = new Scanner(System.in);
        System.out.println("Type the name of the playlist you wish to select");
        return sSP.nextLine();
    }





}
