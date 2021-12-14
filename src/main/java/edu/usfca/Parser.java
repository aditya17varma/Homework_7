package edu.usfca;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Parser {
    XMLParser xmlP;
    static Playlist p1 = new Playlist();
    static Playlist p2 = new Playlist();
    static Playlist mergedPlaylist = new Playlist();
    static HashMap<String, Playlist> playlistMap = new HashMap<>();
    static HashMap<String, Artist> artistMap = new HashMap<>();
    static HashMap<String, Album> albumMap = new HashMap<>();

    public Parser() {
        xmlP = new XMLParser();
    }

    public void findPlaylist(String pname){
        if (playlistMap.containsKey(pname)){


        }
    }

    public void parseXML(String filename){
        xmlP.parse(filename);
    }


    public static void main(String[] args) throws FileNotFoundException {
        Parser p = new Parser();
        Scanner input = new Scanner(System.in);
        boolean condition = true;

        System.out.println("Greetings!!");
        System.out.println("The songs from \"music.db\" have been loaded into the library.");
        String filename = input.nextLine();
        if (filename.contains(".xml")) {
            p.parseXML(filename);
            System.out.println("The songs in the file have been loaded into a library!");
        } else {
            System.out.println("Please input a valid filename!");
        }


        while (condition) {
            System.out.println("Please select an option:");
            System.out.println(
                    "1. List songs in library.\n" +
                    "2. List songs in playlist.\n" +
                    "3. Add a song to a playlist.\n" +
                    "4. Merge two playlists.\n" +
                    "5. Delete a song from playlist.\n" +
                    "6. Sort a playlist with liked songs in front.\n" +
                    "7. Shuffle the playlist.\n" +
                    "8. Create a playlist of songs with a certain vibe.\n" +
                    "9. Write the playlist out to a XML file.\n" +
                    "10. Write the library out to a XML file.\n" +
                    "11. Stop the program!");
            int selection = 0;
            selection = input.nextInt();

            switch (selection) {

                case 1:
                    //songs in library
                        for(Song s: p.xmlP.xmlLibrary.librarySongs){
                            System.out.println(s.name);
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





}
