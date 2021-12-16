package edu.usfca;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;

/**
 * This class allows you to fetch data about the Song, Artist or Ablum from MusicBrainz.db
 */
public class MusicBrainz {

    /**
     * This method is used to access the data contained within child nodes in the XML file
     * @param n The node you want to access.
     * @return The value of the Node as a String.
     */
    public static String getContent(Node n) {
        StringBuilder sb = new StringBuilder();
        Node child = n.getFirstChild();
        sb.append(child.getNodeValue());
        return sb.toString();
    }

    /**
     * For a given artist name, this method queries MusicBrainz to access the XML file associated with the artist name.
     * If a query returns multiple hits, the method assumes the first artist in the list is the intended target.
     * @param artistSearch The name of the artist to be searched.
     * @return The name of the artist as contained in MusicBrainz.
     */
    public static String artistMB(String artistSearch) {

        String search = artistSearch.replaceAll(" ", "+");

        String returnArtist = "";
        String initialURL = "https://musicbrainz.org/ws/2/artist?query=" + search + "&fmt=xml";
        /* MusicBrainz gives each element in their DB a unique ID, called an MBID. We'll use this to fecth that. */

        /* now let's parse the XML.  */
        Node subNode;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection u = new URL(initialURL).openConnection();
            /* MusicBrainz asks to have a user agent string set. This way they can contact you if there's an
             * issue, and they won't block your IP. */
            u.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (avkunatharaju@dons.usfca.edu");

            Document doc = db.parse(u.getInputStream());
            /* let's get the artist-list node */
            NodeList artists = doc.getElementsByTagName("artist-list");
            /* let's assume that the one we want is first. */
            Node artistNode = artists.item(0).getFirstChild();
            Node artistIDNode = artistNode.getAttributes().getNamedItem("id");
            NodeList artistNodes = artistNode.getChildNodes();

            returnArtist = getContent(artistNode.getFirstChild());
            //System.out.println(returnArtist);

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
        return returnArtist;
    }

    /**
     * For a given album name, this method queries MusicBrainz to access the XML file associated with the album name.
     * If a query returns multiple hits, the method assumes the first album in the list is the intended target.
     * @param albumSearch The name of the album to be searched.
     * @return The name of the album as contained in MusicBrainz.
     */
    public static String albumMB(String albumSearch) {

        String returnAlbum = "";
        String artistN = "";
        String search = albumSearch.replaceAll(" ", "+");
        String initialURL = "https://musicbrainz.org/ws/2/release-group/?query=album+AND+" + search;

        Node subNode;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection u = new URL(initialURL).openConnection();
            /* MusicBrainz asks to have a user agent string set. This way they can contact you if there's an
             * issue, and they won't block your IP. */
            u.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (avkunatharaju@dons.usfca.edu");

            Document doc = db.parse(u.getInputStream());
            /* let's get the artist-list node */
            NodeList albums = doc.getElementsByTagName("release-group-list");
            /* let's assume that the one we want is first. */
            Node albumNode = albums.item(0).getFirstChild();
            NodeList albumNodes = albumNode.getChildNodes();

            returnAlbum = getContent(albumNode.getFirstChild());
            System.out.println(returnAlbum);

            for(int i = 0; i < albumNodes.getLength(); i++){
                subNode = albumNodes.item(i);
                if(subNode.getNodeName().equals("artist-credit")){
                    artistN = getContent(subNode.getFirstChild().getFirstChild());
                }
            }
            System.out.println(artistN);

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
        return returnAlbum;
    }

    /**
     * For a given song name, this method queries MusicBrainz to access the XML file associated with the song name.
     * If a query returns multiple hits, the method assumes the first song in the list is the intended target.
     * The method returns information on the name of the Songs, the performing Artist, and the Album it appears in.
     * @param songSearch The name of the song to be searched.
     * @return An Array of Strings that contains the Song name, Artist name, and Album name.
     */
    public static String[] songMB(String songSearch) {

        String[] returnList = new String[3];
        String returnSong = "";
        String artistS = "";
        String albumS = "";
        String search = songSearch.replaceAll(" ", "+");
        String initialURL = "https://musicbrainz.org/ws/2/recording/?query=" + search;

        Node subNode;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            URLConnection u = new URL(initialURL).openConnection();
            /* MusicBrainz asks to have a user agent string set. This way they can contact you if there's an
             * issue, and they won't block your IP. */
            u.setRequestProperty("User-Agent", "Application ExampleParser/1.0 (avkunatharaju@dons.usfca.edu");

            Document doc = db.parse(u.getInputStream());
            /* let's get the artist-list node */
            NodeList songs = doc.getElementsByTagName("recording-list");
            /* let's assume that the one we want is first. */
            Node songNode = songs.item(0).getFirstChild();
            NodeList songNodes = songNode.getChildNodes();

            returnSong = getContent(songNode.getFirstChild());
            //System.out.println(returnSong);
            returnList[0] = returnSong;

            for(int i = 0; i < songNodes.getLength(); i++){
                subNode = songNodes.item(i);
                if(subNode.getNodeName().equals("artist-credit")){
                    artistS = getContent(subNode.getFirstChild().getFirstChild());
                }
            }
            returnList[1] = artistS;
            //System.out.println(artistS);

            for(int j = 0; j < songNodes.getLength(); j++){
                subNode = songNodes.item(j);
                if(subNode.getNodeName().equals("release-list")){
                    albumS = getContent(subNode.getFirstChild().getFirstChild());
                }
            }
            //System.out.println(albumS);
            returnList[2] = albumS;

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
        //returns [songName, artistName, albumName]
        return returnList;

    }

//    public static void main(String[] args){
//        //artistMB("judas priest");
//        //albumMB("joshua tree");
//        songMB("Where the streets have no name");
//
//    }


}
