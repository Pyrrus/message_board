import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Communication {
  private int topic_id;
  private int message_id;
  private int id;

  public Communication (int topic_id, int message_id) {
    this.topic_id = topic_id;
    this.message_id = message_id;
  }

  public int getTopicId() {
    return topic_id;
  }

  public int getMessageId() {
    return message_id;
  }


}
