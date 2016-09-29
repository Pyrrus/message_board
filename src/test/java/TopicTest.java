import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;
import java.sql.Timestamp;
import java.util.Date;

public class TopicTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void topic_instantiatesCorrectly_true() {
    Topic testTopic = new Topic("Why is the sky blue?", "Been wondering about the sky and came up with a question", 1);
    assertEquals(true, testTopic instanceof Topic);
  }

  @Test
  public void getTag_topicInstantiatesWithTitle() {
    Topic testTopic = new Topic("Why is the sky blue?", "Been wondering about the sky and came up with a question", 1);
    assertEquals("Why is the sky blue?", testTopic.getTitle());
  }

  @Test
  public void getTag_topicInstantiatesWithContent() {
    Topic testTopic = new Topic("Why is the sky blue?", "Been wondering about the sky and came up with a question", 1);
    assertEquals("Been wondering about the sky and came up with a question", testTopic.getContent());
  }

  @Test
  public void all_returnsAllInstancesOfTopic_true() {
    Topic firstTopic = new Topic("Why is the sky blue?", "Been wondering about the sky and came up with a question", 1);
    firstTopic.save();
    Topic secondTopic = new Topic("Why is an orange orange?", "Been wondering about oranges and came up with a question", 1);
    secondTopic.save();
    assertEquals(true, Topic.all().get(0).equals(firstTopic));
    assertEquals(true, Topic.all().get(1).equals(secondTopic));
  }

  @Test
  public void getId_categoriesInstantiateWithAnId_1() {
    Topic testTopic = new Topic("Why is the sky blue?", "Been wondering about the sky and came up with a question", 1);
    testTopic.save();
    assertTrue(testTopic.getId() > 0);
  }

  @Test
  public void find_returnsTopicWithSameId_secondTopic() {
    Topic firstTopic = new Topic("Why is the sky blue?", "Been wondering about the sky and came up with a question", 1);
    firstTopic.save();
    Topic secondTopic = new Topic("Why is an orange orange?", "Been wondering about oranges and came up with a question", 1);
    secondTopic.save();
    assertEquals(Topic.find(secondTopic.getId()), secondTopic);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Topic firstTopic = new Topic("Why is the sky blue?", "Been wondering about the sky and came up with a question", 1);
    Topic secondTopic = new Topic("Why is the sky blue?", "Been wondering about the sky and came up with a question", 1);
    assertTrue(firstTopic.equals(secondTopic));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Topic myTopic = new Topic("Why is the sky blue?", "Been wondering about the sky and came up with a question", 1);
    myTopic.save();
    assertTrue(Topic.all().get(0).equals(myTopic));
  }

  @Test
  public void save_assignsIdToObject() {
    Topic myTopic = new Topic("Why is the sky blue?", "Been wondering about the sky and came up with a question", 1);
    myTopic.save();
    Topic savedTopic = Topic.all().get(0);
    assertEquals(myTopic.getId(), savedTopic.getId());
  }

  @Test
    public void save_recordsTimeOfCreationInDatabase() {
      Topic testTopic = new Topic("Why is the sky blue?", "Been wondering about the sky and came up with a question", 1);
      testTopic.save();
      Timestamp savedTestTopic = Topic.find(testTopic.getId()).getCreatedAt();
      Timestamp rightNow = new Timestamp(new Date().getTime());
      assertEquals(rightNow.getDay(), savedTestTopic.getDay());
  }

  @Test
  public void getMessage_manyToMany () {
    Topic testTopic = new Topic("Why is the sky blue?", "Been wondering about the sky and came up with a question", 1);
    testTopic.save();
    Message myMessage = new Message("This topic is okay.", 1);
    myMessage.save();
    testTopic.add(myMessage);
    assertTrue(myMessage.equals(testTopic.getMessage().get(0)));
  }

  // @Test
  // public void addRecipe_addsRecipeToTopic() {
  //   Topic testTopic = new Topic("Why is the sky blue?", "Been wondering about the sky and came up with a question", 1);
  //   testTopic.save();
  //   Recipes testRecipe = new Recipes("Chicken and Rice", "One whole chicken, 2 cups of rice, 4 cups of Water", "1. Cook Chicken in Oven 350 degrees (until white). 2. Boil rice in rice-cooker. 3. Season rice and chicken. 4. Put rice and chiken together.");
  //   testRecipe.save();
  //   testTopic.addRecipe(testRecipe);
  //   Recipes savedRecipe = testTopic.getRecipe().get(0);
  //   assertTrue(testRecipe.equals(savedRecipe));
  // }

}
