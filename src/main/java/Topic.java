import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Date;
import java.sql.SQLException;

public class Topic extends Post implements DatabaseMethods {
  private String title;

  public Topic(String title, String content, int user_id) {
    this.content = content;
    this.title = title;
    this.user_id = user_id;
  }

  public String getTitle() {
    return title;
  }

  @Override
  public boolean equals(Object otherTopic) {
    if (!(otherTopic instanceof Topic)) {
      return false;
    } else {
      Topic newTopic = (Topic) otherTopic;
      return this.getTitle().equals(newTopic.getTitle()) &&
             this.getContent().equals(newTopic.getContent());
    }
  }

  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO topics(title, content, user_id, createdat) VALUES (:title, :content, :user_id, now())";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("title", this.title)
        .addParameter("content", this.content)
        .addParameter("user_id", this.user_id)
        .executeUpdate()
        .getKey();
   }
 }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM topics WHERE id=:id;";
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();

      sql = "DELETE FROM topic_tags WHERE topic_id = :topic_id";
      con.createQuery(sql)
      .addParameter("topic_id", this.id)
      .executeUpdate();
    }
  }

  public void update(String content, String user_id) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE topics SET content = :content, user_id =:user_id WHERE id=:id";
    con.createQuery(sql)
    .addParameter("content", content)
    .addParameter("user_id", user_id)
    .addParameter("id", this.id)
    .executeUpdate();
    }
  }

  public static List<Topic> all() {
    String sql = "SELECT * FROM topics";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Topic.class);
    }
  }

  public static Topic find(int id) {
  try(Connection con = DB.sql2o.open()) {
    String sql = "SELECT * FROM topics where id=:id";
    Topic topic = con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Topic.class);
    return topic;
    }
  }

  public void add(Message message) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO communications (topic_id, message_id) VALUES (:topic_id, :message_id)";
      con.createQuery(sql)
      .addParameter("topic_id", this.id)
      .addParameter("message_id", message.getId())
      .executeUpdate();
    }
  }

  public List<Message> getMessage() {
    try(Connection con = DB.sql2o.open()){
      String joinQuery = "SELECT communications.* FROM communications LEFT JOIN topics ON topics.id = communications.topic_id WHERE topic_id = :id";

      System.out.println("before the first con");
      List<Communication>  communications = con.createQuery(joinQuery)
      .addParameter("id", this.getId())
      .executeAndFetch(Communication.class);

      System.out.println("after the first con");

      List<Message> messages = new ArrayList<Message>();

      for (Communication communication : communications) {
        String recipesQuery = "SELECT * FROM messages WHERE id = :message_id";
        Message message = con.createQuery(recipesQuery)
        .addParameter("message_id", communication.getMessageId())
        .executeAndFetchFirst(Message.class);
        messages.add(message);
      }
      return messages;
    }
  }
}
