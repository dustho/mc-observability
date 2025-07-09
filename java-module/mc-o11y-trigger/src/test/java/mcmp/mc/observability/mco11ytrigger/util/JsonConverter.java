package mcmp.mc.observability.mco11ytrigger.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
  private static ObjectMapper objectMapper = new ObjectMapper();

  public static String asJsonString(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
