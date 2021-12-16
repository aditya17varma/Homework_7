package edu.usfca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {
    Album al1;

    @BeforeEach
    void setUp() {
        al1 = new Album("Redeemer of Souls");
        al1.setID(100);
    }

    @Test
    void fromSQL() {

        List<Album> alTest = al1.fromSQL();
        for(Album al: alTest){
            System.out.println("Album name: "+ al.name + " ID: " + al.albumID);
        }
    }
}