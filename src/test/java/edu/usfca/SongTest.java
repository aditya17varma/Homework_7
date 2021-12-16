package edu.usfca;

import edu.usfca.Album;
import edu.usfca.Artist;
import edu.usfca.Song;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SongTest {
    Song s1, s2;
    Artist a1;
    Album al1;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        s1 = new Song("You Belong With Me");
        a1 = new Artist("Taylor Swift");
        al1 = new Album("Fearless");

        s1.setID(7);
        a1.setID(3);
        al1.setID(7);
        s1.setPerformer(a1);
        s1.setAlbum(al1);

    }

    @Test
    void fromSQL() {
        List<Song> sTest = s1.fromSQL();
        for(Song song: sTest){
            System.out.println("Song name: " + song.name + " ID: " + song.songID);
        }
    }

    @Test
    void getPerformer() {
        System.out.println(s1.getPerformer().name);
    }
}