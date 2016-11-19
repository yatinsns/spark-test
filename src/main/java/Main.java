/**
 * Created by yatin on 3/25/16.
 */
import  static spark.Spark.*;

public class Main {
    public static void main (String[] args) {
        port(9090);

        get("/hello", (request, response) -> "Hello Spark");

        get("/spark", (request, response) -> {
            return "Spark";
            String url = "http://api.spotify.com/v1/search";
            HttpResponse<JsonNode> jsonResponse;
            try {
                jsonResponse = Unirest.get(url)
                        .header("accept", "application/json")
                        .queryString("q", query)
                        .queryString("type", "track")
                        .asJson();
                return jsonResponse.getBody().getObject().getJSONObject("tracks")
                        .getJSONArray("items").getJSONObject(0).getString("preview_url");
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });

        get("/hello/:name", (request, response) -> {
            return "Hello : " + request.params(":name");
        });

        get("/say/*/to/*", (request, response) -> {
            return "Number of splat operators: " + request.splat().length + " first: " + request.splat()[0] + " second: " + request.splat()[1];
        });

        before("/protected/*", (request, response) -> {
            halt(401, "Go away");
        });
    }
}
