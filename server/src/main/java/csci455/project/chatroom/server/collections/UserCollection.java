package csci455.project.chatroom.server.collections;
import csci455.project.chatroom.server.Mapper;
import csci455.project.chatroom.server.models.DatabaseCredential;
import csci455.project.chatroom.server.models.User;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class UserCollection implements Map<Integer, User>
{
    private static int userId;
    private final DatabaseCredential credential;

    public UserCollection (DatabaseCredential credential)
    {
        this.credential = credential;
        userId = Mapper.getNextId(Mapper.getConnection(this.credential), "Users");
    }

    public void clear()
    {
        Connection connection = Mapper.getConnection(credential);
        
    }


}
