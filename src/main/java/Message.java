import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import java.sql.Timestamp;

public class Message extends Post {
  public Message(String content, int user_id) {
    this.content = content;
    this.user_id = user_id;
  }

  @Override
  public boolean equals(Object otherMessage) {
    if (!(otherMessage instanceof Message)) {
      return false;
    } else {
      Message newMessage = (Message) otherMessage;
      return this.getContent().equals(newMessage.getContent()) &&
             this.getUserId() == newMessage.getUserId();

    }
  }

   public void save() {
     try(Connection con = DB.sql2o.open()) {
       String sql = "INSERT INTO messages(content, user_id, createdAt) VALUES (:content, :user_id, now())";
       this.id = (int) con.createQuery(sql, true)
         .addParameter("content", this.content)
         .addParameter("user_id", this.user_id)
         .executeUpdate()
         .getKey();
     }
   }

   public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM recipes WHERE id=:id;";
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();

      // sql = "DELETE FROM communication WHERE message_id = :message_id";
      // con.createQuery(sql)
      // .addParameter("message_id", this.id)
      // .executeUpdate();
    }
  }

  public void update(String content, String user_id, String instructions) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE messages SET content = :content, user_id =:user_id WHERE id=:id";
    con.createQuery(sql)
    .addParameter("content", content)
    .addParameter("user_id", user_id)
    .addParameter("id", this.id)
    .executeUpdate();
    }
  }

  public static List<Message> all() {
    String sql = "SELECT * FROM messages";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Message.class);
    }
  }

  public static Message find(int id) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM messages where id=:id";
    Message message = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Message.class);
    return message;
    }
  }
}
