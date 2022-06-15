public class Category {
    private int CategoryID;
    private String Name;

    public Category(int CategoryID, String Name){
        this.CategoryID = CategoryID;
        this.Name = Name;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public String getName() {
        return Name;
    }
}
