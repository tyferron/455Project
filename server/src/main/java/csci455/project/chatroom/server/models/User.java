package csci455.project.chatroom.server.models;
import java.util.Map;

public class User implements Comparable<User>, Map.Entry<Integer, User> 
{
    private final int userId;
    private String userName;
    private String password;

    public User(int userId, String userName, String password)
    {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public int compareTo(User other)
    {
        return userName.compareTo(other.userName);
    }
   
    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof User))
        {
            return false;
        }
        User other = (User)obj;
        return password.equals(other.password) && userName.equals(other.userName);
    }

    public Integer getKey() 
    {
        return userId;
    }

    public User getValue() 
    {
        return this;
    }

    @Override
    public int hashCode()
    {
        String temp = userName + password;
        int sum = 0;
        for (char c : temp.toCharArray())
        {
            sum += c;
        }
        return sum;
    }

    public User setValue(User value) 
    {
        User previous = getValue();
        this.userName = value.userName;
        this.password = value.password;
        return previous;
    }

    @Override
    public String toString()
    {
        return "User Name: " + userName + ", Password: " + password;
    }
}
