package csci455.project.chatroom.server;
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
        credential = new DatabaseCredential(5432, "ChatroomDB", 
        "postgres", "postgres");
    }

    @Test
    public void testConnection()
    {
        Assertions.assertNotNull(Mapper.getConnection(credential));
    }
}
