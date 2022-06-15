import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String dbUrl = "jdbc:mysql://localhost:3306/projetjava";
    public static String user = "root";
    public static String password = "c0ll05u5";

    public static void main(String[] args) throws SQLException {
        Document document1 = new Document("Document 1", new java.sql.Date(3), "doc1", new Topic(1, ""), new Category(1, ""), new ArrayList<>(List.of(new Tag(1, ""), new Tag(2, ""))));
        document1.ajouterBdd();
    }
}
