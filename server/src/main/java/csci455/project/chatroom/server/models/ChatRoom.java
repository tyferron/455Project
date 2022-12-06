package csci455.project.chatroom.server.models;
import java.util.Map;
import java.util.Random;

import csci455.project.chatroom.server.Mapper;

public class ChatRoom implements Map.Entry<Integer, ChatRoom>
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

    public static ChatRoom generate()
    {
        Random random = new Random();
        int roomId = random.nextInt(Integer.MAX_VALUE) + 1;
        String roomName = Mapper.generateString(random, 20);
        String password = Mapper.generateString(random, 20);
        String messageHistory = Mapper.generateString(random, 40);
        return new ChatRoom(roomId, roomName, password, messageHistory);
    }

    public Integer getKey()
    {
        return roomId;
    }

    public String getMessageHistory()
    {
        return messageHistory;
    }

    public String getPassword()
    {
        return password;
    }

    public String getRoomName()
    {
        return roomName;
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
