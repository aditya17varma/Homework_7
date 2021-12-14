package edu.usfca;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArtistTest {
    Artist a1;

    @BeforeEach
    void setUp() {
        a1 = new Artist("Judas Priest");
        a1.setID(4);

    }

    @Test
    void toSQL() {
        a1.toSQL();
    }

    @Test
    void fromSQL() {
        a1.fromSQL();
    }
}