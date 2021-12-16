package edu.usfca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlaylistTest {
    Playlist p1, p2, p3;
    Song s1, s2, s3, s4, s5, s6;
    Library l1;

    @BeforeEach
    void setUp() {
        p1 = new Playlist();
        s1 = new Song("Shake It Off");
        s1.setVibe("ratchet");
        s2 = new Song("Bad Blood");
        s3 = new Song("22");
        s4 = new Song("You Belong With Me");
        s4.setVibe("ratchet");
        s1.setLiked(true);
        s2.setLiked(false);
        s3.setLiked(false);
        s4.setLiked(true);

        s5 = new Song("Love Story");
        s5.setVibe("ratchet");
        s6 = new Song("Wildest Dreams");
    }

    @Test
    void addSong() {
        p1.addSong(s1);
        p1.addSong(s2);
        assertTrue(p1.listOfSongs.contains(s1));
        assertFalse(p1.listOfSongs.contains(s2));
    }

    @Test
    void deleteSong() {
        p1.addSong(s1);
        assertTrue(p1.listOfSongs.contains(s1));
        p1.deleteSong(s1);
        assertFalse(p1.listOfSongs.contains(s1));
    }

    @Test
    void mergePlaylist() {
        p1.addSong(s1);
        p1.addSong(s2);
        p1.addSong(s3);
        p1.addSong(s4);
        p2 = new Playlist();
        p2.addSong(s1);
        p2.addSong(s4);
        p2.addSong(s5);
        p2.addSong(s6);

        p3 = p1.mergePlaylist(p2);
        for (Song song: p3.listOfSongs){
            System.out.println(song.name);
        }



    }

    @Test
    void toXML() {
        s1.setID(1);
        s1.setPerformer(new Artist("Taylor Swift"));
        s1.setAlbum(new Album("Red"));
        p1.addSong(s1);
        p1.addSong(s2);
        p1.addSong(s3);
        p1.addSong(s4);
        p1.addSong(s5);
        p1.addSong(s6);

        String testXML = p1.toXML();
        System.out.println(testXML);



    }
}