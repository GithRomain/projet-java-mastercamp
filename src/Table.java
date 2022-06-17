import java.sql.*;

public abstract class Table {
    private int ID;
    private String Name;

    private String NameSubClass = String.valueOf(Table.super.getClass()).substring(6);

    public Table(){
    }

    public Table(String Name){
        this.Name = Name;
        this.findId();
    }

    /**
     * trouver l'id de la sous classe, si il n'existe pas, on l'ajoute
     * @return void
     * @params none
     */
    private void findId(){
        System.out.println("\nFind ID of " + this.NameSubClass + " in Process...");
        int id = 0;
        //Rechercher en fonction des paramètre de la sous classe
        final String sql = "SELECT " + this.NameSubClass + "ID FROM " + this.NameSubClass + " WHERE " + this.NameSubClass + "Name = ?";
        try (Connection con = DriverManager.getConnection(Main.dbUrl, Main.user, Main.password);
             PreparedStatement stmt = con.prepareStatement(sql)) {
            //rechercher le ID à partir du Name
            stmt.setString(1, this.Name);
            ResultSet rs = stmt.executeQuery();
            //lister toutes les category par Name
            while (rs.next()) {
                id = rs.getInt(this.NameSubClass + "ID");
            }
            //si existe deja alors fixe l'id
            if (id != 0){
                this.ID = id;
            }
            //sinon ajoute à la BDD puis fixe l'id
            else {
                this.ajouterBdd();
                while (rs.next()) {
                    this.ID = rs.getInt(this.NameSubClass + "ID");
                }
            }
        }
        //Exception mauvaise requete SQL
        catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("...Done Find ID of " + this.NameSubClass);
    }

    /**
     * ajoute a la sous table un nouvel element
     * @return void
     * @params none
     */
    private void ajouterBdd(){
        System.out.println("\nAdding a new " + this.NameSubClass + " in Process...");
        final String sql = "INSERT INTO " + this.NameSubClass + " VALUES (0, ?)";
        //Rechercher en fonction des paramètre de la sous classe
        try (Connection con = DriverManager.getConnection(Main.dbUrl, Main.user, Main.password);
             PreparedStatement stmt = con.prepareStatement(sql)) {
                //set les paramètres de category
                stmt.setString(1, Name);
                stmt.executeUpdate();
            //Exception mauvaise requete SQL
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("...Done Adding a new " + this.NameSubClass);
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return Name;
    }
}
