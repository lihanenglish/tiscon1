package tiscon1.repository;

import tiscon1.model.Item;


import java.io.IOException;
import java.util.List;

/**
 * @author fujiwara
 */
public interface CategoryRepository {
    List<Item> findTop10(String genreId, String subgenreId) throws IOException;

    Item searchItem(String genreId, String id) throws IOException;
}
