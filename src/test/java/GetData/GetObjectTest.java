package GetData;

import static GetData.GetObject.*;
import static org.assertj.core.api.Assertions.assertThat;
import JsonObjects.*;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.runner.RunWith;
import java.io.IOException;

@RunWith(ZohhakRunner.class)
public class GetObjectTest {
    private static JSONArray usersArray;
    private static JSONArray postsArray;

    @Before
    public void setup() throws IOException {
        String jsonUsersArrayAsString = "{\"users\":" +
                GetStringFromURL.readJsonFromUrl("https://jsonplaceholder.typicode.com/users") + "}";
        JSONObject mainUsersObject = new JSONObject(jsonUsersArrayAsString);
        usersArray = mainUsersObject.getJSONArray("users");

        String jsonPostsArrayAsString = "{\"posts\":" +
                GetStringFromURL.readJsonFromUrl("https://jsonplaceholder.typicode.com/posts") + "}";
        JSONObject mainPostsObject = new JSONObject(jsonPostsArrayAsString);
        postsArray = mainPostsObject.getJSONArray("posts");

    }

    @TestWith({
            "1,-37.315981.1496",
            "2,-43.9509-34.4618",
            "10,-38.238657.2232"
    })
    public static void getGeoTest(int userId, String expected) {
        JSONObject jsonUser = usersArray.getJSONObject(userId - 1);
        JSONObject jsonAddress = jsonUser.getJSONObject("address");
        JSONObject jsonGeo = jsonAddress.getJSONObject("geo");
        Geo geo = getGeo(jsonGeo);
        String result = geo.getLat() + String.valueOf(geo.getLng());
        assertThat(result).isEqualTo(expected);
    }

    @TestWith({
            "1,Kulas LightApt. 556Gwenborough92998-3874-37.315981.1496",
            "10,Kattie TurnpikeSuite 198Lebsackbury31428-2261-38.238657.2232"
    })
    public static void getAddressTest(int userId, String expected) {
        JSONObject jsonUser = usersArray.getJSONObject(userId - 1);
        JSONObject jsonAddress = jsonUser.getJSONObject("address");
        JSONObject jsonGeo = jsonAddress.getJSONObject("geo");
        Address address = getAddress(jsonAddress, getGeo(jsonGeo));
        String result = address.getStreet() + address.getSuite() + address.getCity() + address.getZipcode() +
                address.getGeo().getLat() + address.getGeo().getLng();
        assertThat(result).isEqualTo(expected);
    }

    @TestWith({
            "1,Romaguera-CronaMulti-layered client-server neural-netharness real-time e-markets",
            "10,Hoeger LLCCentralized empowering task-forcetarget end-to-end models"
    })
    public static void getCompanyTest(int userId, String expected) {
        JSONObject jsonUser = usersArray.getJSONObject(userId - 1);
        JSONObject jsonCompany = jsonUser.getJSONObject("company");
        Company company = getCompany(jsonCompany);
        String result = company.getName() + company.getCatchPhrase() + company.getBs();
        assertThat(result).isEqualTo(expected);
    }

    @TestWith({
            "1,11" +
                    "sunt aut facere repellat provident occaecati excepturi optio reprehenderit" +
                    "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit" +
                    " molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto",
            "100,10100at nam consequatur ea labore ea harum" + "cupiditate quo est a modi nesciunt soluta\nipsa voluptas "+
                    "error itaque dicta in\nautem qui minus magnam et distinctio eum\naccusamus ratione error aut"
    })
    public static void getPostTest(int postId, String expected) {
        JSONObject jsonPost = postsArray.getJSONObject(postId - 1);
        Post post = getPost(jsonPost);
        String result = String.valueOf(post.getUserId()) + post.getId() + post.getTitle() + post.getBody();
        assertThat(result).isEqualTo(expected);
    }

    @TestWith({
            "1,1Gwenborough81.1496Romaguera-Crona",
            "6,6South Christy71.7478Considine-Lockman",
            "10,10Lebsackbury57.2232Hoeger LLC"
    })
    public static void getUserTest(int userId, String expected) {
        JSONObject jsonUser = usersArray.getJSONObject(userId - 1);
        JSONObject jsonCompany = jsonUser.getJSONObject("company");
        JSONObject jsonAddress = jsonUser.getJSONObject("address");
        JSONObject jsonGeo = jsonAddress.getJSONObject("geo");
        Geo geo = getGeo(jsonGeo);
        Address address = getAddress(jsonAddress, geo);
        Company company = getCompany(jsonCompany);
        User user = getUser(jsonUser, address, company);

        String result = user.getId() + user.getAddress().getCity() +
                user.getAddress().getGeo().getLng() + user.getCompany().getName();
        assertThat(result).isEqualTo(expected);
    }

}
