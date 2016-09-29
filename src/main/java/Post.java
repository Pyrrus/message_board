import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public abstract class Post {
  public String content;
  public int user_id;
  public int id;
  public Timestamp createdAt;

  public int getId() {
    return id;
  }

  public String getContent() {
    return content;
  }

  public int getUserId() {
    return user_id;
  }

  public Timestamp getCreatedAt(){
    return createdAt;
  }
}
