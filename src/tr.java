import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class tr {

    public static void main(String[] args) {
        String str = "what are you doing man?".replaceAll(" ", "+");
        String strRu = "делаю проект".replaceAll(" ", "+");

        try {
            String string = getJsonStringYandex("en-ru", str);
            String string2 = getJsonStringYandex("ru-en", strRu);
            System.out.println(string);
            System.out.println(string2);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public static String getJsonStringYandex(String trans, String text) throws IOException, ParseException {
        String apiKey = "trnsl.1.1.20161220T135643Z.27f884ebff787023.6782a13b703f4203df9dd0292ca12338b0699ed4";
        String requestUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key="
                + apiKey + "&lang=" + trans + "&text=" + text;

        URL url = new URL(requestUrl);
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        httpConnection.connect();
        int rc = httpConnection.getResponseCode();
        System.out.println(rc);

        if (rc == 200) {
            System.out.println("ok");
            String line = null;
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            StringBuilder strBuilder = new StringBuilder();
            while ((line = buffReader.readLine()) != null) {
                strBuilder.append(line + '\n');
            }
            return getTranslateFromJSON(strBuilder.toString());
        }
        return "Done";
    }

    public static String getTranslateFromJSON(String str) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(str);
        StringBuilder sb = new StringBuilder();
        JSONArray array = (JSONArray) object.get("text");
        for (Object s : array) {
            sb.append(s.toString() + "\n");
        }
        return sb.toString();
    }

}