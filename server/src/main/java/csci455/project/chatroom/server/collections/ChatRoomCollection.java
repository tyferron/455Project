package csci455.project.chatroom.server.collections;
import csci455.project.chatroom.server.Mapper;
import csci455.project.chatroom.server.models.ChatRoom;
import csci455.project.chatroom.server.models.DatabaseCredential;
import java.io.Closeable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
public class ChatRoomCollection implements Closeable, Map<Integer,ChatRoom>
{
    private final Connection connection;
    private final Set<String> passwords;
    private final Set<String> roomNames;

    public ChatRoomCollection(DatabaseCredential credential)
    {
        connection = Mapper.getConnection(credential);
        passwords = new HashSet<String>();
        roomNames = new HashSet<String>();
        initUserNamesAndPasswords();
    }

    public void clear() 
    {
        String sql = "DELETE FROM public.\"ChatRoom\";";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeQuery();
            connection.commit();
            statement.close();
            passwords.clear();
            roomNames.clear();
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
        ResultSet result = Mapper.resultOf(connection, "SELECT * FROM public.\"ChatRoom\" WHERE \"RoomID\" = " + id + ";");
        try {
            return result.next();
        } catch (SQLException ex) {
            System.out.println("Unable to determine whether the key exists.");
            ex.printStackTrace();
        }
        return false;
    }

    public boolean containsValue(Object value) {
        if (value instanceof ChatRoom) {
            ChatRoom room = (ChatRoom) value;
            ResultSet table = Mapper.resultOf(connection, "SELECT * FROM public.\"ChatRoom\" WHERE " +
                    "\"RoomName\" = '" + room.getRoomName() + "' AND \"Password\" = '" + room.getPassword()
                    + "' AND \"MessageHistory\" = '" + room.getMessageHistory() + "';");
            try {
                return table.next();
            } catch (SQLException ex) {
                System.out.println("Unable to determine if the table contains the value.");
                ex.printStackTrace();
            }
        }
        return false;
    }

    public Set<Map.Entry<Integer, ChatRoom>> entrySet() {
        Set<Map.Entry<Integer, ChatRoom>> entries = new HashSet<Map.Entry<Integer, ChatRoom>>();
        ResultSet table = Mapper.resultOf(connection, "SELECT * FROM \"ChatRoom\";");
        try {
            while (table.next()) {
                ChatRoom current = new ChatRoom(table.getInt(1), table.getString(2),
                        table.getString(3), table.getString(4));
                entries.add(current);
            }
            table.close();
        } catch (SQLException ex) {
            System.out.println("Unable to retrieve the set of entries.");
            ex.printStackTrace();
        }
        return entries;
    }

    public ChatRoom get(Object key) {
        if (key instanceof Integer) {
            Integer id = (Integer) key;
            ResultSet table = Mapper.resultOf(connection,"SELECT * FROM public.\"ChatRoom\" WHERE \"RoomID\" = " + id + ";");
            try {
                return table.next() ? new ChatRoom(table.getInt(1), table.getString(2),
                 table.getString(3), table.getString(4)) : null;
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
        Set<Integer> keys = new HashSet<Integer>();
        ResultSet table = Mapper.resultOf(connection, "SELECT * FROM public.\"ChatRoom\";");
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

    public ChatRoom put(Integer key, ChatRoom value) {
        if (!roomNames.add(value.getRoomName()))
        {
            throw new IllegalArgumentException("The Room name has already been taken.");
        }
        else if (!passwords.add(value.getPassword()))
        {
            throw new IllegalArgumentException("The Passowrd has already been taken.");
        }
        ChatRoom previous = get(key);
        try {
            String sql;
            if (previous == null) // Add case
            {
                sql = "INSERT INTO public.\"ChatRoom\" (\"RoomName\", \"Password\", \"MessageHistory\") VALUES ('" +
                        value.getRoomName() + "', '" + value.getPassword() + "', '" + value.getMessageHistory() + "');";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeQuery();
                statement.close();
                connection.commit();
            } else // Update case
            {
                sql = "UPDATE public.\"ChatRoom\" SET \"RoomName\" = '" + value.getRoomName() + "', " +
                        "\"Password\" = '" + value.getPassword() + "', \"MessageHistory\" = '" + value.getMessageHistory() + 
                        "' WHERE \"RoomID\" = " + key + ";";
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

    public void putAll(Map<? extends Integer, ? extends ChatRoom> m) {
        for (Map.Entry<? extends Integer, ? extends ChatRoom> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public ChatRoom remove(Object key) {
        ChatRoom previous = get(key);
        if (previous != null)
        {
            String sql = "DELETE FROM public.\"ChatRoom\" WHERE \"RoomID\" = " + previous.getKey() + ";";
            try 
            {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.execute();
                statement.close();
                connection.commit();
                roomNames.remove(previous.getRoomName());
                passwords.remove(previous.getPassword());
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
        ResultSet result = Mapper.resultOf(connection, "SELECT COUNT(\"RoomID\") FROM public.\"ChatRoom\";");
        try {
            return result.next() ? result.getInt(1) : 0;
        } catch (SQLException ex) {
            System.out.println("Unable to count Users");
        }
        return 0;
    }

    public Collection<ChatRoom> values() {
        Collection<ChatRoom> users = new HashSet<ChatRoom>();
        for (Map.Entry<Integer, ChatRoom> entry : entrySet()) {
            users.add(entry.getValue());
        }
        return users;
    }

    private void initUserNamesAndPasswords()
    {
        Collection<ChatRoom> currentRooms = values();
        for (ChatRoom room : currentRooms)
        {
            passwords.add(room.getPassword());
            roomNames.add(room.getRoomName());
        }
    }
}
