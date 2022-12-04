package csci455.project.chatroom.server.models;
import java.util.Map;

public class ChatRoom implements Comparable<ChatRoom>, Map.Entry<Integer, ChatRoom>
{
    private final int roomId;
    private String roomName;
    private String password;
    private String messageHistory;

    public ChatRoom(int roomId, String roomName, String password, String messageHistory)
    {
        this.roomId = roomId;
        this.roomName = roomName;
        this.password = password;
        this.messageHistory = messageHistory;
    }

    public int compareTo(ChatRoom other)
    {
        return roomName.compareTo(other.roomName);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof ChatRoom))
        {
            return false;
        }
        ChatRoom other = (ChatRoom) obj;
        return roomName.equals(other.roomName) && password.equals(other.password) && 
            messageHistory.equals(other.messageHistory);
    }

    public Integer getKey()
    {
        return roomId;
    }

    public ChatRoom getValue()
    {
        return this;
    }
    
    @Override
    public int hashCode()
    {
        String temp = roomId + roomName + password + messageHistory;
        int sum = 0;
        for (char c : temp.toCharArray())
        {
            sum += c;
        }
        return sum;
    }

    public ChatRoom setValue(ChatRoom value)
    {
        ChatRoom previous = getValue();
        this.password = value.password;
        this.messageHistory = value.messageHistory;
        return previous;
    }

    @Override
    public String toString()
    {
        return "Passowrd: " + password + ", Message History: " + messageHistory;
    }
}
