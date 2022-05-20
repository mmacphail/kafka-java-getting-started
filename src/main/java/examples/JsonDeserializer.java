package examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.kafka.common.serialization.Deserializer;

public class JsonDeserializer implements Deserializer<UserItem> {
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public UserItem deserialize(String topic, byte[] data) {
    try {
      return objectMapper.readValue(data, UserItem.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
