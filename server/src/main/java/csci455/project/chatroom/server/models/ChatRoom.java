package csci455.project.chatroom.server.models;

public class ChatRoom 
{
    private String password;
    private String messageHistory;

    public ChatRoom(String password, String messageHistory)
    {
        setPassword(password);
        this.messageHistory = messageHistory;
    }

    public String getPassowrd()
    {
        return password;
    }

    public String getMessageHistory()
    {
        return messageHistory;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setMessageHistory(String messageHistory)
    {
        this.messageHistory = messageHistory;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof ChatRoom))
        {
            return false;
        }
        ChatRoom other = (ChatRoom) obj;
        return password.equals(other.password) && messageHistory.equals(other.messageHistory);
    }
    
    @Override
    public int hashCode()
    {
        int sum = 0;
        for (char c : password.toCharArray())
        {
            sum += c;
        }
        for (char c : messageHistory.toCharArray())
        {
            sum += c;
        }
        return sum;
    }

    @Override
    public String toString()
    {
        return "Passowrd: " + password + ", Message History: " + messageHistory;
    }
}
