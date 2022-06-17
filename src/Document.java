import java.sql.*;
import java.util.*;

public class Document{
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
                    lienContenir();
        //Exception mauvaise requete SQL
        } catch (SQLException e) {
            e.printStackTrace();
        //Exception conflit clef primaire / foreign clef
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n...Done Adding a new document");
    }

    /**
     * trouver l'id du Document
     * @return void
     * @params none
     */
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

    /**
     * faire le lien entre Document et Tag, en ajoutant les combinaisons dans Contenir
     * @return void
     * @params none
     */
    private void lienContenir(){
        this.findId();
        //ajouter tous les liens entre Document et Tag
        for (Tag tag : tagList){
            //Si nouveau Tag detectee alors creation
            tag.findCombinaisonContenir(this.DocumentID);
        }
    }

    /**
     * print la liste des documents par Category
     *
     * @return void
     * @params String
     */
    public static void listDocumentByCategory(String category) {
        System.out.println("\nVoici la liste des documents par Category :");
        try (Connection con = DriverManager.getConnection(Main.dbUrl, Main.user, Main.password);
             PreparedStatement stmt = con.prepareStatement(DocumentSQLs.ListDocByCategory)) {
                for (List document : getStrings(category, stmt).orElseThrow()){
                    System.out.println("\n" + document.toString());
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * print la liste des documents par Topic
     *
     * @return void
     * @params String
     */
    public static void listDocumentByTopic(String topic) {
        System.out.println("\nVoici la liste des documents par Topic :");
        try (Connection con = DriverManager.getConnection(Main.dbUrl, Main.user, Main.password);
             PreparedStatement stmt = con.prepareStatement(DocumentSQLs.ListDocByTopic)) {
            for (List document : getStrings(topic, stmt).orElseThrow()){
                System.out.println("\n" + document.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * print la liste des documents par Tag
     *
     * @return void
     * @params String
     */
    public static void listDocumentByTag(String tag) {
        System.out.println("\nVoici la liste des documents par Tag :");
        try (Connection con = DriverManager.getConnection(Main.dbUrl, Main.user, Main.password);
             PreparedStatement stmt = con.prepareStatement(DocumentSQLs.ListDocByTag)) {
            for (List document : getStrings(tag, stmt).orElseThrow()){
                System.out.println("\n" + document.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * renvoie un set de List optionel de document en fonction d'un paramètre à rentrer dans une recherche
     *
     * @return Optional(Set(List(Object)))
     * @params String, PreparedStatement
     */
    private static Optional<Set<List<Object>>> getStrings(String str, PreparedStatement stmt) throws SQLException {
        Set<List<Object>> listSet = new HashSet<>();
        stmt.setString(1, str);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int DocumentID = rs.getInt("DocumentID");
            String DocumentName = rs.getString("DocumentName");
            java.sql.Date DocumentDate = rs.getDate("DocumentDate");
            String StorageAdress = rs.getString("StorageAddress");
            int TopicID = rs.getInt("TopicID");
            int CategoryID = rs.getInt("CategoryID");

            listSet.add(List.of(DocumentID, DocumentName, DocumentDate, StorageAdress, TopicID, CategoryID));
        }
        return Optional.of(listSet);
    }
}

class DocumentSQLs{
    static final String ListDocByCategory = """
            select * from Document where CategoryID = (select CategoryID from Category where CategoryName = ?)
            """;

    static final String ListDocByTopic = """
            select * from Document where TopicID = (select TopicID from Topic where TopicName = ?)
            """;

    static final String ListDocByTag = """
            select Document.DocumentID, Document.DocumentName, Document.DocumentDate, Document.StorageAddress, Document.TopicID, Document.CategoryID from Document join Contenir on Document.DocumentID = Contenir.DocumentID join Tag on Contenir.TagID = Tag.TagID where TagName = ?
            """;
}
