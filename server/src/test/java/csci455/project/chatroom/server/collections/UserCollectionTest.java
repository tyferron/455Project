package csci455.project.chatroom.server.collections;
import csci455.project.chatroom.server.models.DatabaseCredential;
import csci455.project.chatroom.server.models.User;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
public class UserCollectionTest 
{
    private static Map<Integer, User> userTable;

    @BeforeAll
    public static void initUserTable()
    {
        DatabaseCredential credential = new DatabaseCredential(7359, "ChatroomDB", "postgres", "Ndsu#5973");
        userTable = new UserCollection(credential);
    }

    @Test
    public void testIsEmptyWithoutObjects()
    {
        Assertions.assertTrue(userTable.isEmpty());
    }

    @Test
    public void testClear()
    {
        userTable.clear();
        Assertions.assertEquals(0, userTable.size());
    }

    @Test
    public void testPutWithoutDuplicate()
    {
        User testUser = new User(0, "test", "123");
        Assertions.assertNull(userTable.put(1, testUser));
    }

    @Test
    public void testContainsKey()
    {
        Assertions.assertTrue(userTable.containsKey(4));
    }

    @Test
    public void testDoesNotContainKey1()
    {
        Assertions.assertFalse(userTable.containsKey(3));
    }

    @Test
    public void testDoesNotContainKey2()
    {
        Assertions.assertFalse(userTable.containsKey("4"));
    }

    @Test
    public void testContainsValue()
    {
        User user = new User(4, "test", "123");
        Assertions.assertTrue(userTable.containsValue(user)); 
    }

    @Test
    public void testDoesNotContainValue()
    {
        User user = new User(4, null, "123");
        Assertions.assertFalse(userTable.containsValue(user));
    }

    @Test
    public void testEntrySet()
    {
        Set<Map.Entry<Integer,User>> entries = new TreeSet<Map.Entry<Integer,User>>();
        entries.add(new User(4, "test", "123"));
        Assertions.assertArrayEquals(entries.toArray(), userTable.entrySet().toArray());
    }

    @Test
    public void testGet()
    {
        User expected = new User(4, "test", "123");
        Assertions.assertEquals(expected, userTable.get(4));
    }

    @Test
    public void testGetReturningNull()
    {
        User testUser = new User(4, "test", "1234");
        Assertions.assertNull(userTable.get(testUser));
    }

    @Test
    public void testKeySet()
    {
        Set<Integer> keys = new TreeSet<Integer>();
        keys.add(4);
        Assertions.assertArrayEquals(keys.toArray(), userTable.keySet().toArray());
    }

    @Test
    public void testPutWithDuplicateKeys()
    {
        User expected = new User(4, "test", "123");
        User actual = userTable.put(4, new User(3, "Zak", "NdsuRules"));
        Assertions.assertEquals(expected, actual);
    }
}
