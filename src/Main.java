import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String dbUrl = "jdbc:mysql://localhost:3306/projetjava";
    public static String user = "root";
    public static String password = "c0ll05u5";

    public static void main(String[] args){
        Document document1 = new Document("Document 1", new java.sql.Date(10), "doc1", new Topic("Cluster Graduation Projet en 2022"), new Category("plan"), new ArrayList<>(List.of(new Tag("medical"), new Tag("technical"))));
        Document document2 = new Document("Document 2", new java.sql.Date(20), "doc2", new Topic( "CS243 Course Files in Fall 2021"), new Category("school"), new ArrayList<>(List.of(new Tag("legal"))));
        Document document3 = new Document("Document 3", new java.sql.Date(30), "doc3", new Topic("Star wars"), new Category( "science"), new ArrayList<>(List.of(new Tag("medical"), new Tag("reporting"), new Tag("friends"))));
        Document document4 = new Document("Document 4", new java.sql.Date(40), "doc4", new Topic("Dogs"), new Category("teachers"), new ArrayList<>(List.of(new Tag("medical"), new Tag("technical"))));
        Document document5 = new Document("Document 5", new java.sql.Date(50), "doc5", new Topic("Efrei 2024"), new Category( "food"), new ArrayList<>(List.of()));

        document1.ajouterBdd();
        document1.lienContenir();

        document2.ajouterBdd();
        document2.lienContenir();

        document3.ajouterBdd();
        document3.lienContenir();

        document4.ajouterBdd();
        document4.lienContenir();

        document5.ajouterBdd();
        document5.lienContenir();
    }
}
