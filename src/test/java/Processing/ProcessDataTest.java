package Processing;

import GetData.GetObject;
import JsonObjects.*;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import static GetData.GetStringFromURL.readAll;
import static GetData.GetUsersAndPosts.connectUsersWithPosts;
import static Processing.ProcessData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(ZohhakRunner.class)
public class ProcessDataTest {
    private static List<User> userList;
    private static List<Post> postList;


    @Before
    public void createUsersTestList() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("src\\test\\java\\resources\\testing_list_of_users.txt"));
        String jsonUsersArrayAsString = readAll(reader);

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
        userList = listOfUsers;
    }

    @Before
    public void createPostsTestList() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src\\test\\java\\resources\\testing_list_of_posts.txt"));
        String jsonPostsArrayAsString = readAll(reader);

        JSONObject mainPostsObject = new JSONObject(jsonPostsArrayAsString);
        JSONArray postsArray = mainPostsObject.getJSONArray("posts");

        List<Post> listOfPosts = new ArrayList<>();

        for (int i = 0; i < postsArray.length(); i++) {

            Post post = GetObject.getPost(postsArray.getJSONObject(i));
            listOfPosts.add(post);

        }
        postList = listOfPosts;
    }

    @Test
    public void howManyPostsTest() {
        String expected = "Bret napisał(a) 3 postówAntonette napisał(a) 4 postówSamantha napisał(a) 3 postów" +
                "Karianne napisał(a) 3 postówKamren napisał(a) 2 postów";
        String result = "";
        for (String s : howManyPosts(connectUsersWithPosts(userList, postList))) {
            result = result + s;
        }
        assertEquals(result, expected);
    }

    @Test
    public void duplicatedTitlesTest() {
        String result = "powtórki: [magnam facilis autem, eveniet quod temporibus]";
        Set<String> duplicatedTitles = duplicatedTitles(postList);
        String expected = "powtórki: " + duplicatedTitles;
        assertEquals(result, expected);
    }

    @Test
    public void distanceTest() {
        double distance = distance(userList.get(0), userList.get(1));
        assertTrue(19_900 < distance && 20_100 > distance);
        distance = distance(userList.get(1), userList.get(2));
        assertTrue(9_900 < distance && 10_100 > distance);
        distance = distance(userList.get(2), userList.get(3));
        assertTrue(19_900 < distance && 20_100 > distance);

    }

    @Test
    public void findNearestUserTest() {
        String expected = "Chelsey DietrichChelsey DietrichClementine BauchClementine BauchLeanne Graham";

        Map<User, User> closestNeighbors = findNearestUser(userList);
        List<String> resultStrings = new ArrayList<>();

        for (Map.Entry<User, User> users : closestNeighbors.entrySet()) {
            resultStrings.add(users.getValue().getName());
        }
        Collections.sort(resultStrings);
        String result = "";
        for(String s : resultStrings){
            result = result + s;
        }
        System.out.println(result);
        assertEquals(expected,result);

    }

}
