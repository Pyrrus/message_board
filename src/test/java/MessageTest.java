import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.sql.Timestamp;
import java.util.Date;

public class MessageTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void message_instantiatesCorrectly_true() {
    Message testMessage = new Message("This topic sucks", 1);
    assertEquals(true, testMessage instanceof Message);
  }

  @Test
  public void getTag_messageInstantiatesWithTag_fast() {
    Message testMessage = new Message("This topic sucks", 1);
    assertEquals("This topic sucks", testMessage.getContent());
  }

  @Test
  public void all_returnsAllInstancesOfMessage_true() {
    Message firstMessage = new Message("This topic sucks", 1);
    firstMessage.save();
    Message secondMessage = new Message("This topic rocks", 1);
    secondMessage.save();
    assertEquals(true, Message.all().get(0).equals(firstMessage));
    assertEquals(true, Message.all().get(1).equals(secondMessage));
  }

  @Test
  public void getId_categoriesInstantiateWithAnId_1() {
    Message firstMessage = new Message("This topic is okay.", 1);
    firstMessage.save();
    assertTrue(firstMessage.getId() > 0);
  }

  @Test
  public void find_returnsMessageWithSameId_secondMessage() {
    Message firstMessage = new Message("This topic sucks", 1);
    firstMessage.save();
    Message secondMessage = new Message("This topic rocks", 1);
    secondMessage.save();
    assertEquals(Message.find(secondMessage.getId()), secondMessage);
  }

  @Test
  public void equals_returnsTrueIfContentsAretheSame() {
    Message firstMessage = new Message("This topic sucks", 1);
    Message secondMessage = new Message("This topic sucks", 1);
    assertTrue(firstMessage.equals(secondMessage));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Message myMessage = new Message("This topic is okay.", 1);
    myMessage.save();
    assertTrue(Message.all().get(0).equals(myMessage));
  }

  @Test
  public void save_assignsIdToObject() {
    Message myMessage = new Message("This topic is okay.", 1);
    myMessage.save();
    Message savedMessage = Message.all().get(0);
    assertEquals(myMessage.getId(), savedMessage.getId());
  }

  @Test
    public void save_recordsTimeOfCreationInDatabase() {
      Message testMessage = new Message("This topic sucks", 1);
      testMessage.save();
      Timestamp savedTestMessage = Message.find(testMessage.getId()).getCreatedAt();
      Timestamp rightNow = new Timestamp(new Date().getTime());
      assertEquals(rightNow.getDay(), savedTestMessage.getDay());
  }

  @Test
  public void likes_ownException() {
    Message testMessage = new Message("This topic sucks", 1);
    testMessage.save();
    try {
        testMessage.likes(1);
      } catch (UnsupportedOperationException exception){ }
    assertTrue(0 == testMessage.getLikes());
  }

  @Test
  public void likes_gainLikes() {
    Message testMessage = new Message("This topic sucks", 1);
    testMessage.save();
    try {
        testMessage.likes(2);
      } catch (UnsupportedOperationException exception){ }
    assertTrue(1 == testMessage.getLikes());
  }

  @Test
  public void likes_OnlyOneLikesperUser() {
    Message testMessage = new Message("This topic sucks", 1);
    testMessage.save();
    try {
        testMessage.likes(2);
        testMessage.likes(2);
      } catch (UnsupportedOperationException exception){ }
    assertTrue(1 == testMessage.getLikes());
  }


}
