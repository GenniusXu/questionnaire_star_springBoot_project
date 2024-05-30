package mygroup.questionnaire_star.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class BaiduAIUitil {

    private static final String API_KEY = "w910LWvmPORSKwZRLBO6VFqH";
    private static final String SECRET_KEY = "eCFylaDJDvsZ7Lr1DqFrHpl4GvtSRIfV";
    private static final String TOKEN_URL = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=" + API_KEY + "&client_secret=" + SECRET_KEY;
    private static final String CHAT_URL = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/eb-instant?access_token=";

    public static String getAccessToken() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(TOKEN_URL);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Accept", "application/json");

        CloseableHttpResponse response = httpClient.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> tokenMap = objectMapper.readValue(result, new TypeReference<Map<String, String>>() {});
        return tokenMap.get("access_token");
    }

    public static String chat(String prompt) throws Exception {
        String accessToken = getAccessToken();
        String url = CHAT_URL + accessToken;

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json");

        StringEntity entity = new StringEntity("{\"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}");
        httpPost.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(httpPost);
        String jsonResponse = EntityUtils.toString(response.getEntity());
        System.out.println(jsonResponse);

        //仅仅提取回复中的result字段（即正常语言部分），其他状态字段不要
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            String result = rootNode.get("result").asText();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
