import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String dbUrl = "jdbc:mysql://localhost:3306/projetjava";
    public static String user = "root";
    public static String password = "c0ll05u5";

    public static void main(String[] args){
        //3 a)
        //i)
        Document document1 = new Document("Document 1", new java.sql.Date(10), "doc1", new Topic("Cluster Graduation Projet en 2022"), new Category("policy"), new ArrayList<>(List.of(new Tag("medical"), new Tag("technical"))));
        Document document2 = new Document("Document 2", new java.sql.Date(20), "doc2", new Topic( "Star wars"), new Category("policy"), new ArrayList<>(List.of(new Tag("legal"))));
        Document document3 = new Document("Document 3", new java.sql.Date(30), "doc3", new Topic("Star wars"), new Category( "science"), new ArrayList<>(List.of(new Tag("medical"), new Tag("reporting"), new Tag("friends"))));
        Document document4 = new Document("Document 4", new java.sql.Date(40), "doc4", new Topic("Dogs"), new Category("teachers"), new ArrayList<>(List.of(new Tag("medical"), new Tag("technical"))));
        Document document5 = new Document("Document 5", new java.sql.Date(50), "doc5", new Topic("Efrei 2024"), new Category( "food"), new ArrayList<>(List.of()));

        //ii)
        document1.ajouterBdd();
        document2.ajouterBdd();
        document3.ajouterBdd();
        document4.ajouterBdd();
        document5.ajouterBdd();

        //b)
        //i)
        Document.listDocumentByCategory("policy");
        Document.listDocumentByTopic("Star wars");
        Document.listDocumentByTag("technical");

        //ii)
        System.out.println(Document.mostFrequentTopic().orElseThrow());

        //iii)
        Document.ListMostOccurrencesTag();

        //c)
        //i)
        Document document6 = new Document("Document 6", null, "doc6", new Topic( "Star wars"), new Category("policy"), new ArrayList<>(List.of(new Tag("legal"))));
        document6.ajouterBdd();

        //ii)
        document6.updateDate(new java.sql.Date(60));

        //iii)
        document6.ajouterTag(new Tag("test"));
    }
}
