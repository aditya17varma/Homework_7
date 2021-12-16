package edu.usfca;

import java.util.Date;

/**
 * This Entity class acts as the Parent-class for Song, Artist, Album.
 *
 */
public class Entity {
    protected String name;
    protected static int counter = 0;
    protected int entityID;
    protected Date dateCreated;

    /**
     * Constructor for Entity.
     */
    public Entity() {
        this.name = "";
        counter++;
        this.entityID = counter;
        dateCreated = new Date();
    }

    /**
     * This method checks whether one Entity is equal to another.
     * @param otherEntity The Entity to be compared to.
     * @return Returns a boolean whether equal or not.
     */
    public boolean equals(Entity otherEntity) {
        return entityID == otherEntity.entityID;
    }


    /**
     * Constructor for entity for with a given name.
     * @param name The name for the Entity
     */
    public Entity(String name) {
        this.name = name;
        counter++;
        this.entityID = counter;
        dateCreated = new Date();
    }

    /**
     * Fetches the name of the Entity.
     * @return Returns the name of the Entity.
     */
    public String getName() {
        return name;
    }

    /**
     * Allows you to set the name of the Entity.
     * @param name The name you want to set for the Entity.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method provides a String containing the name and entityID.
     * @return String with name and entityID.
     */
    public String toString() {
        return "Name: " + this.name + " Entity ID: " + this.entityID;
    }

    /**
     * This method provides a String in the XML format
     * @return Returns a String in XML format with the name and entityID.
     */
    public String toXML() {
        return "<entity><name>" + this.name + "</name><ID> " + this.entityID + "</ID></entity>";
    }
}