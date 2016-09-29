import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class User {
  private String name;
  private boolean isAdmin;
  private int id;

  public User (String name, boolean isAdmin) {
    this.name = name;
    this.isAdmin = isAdmin;
  }

  public String getName() {
    return name;
  }

  public boolean getAdmin() {
    return isAdmin;
  }

  public int getId() {
    return id;
  }


}
