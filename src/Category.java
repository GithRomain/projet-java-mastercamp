import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class Category {
    private int CategoryID;
    private String Name;

    private final String dbURL = Main.dbUrl;
    private final String user = Main.user;
    private final String password = Main.password;

    public Category(int CategoryID, String Name){
        this.CategoryID = CategoryID;
        this.Name = Name;
    }

    /**
     * ajoute a la table Category une nouvelle category
     * @return void
     * @params none
     */
    public void ajouterBdd(){
        System.out.println("\nAdding a new Tag in Process...");
        final String sql = "INSERT INTO Category VALUES (?, ?)";
        try (Connection con = DriverManager.getConnection(dbURL, user, password);
             PreparedStatement stm = con.prepareStatement(sql)) {
            //set les paramètres de category
            stm.setInt(1, CategoryID);
            stm.setString(2, Name);
            stm.executeUpdate();
        //Exception mauvaise requete SQL
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("...Done a new Tag in Process");
    }

    /**
     * Recherche dans la table Category si une category existe, si non elle l'ajoute tant qu'elle respecte les clefs primaires et les foreign clef
     * @return void
     * @params none
     * @throws Exception
     */
    public void rechercherBdd() throws Exception {
        System.out.println("\nSearching every Category in Process...");
        Set<Integer> integerSet = new LinkedHashSet<>();
        Set<String> stringSet = new LinkedHashSet<>();
        final String sql = "SELECT CategoryID, Name FROM Category";
        try (Connection con = DriverManager.getConnection(dbURL, user, password);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            //lister toutes les category par ID
            while (rs.next()) {
                int id = rs.getInt("CategoryID");
                String name = rs.getString("Name");
                integerSet.add(id);
                stringSet.add(name);
            }
        //Exception mauvaise requete SQL
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //rajouter une nouvelle category si elle n'existe pas deja
        if (!integerSet.contains(this.CategoryID) && (!stringSet.contains(this.Name))){
            this.ajouterBdd();
            } else if (!integerSet.contains(this.CategoryID) && (stringSet.contains(this.Name))){
                throw new Exception("Attention aux doublons de categroyName");
            }else {
                System.out.println("La category existe deja");
        }
        System.out.println("Done Searching every Category");
    }

    /**
     * getter de categoryID
     * @return int
     * @params none
     */
    public int getCategoryID() {
        return CategoryID;
    }

    /**
     * getter de Name
     * @return String
     * @params none
     */
    public String getName() {
        return Name;
    }
}
