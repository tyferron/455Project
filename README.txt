NOTE: This project requires Java 1.8 or later installed on the server and client computers to run.

Table of Contents
* How to run the project (Overview)
* Setting up the server
* Running the server
* Setting up the client
* Running the client
* Using the client



How to run the project (Overview):

One server must be hosted. For this one server, as many clients can connect as the server computer can handle. Currently tested maximum is 4 (each from different networks), though it can likely far exceed that number.

Each person desiring to access a chatroom must then run the client, supplying it with the server's address.



Setting up the server:

1. Download the server jar file. The current version is server-v0.2.jar
1A. Alternatively, you could download the source code and launch a server from there. This option will not be described in following steps as it assumes you to be proficient with programming and networking.
2. Download the database.sql file

Note: steps 3-6 are external to our project, and as such are not covered as in-depth. If you have trouble with these steps, you will be able to find many tutorials available online for the installation of PostgreSQL server and creation of a database in pgAdmin

Note2: DB name is assumed to be ChatroomDB, port 5432, username postgres, and password postgres
These can be 

3. Download and install PostgreSQL Server - INCLUDE pgAdmin, this can be found at https://www.postgresql.org/    You do not need anything from StackBuilder
4. Open pgAdmin
5. Create a database using the server connection created during the installation of PostgreSQL (likely port 5432)
6. Open the Query Tool for the newly created database.

7. Paste the contents of database.sql into the Query Tool and run the query.
8. If you are expecting clients from outside of your network, you will need to forward the port which you intend on using for the server. As this is a general networking and not specific to our project I will not explain the process, though you can find many tutorials online if needed.



Running the server:
1. Open a command line in the folder where you saved the server jar (right click in the folder "open terminal here", or open a terminal and navigate there with the "cd" command)
2. Run the command "java -jar server-v<ver>.jar [server_port] <DBport> <DBName> <DBUsername> <DBPassword>" where <ver> is the version number (0.2 when this file was written), [server_port] is a required argument specifying the port on which to host the server, and the following arguments relate to the database. These are optional, but require *all* preceding fields to be used. The default DBport is 5432, DBName is ChatroomDB, DBUsername is postgres, and DBPassword is postgres.
3. Leave the terminal open for the duration of when you want the server to be hosted. The server can be closed by terminating the process (Ctrl+C in terminal), or by closing the terminal window. User accounts, chatrooms, message histories, etc, are all saved between different times of you running the server in the previously established local database.



Setting up the client:
1. Download the client jar



Running the client:
PREREQ: a running server to which you know the relevant IP. If you need the local IP (client on same network as server), run "ipconfig" in the terminal of the server computer. If you need the public IP (any other network), access it through your favorite method (such as https://whatsmyip.org) on the server computer.

1. Open a command line in the folder where you saved the clientjar (right click in the folder "open terminal here", or open a terminal and navigate there with the "cd" command)
2. Run the command "java -jar client-v<ver>.jar [server_ip] [server_port]" where <ver> is the version number (0.2.3 when this file was written), [server_ip] is the IP address of the server computer, and [server_port] is the port on which the server is hosted.
3. Leave the terminal open for the duration of using the client. Closing the terminal or terminating the process will close the client.



Using the client:
All uses of the client from this point on should be intuitive with the exception of room switching/joining not being available outside of creating a room (which cannot be joined by other users) If guidance is needed for any process:

Note: Feedback is frequently lacking, such as during account creation. If you are not sure why something did not process as intended, consult the restrictions listed in the appropriate description below.

Note: Closing *any* client window with the X will close the entire client (internal close buttons will close only the intended window).

* Creating an account - can only be done prior to logging in, the account must have a username, password, and matching confirmPassword or it will be rejected. Additionally, a username must be unique to the database, or the account will not be created. After creating an account you are automatically logged in.
* Logging in - Must use a previously created account, there are no pre-populated accounts, though accounts created in previous server sessions may be used. A username and password must be provided.
* Sending a message - enter your desired message in the textfield at the bottom of the screen, then press send. If the message is longer than the textbox, or contains one or more newline characters, it will not be properly displayed. Furthermore, any text after a newline character will not even be stored in the database.
* Obtaining a desired message color - create new accounts until you find one with the desired color. Color is generated based on your username, and is not customizable. This helps prevent impersonation and improves users abilities to distinguish messages from other users while the profile picture option is not implemented
* Identifying a user - in addition to a user's chat color, a user may be identified by the location of their profile picture (left is current user, right is all other users), as well as by hovering over their profile picture which will display that user's username.
* Creating a chatroom - Requires a name (not unique). Passwords are optional. If a password is provided, a confirmPassword must match it. If a confirmPassword is provided, a password must match it.
* Switching rooms - Unimplemented
* View joined rooms - Unimplemented
* Join an existing room - Unimplemented
* The I'm a room button - A dummy implementation of the room list for switching rooms, though you still cannot use it to join a new room