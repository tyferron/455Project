package csci455.project.chatroom.server.models;

public class DatabaseCredential 
{
    private final int portNumber;
    private final String databaseName;
    private final String userName;
    private final String password;

    public DatabaseCredential(int portNumber, String databaseName, String userName, String password)
    {
        this.portNumber = portNumber;
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
    }

    public int getPortNumber()
    {
        return portNumber;
    }

    public String getDatabaseName()
    {
        return databaseName;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }
}
