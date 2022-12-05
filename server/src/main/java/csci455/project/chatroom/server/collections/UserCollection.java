package csci455.project.chatroom.server.collections;
import csci455.project.chatroom.server.Mapper;
import csci455.project.chatroom.server.models.DatabaseCredential;
import csci455.project.chatroom.server.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


public class UserCollection implements Map<Integer, User>
{
    private final DatabaseCredential credential;
    private int count;

    public UserCollection (DatabaseCredential credential)
    {
        this.credential = credential;
        count = 0;
    }

    public void clear()
    {
        Connection connection = Mapper.getConnection(credential);
        String sql = "DELETE FROM public.\"Users\"";
        try
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            count = 0;
        }
        catch (SQLException ex)
        {
            System.out.print("Unable to clear the records.");
        }
    }

    public boolean containsKey(Object key)
    {
        if (!(key instanceof Integer))
        {
            return false;
        }
        Integer id = (Integer)key;
        Connection connection = Mapper.getConnection(credential);
        ResultSet table = loadTable(connection);
        try
        {
            while (table.next())
            {
                if (id.intValue() == table.getInt(1))
                {
                    return true;
                }
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to determine whether the key exists.");
        }
        return false;
    }

    public boolean containsValue(Object value)
    {
        if (value instanceof User)
        {
            User user = (User)value;
            Connection connection = Mapper.getConnection(credential);
            ResultSet table = loadTable(connection);
            try
            {
                while (table.next())
                {
                    User currentUser = new User(table.getInt(1), 
                        table.getString(2), table.getString(3));
                    if (currentUser.equals(user))
                    {
                        return true;
                    }
                }
            }
            catch (SQLException ex)
            {

            }
        }
        return false;
    }

    public Set<Map.Entry<Integer,User>> entrySet()
    {
        Set<Map.Entry<Integer,User>> entries = 
            new TreeSet<Map.Entry<Integer,User>>(new UserEntryComparator());
        Connection connection = Mapper.getConnection(credential);
        ResultSet table = loadTable(connection);
        try
        {
            while (table.next())
            {
                User current = new User(table.getInt(1), table.getString(2),
                     table.getString(3));
                entries.add(current);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to retrieve the set of entries.");
        }
        return entries;
    }

    public User get(Object key)
    {
        if (key instanceof Integer)
        {
            Integer k = (Integer)key;
            Connection connection = Mapper.getConnection(credential);
            ResultSet table = loadTable(connection);
            try
            {
                while (table.next())
                {
                    User current = new User(table.getInt(1), table.getString(2),
                     table.getString(3));
                    if (current.getKey().equals(k))
                    {
                        return current;
                    }
                }
            }
            catch (SQLException ex)
            {
                System.out.println("Unable to retrieve a User.");
            }
        }
        return null;
    }

    public boolean isEmpty()
    {
        return count == 0;
    }

    public Set<Integer> keySet()
    {
        Set<Integer> keys = new TreeSet<Integer>();
        Connection connection = Mapper.getConnection(credential);
        ResultSet table = loadTable(connection);
        try
        {
            while (table.next())
            {
                keys.add(table.getInt(1));
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to load the keys.");
        }
        return keys;
    }

    public User put(Integer key, User value)
    {
        User previous = get(key);
        try
        {
            String sql = previous == null ?
            "INSERT INTO public.\"Users\" (\"UserName\", \"Password\") VALUES " +
            "('" + value.getUserName() + "', '" + value.getPassword() + "');" :
            "UPDATE public.\"Users\" SET \"UserName\" = " + value.getUserName() + ", \"Password\" = " +
            value.getPassword() + " WHERE \"UserID\" = " + value.getKey() + ";";
            Connection connection = Mapper.getConnection(credential);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            if (previous == null)
            {
                count++;
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to apply the value in table.");
        }
        return previous;
    }

    public void putAll(Map<? extends Integer, ? extends User> m)
    {
        for (Map.Entry<? extends Integer, ? extends User> entry : m.entrySet())
        {
            put(entry.getKey(), entry.getValue());
        }
    }

    public User remove(Object key)
    {
        User previous = get(key);
        String sql = "DELETE FROM public.\"Users\" WHERE \"UserID\" = " + previous.getKey() + ";";
        Connection connection = Mapper.getConnection(credential);
        try
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to remove the key");
        }
        return previous;
    }

    public int size()
    {
        return count;
    }

    public Collection<User> values()
    {
        Collection<User> users = new TreeSet<User>();
        for (Map.Entry<Integer,User> entry : entrySet())
        {
            users.add(entry.getValue());
        }
        return users;
    }

    private ResultSet loadTable(Connection connection)
    {
        String sql = "SELECT * FROM public.\"Users\"";
        try
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            return statement.executeQuery();
        }
        catch (SQLException ex)
        {
            System.out.println("Unable to load the Users Table.");
            return null;
        }
    }
    private class UserEntryComparator implements Comparator<Map.Entry<Integer, User>>
    {
        public int compare(Map.Entry<Integer,User> user1, Map.Entry<Integer,User> user2)
        {
            return user1.getValue().compareTo(user2.getValue());
        }
    }


}
