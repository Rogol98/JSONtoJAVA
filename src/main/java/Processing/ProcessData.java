package Processing;

import JsonObjects.Post;
import JsonObjects.User;
import java.io.IOException;
import java.util.*;

public class ProcessData {

    public static List<String> howManyPosts(List<User> listOfUsers) {
        List<String> whoWriteHowMany = new ArrayList<>();
        for (User user : listOfUsers) {
            whoWriteHowMany.add(user.getUsername() + " napisał(a) " + user.getListOfPosts().size() + " postów");
        }
        System.out.println(whoWriteHowMany);
        return whoWriteHowMany;
    }

    public static Set<String> duplicatedTitles(List<Post> postList) {

        Set<String> duplicatedTitles = new HashSet<>();
        List<String> listOfPostsTitles = new ArrayList<>();

        for (int i = 0; i < postList.size(); i++) {
            String postTitle = postList.get(i).getTitle();
            if (listOfPostsTitles.contains(postTitle)) {
                duplicatedTitles.add(postTitle);
            } else {
                listOfPostsTitles.add(postTitle);
            }
        }
        System.out.println("powtórki: " + duplicatedTitles);
        return duplicatedTitles;
    }

    public static double distance(User user1, User user2) {
        double lng1 = user1.getAddress().getGeo().getLng();
        double lat1 = user1.getAddress().getGeo().getLat();
        double lng2 = user2.getAddress().getGeo().getLng();
        double lat2 = user2.getAddress().getGeo().getLat();

        final int R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lngDistance = Math.toRadians(lng2 - lng1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double angle = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * angle;


        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    public static Map<User, User> findNearestUser(List<User> userList) {

        Map<User, User> closestNeighbor = new HashMap<>();

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
}
