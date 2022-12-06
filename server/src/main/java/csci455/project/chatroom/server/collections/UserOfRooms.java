package csci455.project.chatroom.server.collections;
import csci455.project.chatroom.server.Mapper;
import csci455.project.chatroom.server.models.*;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
public class UserOfRooms implements Closeable, Set<ChatRoom> {
    private final Connection connection;
    private final int userID;

    public UserOfRooms(DatabaseCredential credential, int userID)
    {
        connection = Mapper.getConnection(credential);
        this.userID = userID; 
    }

    public boolean add(ChatRoom room)
    {
        if (contains(room))
        {
            return false;
        }
        String sql = "INSERT INTO public.\"UserToChatrooms\" (\"UserID\",\"RoomID\") " + 
        "VALUES (" + userID + ", " + room.getKey() + ");";
        Mapper.resultOf(connection, sql);
        return true;
    }

    public boolean addAll(Collection<? extends ChatRoom> rooms)
    {
        for (ChatRoom room : rooms)
        {
            if (!add(room))
            {
                return false;
            }
        }
        return true;
    }

    public void clear()
    {
        String sql = "DELETE FROM public.\"UserToChatrooms\" WHERE \"UserID\" = " + userID + ";";
        Mapper.resultOf(connection, sql);
    }

    public void close()
    {
        try
        {
            connection.close();
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to closed connection.");
            ex.printStackTrace();
        }
    }

    public boolean contains(Object o)
    {
        if (!(o instanceof ChatRoom))
        {
            return false;
        }
        ChatRoom room = (ChatRoom)o;
        String sql = "SELECT * FROM \"UserToChatrooms\" WHERE \"UserID\" = " + userID + 
            " AND \"RoomID\" = " + room.getKey() + ";";
        try
        {
            ResultSet rs = Mapper.resultOf(connection, sql);
            return rs.next();
        }
        catch (SQLException ex)
        {
            return false;
        }
    }

    public boolean containsAll(Collection<?> users)
    {
        for (Object u : users)
        {
            if (!contains(u))
            {
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty()
    {
        return size() == 0;
    }

    public Iterator<ChatRoom> iterator()
    {
        String sql = "SELECT * FROM \"UserToChatrooms\" WHERE \"UserID = " + userID + ";";
        return new ChatRoomIterator(Mapper.resultOf(connection, sql));
    }

    public boolean remove(Object o)
    {
        if (!(o instanceof ChatRoom))
        {
            return false;
        }
        ChatRoom room = (ChatRoom)o;
        String sql = "DELETE FROM public.\"UserToChatrooms\" WHERE \"UserID\" = " + userID + 
        " AND \"RoomID\" = " + room.getKey() + ";";
        Mapper.resultOf(connection, sql);
        return true;
    }

    public boolean removeAll(Collection<?> c)
    {
        for (Object o : c)
        {
            if (!remove(o))
            {
                return false;
            }
        }
        return true;
    }

    public boolean retainAll(Collection<?> c)
    {
        Collection<Object> tobeRemoved = new HashSet<Object>();
        for (ChatRoom user : this)
        {
            if (!c.contains(user))
            {
                tobeRemoved.add(user);
            }
        }
        return removeAll(tobeRemoved);
    }

    public int size()
    {
        String sql = "SELECT COUNT(\"RoomID\") FROM public.\"UserToChatrooms\";";
        try
        {
            return Mapper.resultOf(connection, sql).getInt(1);
        }
        catch (SQLException ex)
        {
            return 0;
        }
    }

    public Object[] toArray()
    {
        Object[] result = new Object[size()];
        int index = 0;
        for (ChatRoom user : this)
        {
            result[index++] = user;
        }
        return result;
    }
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a)
    {
        Object[] collection = a;
        Set<Object> users = Arrays.stream(collection).distinct().collect(Collectors.toSet());
        for (ChatRoom user : this)
        {
            users.add(user);
        }
        return (T[])users.toArray(); 
    }

    private class ChatRoomIterator implements Iterator<ChatRoom>
    {
        private ResultSet rooms;

        public ChatRoomIterator(ResultSet rooms)
        {
            this.rooms = rooms;
        }

        public boolean hasNext()
        {
            try
            {
                return rooms.next();
            }
            catch (SQLException ex)
            {
                System.out.println("Unable to load users.");
                ex.printStackTrace();
                return false;
            }
        }

        public ChatRoom next()
        {
            try
            {
                return new ChatRoom(rooms.getInt(1), rooms.getString(2), rooms.getString(3), rooms.getString(4));
            }
            catch (SQLException ex)
            {
                System.out.println("Unable to load users.");
                ex.printStackTrace();
                return null;
            }
        }
    }
}
