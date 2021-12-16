package edu.usfca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArtistTest {
    Artist a1;

    @BeforeEach
    void setUp() {
        a1 = new Artist("Judas Priest");
        a1.setID(4);

    }


    @Test
    void fromSQL() {
        List<Artist> artTest = a1.fromSQL();
        for (Artist art: artTest){
            System.out.println("Artist name: " + art.name + " ID: " + art.artistID);
        }
    }
}