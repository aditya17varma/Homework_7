package edu.usfca;

class XMLParserTest {
    XMLParser xmlP;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        xmlP = new XMLParser();

    }

    @org.junit.jupiter.api.Test
    void parse() {
        xmlP.parse("music-library.xml");
    }
}