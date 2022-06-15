import java.sql.*;
import java.util.Date;
import java.util.List;

public class Document {
    private static int counter = 1;

    private int DocumentID;
    private String DocumentName;
    private java.sql.Date DocumentDate;
    private String StorageAdress;
    private Topic topic;
    private Category category;
    private List<Tag> tagList;

    private final String dbURL = Main.dbUrl;
    private final String user = Main.user;
    private final String password = Main.password;

    public Document(String DocumentName, java.sql.Date DocumentDate, String StorageAdress, Topic topic, Category category, List<Tag> tagList){
        System.out.println("\nCreating a document in Process...");
        this.DocumentName = DocumentName;
        this.DocumentDate = DocumentDate;
        this.StorageAdress = StorageAdress;
        this.topic = topic;
        this.category = category;
        this.tagList = tagList;
        System.out.println("...Done");
    }

    public void ajouterBdd(){
        System.out.println("\nAdding a new document in Process...");
        final String documentSql = "INSERT INTO Document VALUES (0, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(dbURL, user, password);
                 PreparedStatement documentStmt = con.prepareStatement(documentSql)) {
                    documentStmt.setString(1, DocumentName);
                    documentStmt.setDate(2, DocumentDate);
                    documentStmt.setString(3, StorageAdress);
                    documentStmt.setInt(4, topic.getTopicID());
                    documentStmt.setInt(5, category.getCategoryID());
                    for (Tag tag : tagList){
                        tag.ajouterContenir(counter);
                    }
                    counter++;
                    documentStmt.executeUpdate();
                    System.out.println("...Done");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Document(){

    }

    public void test() {
        System.out.println("\ndemo4...");
        final String sql = "SELECT * FROM Tag";
        try (Connection con = DriverManager.getConnection(dbURL, user, password);
             PreparedStatement stmt = con.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("TagID");
                    String name = rs.getString("TagName");
                    System.out.println(id + " " + name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
