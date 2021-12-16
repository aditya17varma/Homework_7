Homework #7. Putting it all together.

This program is run through the Shell class.
Running the main method in Shell starts a simple user-interface in the terminal,
through which the user can access the multiple features of the program.

The program loads all the songs in the songs table in the music.db database into a Library class. 
Relevant data from the artist and album tables in database is used to fill out the instance variables of the Song object.
Any addition or deletion of Songs is reflected in the songs table in the database.
The Library is re-loaded after each addition or deletion of Songs to accurately reflect the database.
Since the Library reflects the data in the database, users can add Artists and Albums to the database independent of Songs.

The user can also create a Playlist that contains all the Songs in the Library by a single Artist.
The interface in Shell allows the user to choose which Artist to create the Playlist on.

If the user so desires, the Library and the Playlist can be written out as an XML file.



