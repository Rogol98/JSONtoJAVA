package GetData;

import static GetData.GetUsersAndPosts.*;
import static org.assertj.core.api.Assertions.assertThat;
import JsonObjects.Post;
import JsonObjects.User;
import com.googlecode.zohhak.api.TestWith;
import com.googlecode.zohhak.api.runners.ZohhakRunner;
import org.junit.runner.RunWith;
import java.io.IOException;
import java.util.List;

@RunWith(ZohhakRunner.class)
public class GetUsersAndPostsTest {


    @TestWith({
            "1,1", "2,2", "5,5", "8,8", "9,9", "10,10"
    })
    public void connectUsersWithPostsTest(int userId, int idOfUser) throws IOException {
        List<User> userList = connectUsersWithPosts(createUsersList(), createPostsList());
        for (Post post : userList.get(userId - 1).getListOfPosts()) {
            assertThat(post.getUserId()).isEqualTo(idOfUser);
        }

    }

    @TestWith({
            "1,1,sunt aut facere repellat provident occaecati excepturi optio reprehenderit  ," +
                    " quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto",
            "1,2,qui est esse," +
                    "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla",
            "1,10,optio molestias id quia eum," +
                    "quo et expedita modi cum officia vel magni\ndoloribus qui repudiandae\nvero nisi sit\nquos veniam quod sed accusamus veritatis error",
            "5,47,quibusdam cumque rem aut deserunt," +
                    " voluptatem assumenda ut qui ut cupiditate aut impedit veniam\noccaecati nemo illum voluptatem laudantium\nmolestiae beatae rerum ea iure soluta nostrum\neligendi et voluptate",
            "10,100,at nam consequatur ea labore ea harum," +
                    "cupiditate quo est a modi nesciunt soluta\nipsa voluptas error itaque dicta in\nautem qui minus magnam et distinctio eum\naccusamus ratione error aut"
    })
    public void createPostsListTest(int userId, int postId, String title, String body) throws IOException {
        List<Post> postList = createPostsList();
        Post post = postList.get(postId - 1);
        assertThat(post.getUserId()).isEqualTo(userId);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getId()).isEqualTo(postId);
        assertThat(post.getBody()).isEqualTo(body);

    }

    @TestWith({
            "1, Leanne Graham, Bret, Sincere@april.biz, Kulas Light, Apt. 556, Gwenborough, 92998-3874, -37.3159, 81.1496," +
                    "1-770-736-8031 x56442, hildegard.org, Romaguera-Crona, Multi-layered client-server neural-net, harness real-time e-markets ",
            "10, Clementina DuBuque, Moriah.Stanton, Rey.Padberg@karina.biz, Kattie Turnpike, Suite 198, Lebsackbury, 31428-2261, -38.2386, 57.2232," +
                    "024-648-3804, ambrose.net, Hoeger LLC, Centralized empowering task-force, target end-to-end models "

    })
    public static void createUsersListTest(int id, String name, String username, String email, String street,
                                           String suite, String city, String zipcode, double lat, double lng, String phone,
                                           String website, String companyName, String catchPhrase, String bs) throws IOException {
        List<User> userList = createUsersList();
        User user = userList.get(id-1);
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getAddress().getStreet()).isEqualTo(street);
        assertThat(user.getAddress().getSuite()).isEqualTo(suite);
        assertThat(user.getAddress().getCity()).isEqualTo(city);
        assertThat(user.getAddress().getZipcode()).isEqualTo(zipcode);
        assertThat(user.getAddress().getGeo().getLat()).isEqualTo(lat);
        assertThat(user.getAddress().getGeo().getLng()).isEqualTo(lng);
        assertThat(user.getPhone()).isEqualTo(phone);
        assertThat(user.getWebsite()).isEqualTo(website);
        assertThat(user.getCompany().getName()).isEqualTo(companyName);
        assertThat(user.getCompany().getCatchPhrase()).isEqualTo(catchPhrase);
        assertThat(user.getCompany().getBs()).isEqualTo(bs);

    }


}