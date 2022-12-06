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
public class RoomOfUsers implements Closeable, Set<User>
{
    private final Connection connection;
    private final int roomID;

    public RoomOfUsers(DatabaseCredential credential, int roomID)
    {
        connection = Mapper.getConnection(credential);
        this.roomID = roomID; 
    }

    public boolean add(User user)
    {
        if (contains(user))
        {
            return false;
        }
        String sql = "INSERT INTO public.\"UserToChatrooms\" (\"UserID\",\"RoomID\") " + 
        "VALUES (" + user.getKey() + ", " + roomID + ");";
        Mapper.resultOf(connection, sql);
        return true;
    }

    public boolean addAll(Collection<? extends User> users)
    {
        for (User u : users)
        {
            if (!add(u))
            {
                return false;
            }
        }
        return true;
    }

    public void clear()
    {
        String sql = "DELETE FROM public.\"UserToChatrooms\" WHERE \"RoomID\" = " + roomID + ";";
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
        if (!(o instanceof User))
        {
            return false;
        }
        User user = (User)o;
        String sql = "SELECT * FROM \"UserToChatrooms\" WHERE \"UserID\" = " + user.getKey() + 
            " AND \"RoomID\" = " + roomID + ";";
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

    public Iterator<User> iterator()
    {
        String sql = "SELECT * FROM \"UserToChatrooms\" WHERE \"RoomID = " + roomID + ";";
        return new UserIterator(Mapper.resultOf(connection, sql));
    }

    public boolean remove(Object o)
    {
        if (!(o instanceof User))
        {
            return false;
        }
        User user = (User)o;
        String sql = "DELETE FROM public.\"UserToChatrooms\" WHERE \"UserID\" = " + user.getKey() + 
        " AND \"RoomID\" = " + roomID + ";";
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
        for (User user : this)
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
        String sql = "SELECT COUNT(\"UserID\") FROM public.\"UserToChatrooms\";";
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
        for (User user : this)
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
        for (User user : this)
        {
            users.add(user);
        }
        return (T[])users.toArray(); 
    }

    private class UserIterator implements Iterator<User>
    {
        private ResultSet users;

        public UserIterator(ResultSet users)
        {
            this.users = users;
        }

        public boolean hasNext()
        {
            try
            {
                return users.next();
            }
            catch (SQLException ex)
            {
                System.out.println("Unable to load users.");
                ex.printStackTrace();
                return false;
            }
        }

        public User next()
        {
            try
            {
                return new User(users.getInt(1), users.getString(2), users.getString(3));
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
