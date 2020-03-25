package GetData;

import JsonObjects.*;
import org.json.JSONException;
import org.json.JSONObject;

public class GetObject {
    public static Geo getGeo(JSONObject jsonGeo) throws JSONException {
        Geo geo = new Geo();
        geo.setLat(jsonGeo.getDouble("lat"));
        geo.setLng(jsonGeo.getDouble("lng"));
        return geo;
    }

    public static Address getAddress(JSONObject jsonAddress, Geo geo) throws JSONException {
        Address address = new Address();
        address.setGeo(geo);
        address.setStreet(jsonAddress.getString("street"));
        address.setSuite(jsonAddress.getString("suite"));
        address.setCity(jsonAddress.getString("city"));
        address.setZipcode(jsonAddress.getString("zipcode"));
        return address;
    }

    public static Company getCompany(JSONObject jsonCompany) throws JSONException {
        Company comapny = new Company();
        comapny.setName(jsonCompany.getString("name"));
        comapny.setCatchPhrase(jsonCompany.getString("catchPhrase"));
        comapny.setBs(jsonCompany.getString("bs"));
        return comapny;
    }

    public static User getUser(JSONObject jsonUser, Address address, Company company) throws JSONException {
        User user = new User();
        user.setId(jsonUser.getInt("id"));
        user.setName(jsonUser.getString("name"));
        user.setUsername(jsonUser.getString("username"));
        user.setEmail(jsonUser.getString("email"));
        user.setAddress(address);
        user.setPhone(jsonUser.getString("phone"));
        user.setWebsite(jsonUser.getString("website"));
        user.setCompany(company);
        return user;
    }

    public static Post getPost(JSONObject jsonPost){
        Post post = new Post();
        post.setUserId(jsonPost.getInt("userId"));
        post.setId(jsonPost.getInt("id"));
        post.setTitle(jsonPost.getString("title"));
        post.setBody(jsonPost.getString("body"));
        return post;
    }

}
