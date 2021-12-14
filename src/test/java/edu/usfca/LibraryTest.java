package edu.usfca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LibraryTest {
    Library l1, l2;
    Song s1, s2, s3, s4, s5, s6, s7, s8;

    @BeforeEach
    void setUp() {
        l1 = new Library();
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

        s1.setID(1);
        s1.setPerformer(new Artist("Taylor Swift"));
        s1.performer.setID(10);
        s1.setAlbum(new Album("Red"));
        s1.album.setID(101);
        l1.addSongs(s1);
        l1.addSongs(s2);
        l1.addSongs(s3);
        l1.addSongs(s4);
        l1.addSongs(s5);
        l1.addSongs(s6);

    }

    @Test
    void toXML() {
        String testXML = l1.toXML();
        System.out.println(testXML);
    }

}