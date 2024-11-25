package sk.tuke.kpi.kp.Server.Webservice.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {

    private final ObjectMapper objectMapper;

    public JsonConverter() {
        this.objectMapper = new ObjectMapper();
    }

    public String convertFieldsToJson(char[][] field1, char[][] field2, String token, String[] playerNames) {
        try {
            Fields fields = new Fields(field1, field2, token, playerNames);
            return objectMapper.writeValueAsString(fields);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    static class Fields {
        private char[][] field1;
        private char[][] field2;
        private String token;
        private String[] playerNames;

        public Fields(char[][] field1, char[][] field2, String token, String[] playerNames) {
            this.field1 = field1;
            this.field2 = field2;
            this.token = token;
            this.playerNames = playerNames;
        }

        public char[][] getField1() {
            return field1;
        }

        public char[][] getField2() {
            return field2;
        }

        public String getToken() {
            return token;
        }

        public String[] getPlayerNames() {
            return playerNames;
        }
    }
}
