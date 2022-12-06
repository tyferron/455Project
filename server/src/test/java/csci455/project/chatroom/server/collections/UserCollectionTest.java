package csci455.project.chatroom.server.collections;
import csci455.project.chatroom.server.models.DatabaseCredential;
import csci455.project.chatroom.server.models.User;
import java.util.Collection;
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
        DatabaseCredential credential = new DatabaseCredential(5432, "ChatroomDB", "postgres", "postgres");
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
        Assertions.assertTrue(userTable.containsKey(8));
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
        User user = new User(8, "test", "123");
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
        entries.add(new User(8, "test", "123"));
        Assertions.assertArrayEquals(entries.toArray(), userTable.entrySet().toArray());
    }

    @Test
    public void testGet()
    {
        User expected = new User(8, "test", "123");
        Assertions.assertEquals(expected, userTable.get(8));
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
        keys.add(8);
        Assertions.assertArrayEquals(keys.toArray(), userTable.keySet().toArray());
    }

    @Test
    public void testPutWithDuplicateKeys()
    {
        User expected = new User(8, "test", "123");
        User updatedUser = new User(8, "Zak", "NdsuRules");
        User actual = userTable.put(8, updatedUser);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testRemove()
    {
        User expected = new User(8, "Zak", "NdsuRules");
        Assertions.assertEquals(expected, userTable.remove(8));
    }

    @Test
    public void testRemoveWithNothing()
    {
        Assertions.assertNull(userTable.remove(8));
    }

    @Test
    public void testRemoveWithElementNotContainingKey()
    {
        Assertions.assertNull(userTable.remove(8));
    }

    @Test
    public void testSize()
    {
        Assertions.assertEquals(1, userTable.size());
    }

    @Test
    public void testValues()
    {
        Collection<User> expected = new TreeSet<User>();
        expected.add(new User(9, "test", "123"));
        Assertions.assertArrayEquals(expected.toArray(), userTable.values().toArray());
    }
}
