package csci455.project.chatroom.server;
import java.sql.Connection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import csci455.project.chatroom.server.models.DatabaseCredential;
public class MapperTest 
{
    private static DatabaseCredential credential;
    
    @BeforeAll
    public static void loadCredentials()
    {
        credential = new DatabaseCredential(7359, "ChatroomDB", 
        "postgres", "Ndsu#5973");
    }

    @Test
    public void testGetNextId()
    {
        int expected = 3;
        Connection connection = Mapper.getConnection(credential);
        Assertions.assertNotNull(connection);
        String tableName = "Users";
        int actual = Mapper.getNextId(connection, tableName);
        Assertions.assertEquals(expected, actual);
    }
}
