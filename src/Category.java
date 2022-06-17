import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class Category extends Table {
    public Category(String CategoryName) {
        super(CategoryName);
    }

    public Category(Category category){
        super(category.getName());
    }
}
