import java.sql.*;
import java.util.*;

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
        System.out.println("...Done Creating a document");
    }

    /**
     * ajoute a la table Document un nouveau document
     * @return void
     * @params : none
     */
    public void ajouterBdd(){
        System.out.println("\nAdding a new document in Process...");
        final String sql = "INSERT INTO Document VALUES (0, ?, ?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(dbURL, user, password);
                 PreparedStatement stmt = con.prepareStatement(sql)) {
                    //set les paramètres de doc
                    stmt.setString(1, DocumentName);
                    stmt.setDate(2, DocumentDate);
                    stmt.setString(3, StorageAdress);
                    stmt.setInt(4, topic.getTopicID());
                    stmt.setInt(5, category.getCategoryID());

                    //ajouter tous les liens entre Document et topic (plusieurs topic disponible)
                    for (Tag tag : tagList){
                        //Si nouveau Tag detectee alors creation
                        tag.rechercherContenirBdd(counter);
                    }

                    //incrementation du compteur pour le donner à contenir
                    counter++;

                    //Condition pour ajouter si besoin des valeurs dans les aurtes tables
                    //Si nouveau Topic detectee alors creation
                    topic.rechercherBdd();
                    //Si nouvealle category detectee alors creation
                    category.rechercherBdd();

                    //inserer dans la BDD les params de Document
                    stmt.executeUpdate();
        //Exception mauvaise requete SQL
        } catch (SQLException e) {
            e.printStackTrace();
        //Exception conflit clef primaire / foreign clef
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n...Done Adding a new document");
    }
}
