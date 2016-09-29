import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/message_board_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deletMessageeQuery = "DELETE FROM messages *;";
      String deleteTopicQuery = "DELETE FROM topics *;";
      String deleteCOMQuery = "DELETE FROM communications *;";
      con.createQuery(deletMessageeQuery).executeUpdate();
      con.createQuery(deleteTopicQuery).executeUpdate();
      con.createQuery(deleteCOMQuery).executeUpdate();
    }
  }

}
