package tiscon1.model.modeltop10;

import tiscon1.model.modeltop10.Itemtop10;

        import java.io.IOException;
        import java.util.List;

/**
 * @author fujiwara
 */
public interface CategoryRepositorytop10 {
    List<Itemtop10> findTop10(String genreId, String subgenreId) throws IOException;

    Itemtop10 searchItemtop10(String genreId, String id) throws IOException;
}
