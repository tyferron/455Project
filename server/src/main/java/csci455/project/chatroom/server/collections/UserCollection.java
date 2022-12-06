package csci455.project.chatroom.server.collections;

import csci455.project.chatroom.server.Mapper;
import csci455.project.chatroom.server.models.DatabaseCredential;
import csci455.project.chatroom.server.models.User;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class UserCollection implements Closeable, Map<Integer, User> {
    private final Connection connection;

    public UserCollection(DatabaseCredential credential) {
        connection = Mapper.getConnection(credential);
    }

    public void clear() {
        String sql = "DELETE FROM public.\"Users\";";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeQuery();
            connection.commit();
            statement.close();
        } catch (SQLException ex) {
            System.out.println("Unable to clear the records.");
            ex.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Unable to close the collection.");
            ex.printStackTrace();
        }
    }

    public boolean containsKey(Object key) {
        if (!(key instanceof Integer)) {
            return false;
        }
        Integer id = (Integer) key;
        ResultSet result = loadTable("SELECT * FROM public.\"Users\" WHERE \"UserID\" = " + id + ";");
        try {
            return result.next();
        } catch (SQLException ex) {
            System.out.println("Unable to determine whether the key exists.");
            ex.printStackTrace();
        }
        return false;
    }

    public boolean containsValue(Object value) {
        if (value instanceof User) {
            User user = (User) value;
            ResultSet table = loadTable("SELECT * FROM public.\"Users\" WHERE " +
                    "\"UserName\" = '" + user.getUserName() + "' AND \"Password\" = '" + user.getPassword() +
                    "';");
            try {
                return table.next();
            } catch (SQLException ex) {
                System.out.println("Unable to determine if the table contains the value.");
                ex.printStackTrace();
            }
        }
        return false;
    }

    public Set<Map.Entry<Integer, User>> entrySet() {
        Set<Map.Entry<Integer, User>> entries = new TreeSet<Map.Entry<Integer, User>>(new UserEntryComparator());
        ResultSet table = loadTable("SELECT * FROM \"Users\";");
        try {
            while (table.next()) {
                User current = new User(table.getInt(1), table.getString(2),
                        table.getString(3));
                entries.add(current);
            }
            table.close();
        } catch (SQLException ex) {
            System.out.println("Unable to retrieve the set of entries.");
            ex.printStackTrace();
        }
        return entries;
    }

    public User get(Object key) {
        if (key instanceof Integer) {
            Integer id = (Integer) key;
            ResultSet table = loadTable("SELECT * FROM public.\"Users\" WHERE \"UserID\" = " + id + ";");
            try {
                return table.next() ? new User(table.getInt(1), table.getString(2), table.getString(3)) : null;
            } catch (SQLException ex) {
                System.out.println("Unable to retrieve a User.");
                ex.printStackTrace();
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Set<Integer> keySet() {
        Set<Integer> keys = new TreeSet<Integer>();
        ResultSet table = loadTable("SELECT * FROM public.\"Users\";");
        try {
            while (table.next()) {
                keys.add(table.getInt(1));
            }
            table.close();
        } catch (SQLException ex) {
            System.out.println("Unable to load the keys.");
            ex.printStackTrace();
        }
        return keys;
    }

    public User put(Integer key, User value) {
        User previous = get(key);
        try {
            String sql;
            if (previous == null) // Add case
            {
                sql = "INSERT INTO public.\"Users\" (\"UserName\", \"Password\") VALUES ('" +
                        value.getUserName() + "', '" + value.getPassword() + "');";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeQuery();
                statement.close();
                connection.commit();
            } else // Update case
            {
                sql = "UPDATE public.\"Users\" SET \"UserName\" = '" + value.getUserName() + "', " +
                        "\"Password\" = '" + value.getPassword() + "' WHERE \"UserID\" = " + key + ";";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeQuery();
                statement.close();
                connection.commit();
            }
        } catch (SQLException ex) {
            System.out.println("Unable to apply the value in table.");
            ex.printStackTrace();
        }
        return previous;
    }

    public void putAll(Map<? extends Integer, ? extends User> m) {
        for (Map.Entry<? extends Integer, ? extends User> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public User remove(Object key) {
        User previous = get(key);
        if (previous != null)
        {
            String sql = "DELETE FROM public.\"Users\" WHERE \"UserID\" = " + previous.getKey() + ";";
            try 
            {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.execute();
                statement.close();
                connection.commit();
            } 
            catch (SQLException ex) 
            {
                System.out.println("Unable to remove the key");
                ex.printStackTrace();
            }
        }
        return previous;
    }

    public int size() {
        ResultSet result = loadTable("SELECT COUNT(\"UserID\") FROM public.\"Users\";");
        try {
            return result.next() ? result.getInt(1) : 0;
        } catch (SQLException ex) {
            System.out.println("Unable to count Users");
        }
        return 0;
    }

    public Collection<User> values() {
        Collection<User> users = new TreeSet<User>();
        for (Map.Entry<Integer, User> entry : entrySet()) {
            users.add(entry.getValue());
        }
        return users;
    }

    private ResultSet loadTable(String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            return statement.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Unable to load the Users Table.");
            return null;
        }
    }

    private class UserEntryComparator implements Comparator<Map.Entry<Integer, User>> {
        public int compare(Map.Entry<Integer, User> user1, Map.Entry<Integer, User> user2) {
            return user1.getValue().compareTo(user2.getValue());
        }
    }

}
