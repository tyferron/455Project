package csci455.project.chatroom.server.models;

public class User {
    private String userName;
    private String password;
    private boolean joinedRoom;

    public User(String userName, String password, boolean joinedRoom)
    {

    }

    public String getUserName()
    {
        return userName;
    }

    public String getPassword()
    {
        return password;
    }

    public boolean isJoinedRoom()
    {
        return joinedRoom;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setisJoinedRoom(boolean joinedRoom)
    {
        this.joinedRoom = joinedRoom;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof User))
        {
            return false;
        }
        User other = (User)obj;
        return joinedRoom == other.joinedRoom && password.equals(other.password) &&
            userName.equals(other.password);
    }

    @Override
    public int hashCode()
    {
        String temp = userName + password + joinedRoom;
        int sum = 0;
        for (char c : temp.toCharArray())
        {
            sum += c;
        }
        return sum;
    }

    @Override
    public String toString()
    {
        return "User Name: " + userName + ", Password: " + password + ", Joined Room: " + joinedRoom;
    }
}
