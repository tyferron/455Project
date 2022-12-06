package csci455.project.chatroom.server.collections;
import csci455.project.chatroom.server.Mapper;
import csci455.project.chatroom.server.models.ChatRoom;
import csci455.project.chatroom.server.models.DatabaseCredential;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
public class ChatRoomCollectionTest {
    private static Map<Integer,ChatRoom> chatRoomTable;

    @BeforeAll
    public static void initChatRoomTable()
    {
        DatabaseCredential credential = new DatabaseCredential(5432, "ChatroomDB", "postgres", "postgres");
        chatRoomTable = new ChatRoomCollection(credential);
    }

    @Test
    public void testIsEmptyWithoutObjects()
    {
        Assertions.assertTrue(chatRoomTable.isEmpty());
    }

    @Test
    public void testClear()
    {
        chatRoomTable.clear();
        Assertions.assertEquals(0, chatRoomTable.size());
    }

    @Test
    public void testPutWithoutDuplicate()
    {
        ChatRoom testRoom = ChatRoom.generate();
        Assertions.assertNull(chatRoomTable.put(1, testRoom));
    }

    @Test
    public void testContainsKey()
    {
        Assertions.assertTrue(chatRoomTable.containsKey(2));
    }

    @Test
    public void testDoesNotContainKey1()
    {
        Assertions.assertFalse(chatRoomTable.containsKey(1));
    }

    @Test
    public void testDoesNotContainKey2()
    {
        Assertions.assertFalse(chatRoomTable.containsKey("4"));
    }

    @Test
    public void testContainsValue()
    {
        ChatRoom room = new ChatRoom(4, "eDSY1TBj7WYzf0hup5",
         "FxOXOH5k0h0qVtKBxFS",
        "BOb9k1EYdjRUNCgjy8FB3NKgp" );
        Assertions.assertTrue(chatRoomTable.containsValue(room)); 
    }

    @Test
    public void testDoesNotContainValue()
    {
        ChatRoom room = new ChatRoom(1, "v", "3VS/u;!a1_U<^0T7-F,",
        "N0>y>P3cv3" );
        Assertions.assertFalse(chatRoomTable.containsValue(room));
    }

    @Test
    public void testEntrySet()
    {
        Set<Map.Entry<Integer,ChatRoom>> entries = new TreeSet<Map.Entry<Integer,ChatRoom>>();
        entries.add(new ChatRoom(4, "eDSY1TBj7WYzf0hup5",
        "FxOXOH5k0h0qVtKBxFS",
       "BOb9k1EYdjRUNCgjy8FB3NKgp" ));
        Assertions.assertArrayEquals(entries.toArray(), chatRoomTable.entrySet().toArray());
    }

    @Test
    public void testGet()
    {
        ChatRoom expected = new ChatRoom(4, "eDSY1TBj7WYzf0hup5",
        "FxOXOH5k0h0qVtKBxFS",
       "BOb9k1EYdjRUNCgjy8FB3NKgp" );
        Assertions.assertEquals(expected, chatRoomTable.get(4));
    }

    @Test
    public void testGetReturningNull()
    {
        ChatRoom testChatRoom = new ChatRoom(1, "V", "3vS/u;!a1_U<^0T7-F,",
        "N0>y>P3cv3" );
        Assertions.assertNull(chatRoomTable.get(testChatRoom));
    }

    @Test
    public void testKeySet()
    {
        Set<Integer> keys = new TreeSet<Integer>();
        keys.add(4);
        Assertions.assertArrayEquals(keys.toArray(), chatRoomTable.keySet().toArray());
    }

    @Test
    public void testPutWithDuplicateKeys()
    {
        Random rand = new Random();
        ChatRoom expected = new ChatRoom(4, "eDSY1TBj7WYzf0hup5",
        "FxOXOH5k0h0qVtKBxFS",
       "BOb9k1EYdjRUNCgjy8FB3NKgp" );
        ChatRoom updatedChatRoom = new ChatRoom(rand.nextInt(Integer.MAX_VALUE), Mapper.generateString(rand, 20),
        Mapper.generateString(rand, 20), Mapper.generateString(rand, 40));
        ChatRoom actual = chatRoomTable.put(4, updatedChatRoom);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testRemove()
    {
        ChatRoom expected = new ChatRoom(4, "HWMSHWvw5X8", "c5N6AtYAFGxJB",
        "RYI9FP1W5Ttg" );
        Assertions.assertEquals(expected, chatRoomTable.remove(4));
    }

    @Test
    public void testRemoveWithNothing()
    {
        Assertions.assertNull(chatRoomTable.remove(8));
    }

    @Test
    public void testRemoveWithElementNotContainingKey()
    {
        Assertions.assertNull(chatRoomTable.remove(8));
    }

    @Test
    public void testSize()
    {
        Assertions.assertEquals(1, chatRoomTable.size());
    }

    @Test
    public void testValues()
    {
        Collection<ChatRoom> expected = new TreeSet<ChatRoom>();
        expected.add(new ChatRoom(1, "rz7I", "sWO2Wc3sHoRQxWi",
        "ZP69mMyHf61QyssRxyQ5Fok1cC" ));
        Assertions.assertArrayEquals(expected.toArray(), chatRoomTable.values().toArray());
    }
}
