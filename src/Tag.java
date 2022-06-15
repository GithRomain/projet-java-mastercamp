import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tag {
    private int IdTag;
    private String Name;

    private final String dbURL = Main.dbUrl;
    private final String user = Main.user;
    private final String password = Main.password;

    public Tag(int IdTag, String Name){
        this.IdTag = IdTag;
        this.Name = Name;
    }

    public void ajouterContenir(int IdDocument){
        System.out.println("\nAdding a new contenir in Process...");
        final String contenirSql = "INSERT INTO contenir VALUES (?, ?)";
        try (Connection con = DriverManager.getConnection(dbURL, user, password);
             PreparedStatement contenirStmt = con.prepareStatement(contenirSql)) {
                contenirStmt.setInt(0, IdDocument);
                contenirStmt.setInt(1, IdTag);
                contenirStmt.executeUpdate();
                System.out.println("...Done");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String getName() {
        return Name;
    }

    public int getIdTag() {
        return IdTag;
    }
}
