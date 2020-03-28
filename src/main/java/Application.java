import java.io.IOException;

import static GetData.GetUsersAndPosts.*;
import static Processing.ProcessData.*;

public class Application {
    public static void main(String[] args) throws IOException {

        connectUsersWithPosts(createUsersList(), createPostsList());

        howManyPosts(connectUsersWithPosts(createUsersList(), createPostsList()));

        duplicatedTitles(createPostsList());

        findNearestUser(createUsersList());

    }
}
