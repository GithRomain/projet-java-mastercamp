import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public class Topic {
    private int TopicID;
    private String Topic;

    private final String dbURL = Main.dbUrl;
    private final String user = Main.user;
    private final String password = Main.password;

    public Topic(int TopicID, String Topic){
        this.TopicID = TopicID;
        this.Topic = Topic;
    }

    /**
     * ajoute a la table Topic une nouvelle category
     * @return void
     * @params none
     */
    public void ajouterBdd(){
        System.out.println("\nAdding a new Tag in Process...");
        final String sql = "INSERT INTO Topic VALUES (?, ?)";
        try (Connection con = DriverManager.getConnection(dbURL, user, password);
             PreparedStatement stm = con.prepareStatement(sql)) {
            //set les paramètres de category
            stm.setInt(1, TopicID);
            stm.setString(2, Topic);
            stm.executeUpdate();
        //Exception mauvaise requete SQL
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("...Done adding a new Tag");
    }

    /**
     * Recherche dans la table Topic si un Topic existe, si non elle l'ajoute tant qu'elle respecte les clefs primaires et les foreign clef
     * @return void
     * @params none
     * @throws Exception
     */
    public void rechercherBdd() throws Exception {
        System.out.println("\nSearching every Topic in Process...");
        Set<Integer> integerSet = new LinkedHashSet<>();
        Set<String> stringSet = new LinkedHashSet<>();
        final String sql = "SELECT TopicID, TopicName FROM Topic";
        try (Connection con = DriverManager.getConnection(dbURL, user, password);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            //lister tous les Document par ID
            while (rs.next()) {
                int id = rs.getInt("TopicID");
                String name = rs.getString("TopicName");
                integerSet.add(id);
                stringSet.add(name);
            }
        //Exception mauvaise requete SQL
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //rajouter un nouvau Topic si il n'existe pas deja
        if (!integerSet.contains(this.TopicID) && !stringSet.contains(this.Topic)){
            this.ajouterBdd();
        } else if (!integerSet.contains(this.TopicID) && (stringSet.contains(this.Topic))){
            throw new Exception("Attention aux doublons de TopicName");
        }else {
            System.out.println("La category existe deja");
        }
        System.out.println("...Done Ssearching every Topic");
    }

    /**
     * getter de TopicID
     * @return int
     * @params none
     */
    public int getTopicID() {
        return TopicID;
    }

    /**
     * getter de Topic
     * @return String
     * @params none
     */
    public String getTopic() {
        return Topic;
    }
}
