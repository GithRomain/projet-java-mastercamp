import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class Topic extends Table{
    public Topic(String TopicName){
        super(TopicName);
    }

    public Topic(Topic topic){
        super(topic.getName());
    }
}
