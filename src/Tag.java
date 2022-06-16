import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

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

    /**
     * ajoute a la table contenir une nouvelle relation tag / document
     * @return void
     * @params int
     */
    public void ajouterContenirBdd(int IdDocument){
        System.out.println("\nAdding a new contenir in Process...");
        final String sql = "INSERT INTO contenir VALUES (?, ?)";
        try (Connection con = DriverManager.getConnection(dbURL, user, password);
             PreparedStatement stmt = con.prepareStatement(sql)) {
                //set les paramètres de category
                stmt.setInt(1, IdDocument);
                stmt.setInt(2, IdTag);
                stmt.executeUpdate();
        //Exception mauvaise requete SQL
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("...Done Adding a new contenir");
    }

    /**
     * ajoute a la table Tag une un nouveau Tag
     * @return void
     * @params none
     */
    public void ajouterBdd(){
        System.out.println("\nAdding a new Tag in Process...");
        final String sql = "INSERT INTO Tag VALUES (?, ?)";
        try (Connection con = DriverManager.getConnection(dbURL, user, password);
             PreparedStatement stm = con.prepareStatement(sql)) {
            //set les paramètres de category
            stm.setInt(1, IdTag);
            stm.setString(2, Name);
            stm.executeUpdate();
        //Exception mauvaise requete SQL
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("...Done Adding a new Tag");
    }

    /**
     * Recherche dans la table contenir si une relation Tag / docuement existe, si non elle l'ajoute tant qu'elle respecte les clefs primaires et les foreign clef
     * @return void
     * @params int
     * @throws Exception
     */
    public void rechercherContenirBdd(int IdDocument) throws Exception {
        System.out.println("\nSearching every Contenir in Process...");
        boolean bool = rechercherBdd();
        Set<Integer> integerSet = new LinkedHashSet<>();
        Set<Integer> integerSet1 = new LinkedHashSet<>();
        final String sql = "SELECT DocumentID, TagID FROM Contenir";
        try (Connection con = DriverManager.getConnection(dbURL, user, password);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            //lister toutes les relations TagID / docID par ID
            while (rs.next()) {
                int id = rs.getInt("DocumentID");
                int id1 = rs.getInt("TagID");
                integerSet.add(id);
                integerSet1.add(id1);
            }
        //Exception mauvaise requete SQL
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //rajouter un nouvau lien si il n'existe pas deja
        if (!integerSet.contains(IdDocument) || !integerSet1.contains(this.IdTag) && bool){
                this.ajouterContenirBdd(IdDocument);
            } else {
                System.out.println("Verifier votre nouvelle combinaison id, name de contenir");
            }
        System.out.println("...Done Searching every Contenir");
    }

    /**
     * Recherche dans la table Tag si un Tag existe, si non elle l'ajoute tant qu'elle respecte les clefs primaires et les foreign clef
     * @return boolean
     * @params none
     * @throws Exception
     */
    public boolean rechercherBdd() throws Exception {
        System.out.println("\nSearching every Tag in Process...");
        Set<Integer> integerSet = new LinkedHashSet<>();
        Set<String> stringSet = new LinkedHashSet<>();
        final String sql = "SELECT TagID, TagName FROM Tag";
        try (Connection con = DriverManager.getConnection(dbURL, user, password);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            //lister tous les Tag par ID
            while (rs.next()) {
                int id = rs.getInt("TagID");
                String name = rs.getString("TagName");
                integerSet.add(id);
                stringSet.add(name);
            }
        //Exception mauvaise requete SQL
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //rajouter un nouvau Tag si il n'existe pas deja
        if (!integerSet.contains(this.IdTag) && (!stringSet.contains(this.Name))){
            this.ajouterBdd();
            return true;
        } else if (!integerSet.contains(this.IdTag) && (stringSet.contains(this.Name))){
            throw new Exception("Attention aux doublons de categroyName");
        }else {
            System.out.println("La category existe deja");
            return false;
        }
    }

    /**
     * getter de Name
     * @return String
     * @params none
     */
    public String getName() {
        return Name;
    }

    /**
     * getter de IdTag
     * @return int
     * @params none
     */
    public int getIdTag() {
        return IdTag;
    }
}
