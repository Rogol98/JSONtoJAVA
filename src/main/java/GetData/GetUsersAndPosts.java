package GetData;

import JsonObjects.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetUsersAndPosts {

    public static List<User> createUsersList() throws IOException {
        String jsonUsersArrayAsString = "{\"users\":" +
                GetStringFromURL.readJsonFromUrl("https://jsonplaceholder.typicode.com/users") + "}";

        List<User> listOfUsers = new ArrayList<>();

        JSONObject mainUsersObject = new JSONObject(jsonUsersArrayAsString);
        JSONArray usersArray = mainUsersObject.getJSONArray("users");

        for (int i = 0; i < usersArray.length(); i++) {

            JSONObject jsonUser = usersArray.getJSONObject(i);
            JSONObject jsonCompany = jsonUser.getJSONObject("company");
            JSONObject jsonAddress = jsonUser.getJSONObject("address");
            JSONObject jsonGeo = jsonAddress.getJSONObject("geo");

            Geo geo = GetObject.getGeo(jsonGeo);
            Address address = GetObject.getAddress(jsonAddress, geo);
            Company company = GetObject.getCompany(jsonCompany);
            User user = GetObject.getUser(jsonUser, address, company);

            listOfUsers.add(user);
        }
        return listOfUsers;
    }

    public static List<Post> createPostsList() throws IOException {
        String jsonPostsArrayAsString = "{\"posts\":" +
                GetStringFromURL.readJsonFromUrl("https://jsonplaceholder.typicode.com/posts") + "}";

        JSONObject mainPostsObject = new JSONObject(jsonPostsArrayAsString);
        JSONArray postsArray = mainPostsObject.getJSONArray("posts");

        List<Post> listOfPosts = new ArrayList<>();

        for (int i = 0; i < postsArray.length(); i++) {

            Post post = GetObject.getPost(postsArray.getJSONObject(i));
            listOfPosts.add(post);

        }
        return listOfPosts;
    }

    public static List<User> connectUsersWithPosts(List<User> listOfUsers, List<Post> listOfPosts) {
        for (Post post : listOfPosts) {
            listOfUsers.get(post.getUserId() - 1).addPost(post);
        }
        return listOfUsers;
    }

}
