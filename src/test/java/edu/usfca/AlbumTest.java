package edu.usfca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlbumTest {
    Album al1;

    @BeforeEach
    void setUp() {
        al1 = new Album("Redeemer of Souls");
        al1.setID(7);
    }

    @Test
    void toSQL() {
        al1.toSQL();
    }

    @Test
    void fromSQL() {
        al1.fromSQL();
    }
}