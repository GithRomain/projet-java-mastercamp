import java.sql.*;
import java.util.*;

public class Document {
    private int DocumentID;
    private String DocumentName;
    private java.sql.Date DocumentDate;
    private String StorageAdress;
    private Topic topic;
    private Category category;
    private List<Tag> tagList = new ArrayList<>();

    public Document(String DocumentName, java.sql.Date DocumentDate, String StorageAdress, Topic topic, Category category, List<Tag> tagList){
        System.out.println("\nCreating a document in Process...");
        this.DocumentName = DocumentName;
        this.DocumentDate = DocumentDate;
        this.StorageAdress = StorageAdress;
        this.topic = new Topic(topic);
        this.category = new Category(category);
        for (Tag tag : tagList){
            Tag newTag = new Tag(tag);
            this.tagList.add(newTag);
        }
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
        try (Connection con = DriverManager.getConnection(Main.dbUrl, Main.user, Main.password);
                 PreparedStatement stmt = con.prepareStatement(sql)) {
                    //set les paramètres de doc
                    stmt.setString(1, DocumentName);
                    stmt.setDate(2, DocumentDate);
                    stmt.setString(3, StorageAdress);
                    stmt.setInt(4, topic.getID());
                    stmt.setInt(5, category.getID());
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

    private void findId(){
        System.out.println("\nFind ID of Document in Process...");
        //Rechercher en fonction des paramètre de la sous classe
        final String sql = "SELECT DocumentID FROM Document";
        try (Connection con = DriverManager.getConnection(Main.dbUrl, Main.user, Main.password);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            //lister toutes les ID
            while (rs.next()) {
                this.DocumentID = rs.getInt("DocumentID");
            }
        }
        //Exception mauvaise requete SQL
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("...Done Find ID of Document");
    }

    public void lienContenir(){
        this.findId();
        //ajouter tous les liens entre Document et Tag
        for (Tag tag : tagList){
            //Si nouveau Tag detectee alors creation
            tag.findCombinaisonContenir(this.DocumentID);
        }
    }
}
