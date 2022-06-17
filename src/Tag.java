import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Tag extends Table{
    public Tag(String TagName){
        super(TagName);
    }

    public Tag(Tag tag){
        super(tag.getName());
    }

    /**
     * trouver la combinaison de DocumentID et TagID si il n'existe pas, on l'ajoute
     * @return void
     * @params none
     */
    public void findCombinaisonContenir(int IdDocument){
        System.out.println("\nFind ID of Contenir in Process...");
        Map<Integer, Integer> map = new HashMap<>();
        //Rechercher en fonction des paramètre de la sous classe
        final String sql = "SELECT DocumentID, TagID FROM Contenir";
        try (Connection con = DriverManager.getConnection(Main.dbUrl, Main.user, Main.password);
             PreparedStatement stmt = con.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                //lister toutes les relations TagID / docID par ID
                while (rs.next()) {
                    int DocumentID = rs.getInt("DocumentID");
                    int TagID = rs.getInt("TagID");
                    map.put(DocumentID, TagID);
                }
                //si il n'y a pas la combinaison voulu, alors on l'ajoute
                if (!(map.containsKey(IdDocument) && map.containsValue(super.getID()) && map.get(IdDocument).equals(super.getID()))) {
                    this.ajouterBddContenir(IdDocument);
                }
        }
        //Exception mauvaise requete SQL
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("...Done Find ID of Contenir");
    }

    /**
     * ajoute a la Contenir la combinaion voulu
     *
     * @return void
     * @params int
     */
    public void ajouterBddContenir(int IdDocument){
        System.out.println("\nAdding a new Contenir in Process...");
        final String sql = "INSERT INTO Contenir VALUES (?, ?)";
        //Rechercher en fonction des paramètre de la sous classe
        try (Connection con = DriverManager.getConnection(Main.dbUrl, Main.user, Main.password);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            //set les paramètres de category
            stmt.setInt(1, IdDocument);
            stmt.setInt(2, this.getID());
            stmt.executeUpdate();
            //Exception mauvaise requete SQL
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("...Done Adding a new Contenir");
    }
}
