package edu.usfca;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLParser {
    Library xmlLibrary = new Library();

    public static String getContent(Node n) {
        StringBuilder sb = new StringBuilder();
        Node child = n.getFirstChild();
        sb.append(child.getNodeValue());
        return sb.toString();
    }


    public Library parse(String fname) {
        String filename = fname;
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            //dbf.setIgnoringElementContentWhitespace(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(filename));

            Element root = doc.getDocumentElement();
            //System.out.println(root);
            NodeList Songs = root.getElementsByTagName("song");
            //System.out.println(Songs.getLength());


            Node currentNode, subNode;

            Song currentSong;

            for (int i = 0; i < Songs.getLength(); i++) {
                currentNode = Songs.item(i);
                /* each of these is a CD node. */
                NodeList children = currentNode.getChildNodes();
                currentSong = new Song();
                currentSong.setID(Integer.parseInt(getContent(currentNode.getAttributes().getNamedItem("id")).trim()));
                //System.out.println(currentSong.getID());
                for (int j = 0; j < children.getLength(); j++) {
                    subNode = children.item(j);
                    if (subNode.getNodeType() == Node.ELEMENT_NODE) {
                        if (subNode.getNodeName().equals("title")) {
                            currentSong.setName(getContent(subNode).trim());
                            //System.out.println(currentSong.getName());
                        } else if (subNode.getNodeName().equals("artist")) {
                            currentSong.setPerformer(new Artist(getContent(subNode).trim()));
                            currentSong.performer.setID(Integer.parseInt(getContent(subNode.getAttributes().getNamedItem("id"))));
                            //System.out.println(currentSong.getPerformer().name);
                            //System.out.println(currentSong.performer.ID);
                        } else if (subNode.getNodeName().equals(("album"))) {
                            currentSong.setAlbum(new Album(getContent(subNode).trim()));
                            currentSong.album.setID(Integer.parseInt(getContent(subNode.getAttributes().getNamedItem("id"))));
                            //System.out.println(currentSong.getAlbum().name);
                            //System.out.println(currentSong.album.ID);
                        }

                    }
                }
                xmlLibrary.addSongs(currentSong);

            }
        } catch (Exception e) {
            System.out.println("Parsing error:" + e);
        }
        return xmlLibrary;
    }

}
