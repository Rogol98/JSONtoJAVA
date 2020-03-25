
import GetData.GetObject;
import GetData.GetStringFromURL;
import JsonObjects.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

public class Main {

    private static List<User> createUsersList() throws IOException {
        String jsonUsersArrayAsString = "{\"users\":" +
                GetStringFromURL.readJsonFromUrl("https://jsonplaceholder.typicode.com/users") + "}";

        List<User> listOfUsers = new ArrayList<User>();

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

    private static List<Post> createPostsList() throws IOException {
        String jsonPostsArrayAsString = "{\"posts\":" +
                GetStringFromURL.readJsonFromUrl("https://jsonplaceholder.typicode.com/posts") + "}";

        JSONObject mainPostsObject = new JSONObject(jsonPostsArrayAsString);
        JSONArray postsArray = mainPostsObject.getJSONArray("posts");

        List<Post> listOfPosts = new ArrayList<Post>();

        for (int i = 0; i < postsArray.length(); i++) {

            Post post = GetObject.getPost(postsArray.getJSONObject(i));
            listOfPosts.add(post);

        }
        return listOfPosts;
    }

    private static List<User> connectUsersWithPosts(List<User> listOfUsers, List<Post> listOfPosts) throws IOException {
        for (Post p : listOfPosts) {
            listOfUsers.get(p.getUserId() - 1).addPost(p);
        }
        return listOfUsers;
    }

    private static List<String> howManyPosts(List<User> listOfUsers) {
        List<String> whoWriteHowMany = new ArrayList<>();
        for (int i = 0; i < listOfUsers.size(); i++) {
            whoWriteHowMany.add(listOfUsers.get(i).getUsername() + " napisał(a) " + listOfUsers.get(i).getListOfPosts().size() + " postów");
        }
        System.out.println(whoWriteHowMany);
        return whoWriteHowMany;
    }

    private static Set<String> duplicatedPosts(List<Post> postList) throws IOException {

        Set<String> duplicatedPosts = new HashSet<String>();
        List<String> listOfPostsTitles = new ArrayList<String>();

        for (int i = 0; i < postList.size(); i++) {
            String postTitle = postList.get(i).getTitle();
            if (listOfPostsTitles.contains(postTitle)) {
                duplicatedPosts.add(postTitle);
            } else {
                listOfPostsTitles.add(postTitle);
            }
        }
        System.out.println("powtórki: " + duplicatedPosts);
        return duplicatedPosts;
    }

    public static double distance(User user1, User user2) {
        double lon1 = user1.getAddress().getGeo().getLng();
        double lat1 = user1.getAddress().getGeo().getLat();
        double lon2 = user2.getAddress().getGeo().getLng();
        double lat2 = user2.getAddress().getGeo().getLat();

        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double angle = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * angle;


        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    public static Map<User, User> findNearestUser() throws IOException {

        Map<User, User> closestNeighbor = new HashMap<>();
        List<User> userList = createUsersList();


        for (int i = 0; i < userList.size(); i++) {
            double shortestDistance = 100_000;
            int indexOfUser = 0;
            for (int j = 0; j < userList.size(); j++) {
                if (i != j) {
                    if (distance(userList.get(i), userList.get(j)) < shortestDistance) {
                        shortestDistance = distance(userList.get(i), userList.get(j));
                        indexOfUser = j;
                    }
                }
            }
            closestNeighbor.put(userList.get(i), userList.get(indexOfUser));

        }

        System.out.println(closestNeighbor);
        return closestNeighbor;
    }


    public static void main(String[] args) throws IOException, JSONException {

        connectUsersWithPosts(createUsersList(), createPostsList());

        howManyPosts(connectUsersWithPosts(createUsersList(), createPostsList()));

        duplicatedPosts(createPostsList());

        findNearestUser();


    }

}






