import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String dbUrl = "jdbc:mysql://localhost:3306/projetjava";
    public static String user = "root";
    public static String password = "c0ll05u5";

    public static void main(String[] args) throws SQLException {
        Document document1 = new Document("Document 1", new java.sql.Date(10), "doc1", new Topic(2, "Cluster Graduation Projet en 2022"), new Category(2, "plan"), new ArrayList<>(List.of(new Tag(2, "medical"), new Tag(4, "technical"))));
        Document document2 = new Document("Document 2", new java.sql.Date(20), "doc2", new Topic(1, "CS243 Course Files in Fall 2021"), new Category(6, "school"), new ArrayList<>(List.of(new Tag(1, "legal"))));
        Document document3 = new Document("Document 3", new java.sql.Date(30), "doc3", new Topic(4, "Star wars"), new Category(9, "science"), new ArrayList<>(List.of(new Tag(2, "medical"), new Tag(6, "reporting"), new Tag(7, "friends"))));
        Document document4 = new Document("Document 4", new java.sql.Date(40), "doc4", new Topic(3, "Dogs"), new Category(7, "teachers"), new ArrayList<>(List.of(new Tag(2, "medical"), new Tag(4, "technical"))));
        Document document5 = new Document("Document 5", new java.sql.Date(50), "doc5", new Topic(5, "Efrei 2024"), new Category(8, "food"), new ArrayList<>(List.of()));

        Document test = new Document("Document 6", new java.sql.Date(60), "doc1", new Topic(6, "Cluster Graduation Projet en 2022"), new Category(10, "plan"), new ArrayList<>(List.of(new Tag(10, "friends"), new Tag(4, "technical"))));


        document1.ajouterBdd();
        document2.ajouterBdd();
        document3.ajouterBdd();
        document4.ajouterBdd();
        document5.ajouterBdd();

        //exemple doublons géré par exception
        //test.ajouterBdd();
    }
}
