package edu.usfca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserTest {
    Parser p;
    Library l1;
    Song s1, s2, s3, s4, s5, s6, s7, s8;
    Playlist p1, p2, p3, p4, p5;

    @BeforeEach
    void setUp() {
        p = new Parser();
        p1 = new Playlist();
        p2 = new Playlist();
        p3 = new Playlist();
        p4 = new Playlist();
        p5 = new Playlist();

        l1 = new Library();
        s1 = new Song("Don't Fear the Reaper");
        s1.setPerformer(new Artist("Blue Oyster Cult"));
        s1.setAlbum(new Album("IDK"));
        s2 = new Song("I Knew You Were Trouble");
        s2.setAlbum(new Album("Red"));
        s2.setPerformer(new Artist("Taylor Swift"));
        s3 = new Song("Won't Get Fooled Again");
        s3.setPerformer(new Artist("The Who"));
        s3.setAlbum(new Album("Who's Next"));

        s4 = new Song("i knew you were trouble!!!!!");
        s4.setAlbum(new Album("Red"));
        s4.setPerformer(new Artist("Taylor Swift"));
        s5 = new Song("Won't Get Fooled Again");
        s5.setPerformer(new Artist("The Who"));
        s5.setAlbum(new Album("Who's Next"));

        s6 = new Song("Don't Fear the Reaper");
        s6.setPerformer(new Artist("Blue Oyster Cult"));
        s6.setAlbum(new Album("Agents of Fortune"));

        l1.addSongs(s1);
        l1.addSongs(s2);
        l1.addSongs(s3);
        l1.addSongs(s4);
        l1.addSongs(s5);
        l1.addSongs(s6);

    }

    @Test
    void main() {
        s1.setVibe("ratchet");
        s1.setID(1);
        s1.performer.setID(10);
        s1.album.setID(101);
        s4.setVibe("ratchet");
        s4.setID(2);
        s4.performer.setID(20);
        s4.album.setID(201);
        s2.setLiked(true);
        s3.setLiked(true);
        p1.addSong(s1);
        p2.addSong(s2);
        p1.addSong(s3);
        p2.addSong(s4);

        p3 = p1.mergePlaylist(p2);
        System.out.println("Merged Playlist:");
        for (Song song: p3.listOfSongs){
            System.out.println(song.name);
        }
        System.out.println("");

        p4 = p3.shuffleVibe(l1, "ratchet");
        System.out.println("ratchet playlist:");
        for (Song song: p4.listOfSongs){
            System.out.println(song.name + "; Vibe: " + song.vibe);
        }
        System.out.println("");

        p5 = p3.sortLikedFirst();
        System.out.println("like first:");
        for (Song song: p5.listOfSongs){
            System.out.println(song.name + "; Liked: " + song.liked);
        }
        System.out.println("");

        System.out.println("As xml:");
        String xmlText = p3.toXML();
        System.out.println(xmlText);
        System.out.println("");


    }
}