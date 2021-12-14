package edu.usfca;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;

public class MusicBrainz {

    public static String getContent(Node n) {
        StringBuilder sb = new StringBuilder();
        Node child = n.getFirstChild();
        sb.append(child.getNodeValue());
        return sb.toString();
    }

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
            System.out.println(returnArtist);

        } catch (Exception ex) {
            System.out.println("XML parsing error" + ex);
        }
        return returnArtist;
    }

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

    public static String songMB(String albumSearch) {

        String returnSong = "";
        String artistS = "";
        String albumS
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



    public static void main(String[] args){
        //artistMB("judas priest");
        albumMB("joshua tree");
    }


}
