package tiscon1.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tiscon1.exception.SystemException;
import tiscon1.model.modeltop10.Itemtop10;
import tiscon1.model.modeltop10.Movietop10;
import tiscon1.model.modeltop10.Musictop10;
import tiscon1.model.modeltop10.CategoryRepositorytop10;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author fujiwara
 */
@Component
public class CategoryRepositoryImpltop10 implements CategoryRepositorytop10 {
    /**
     * 検索用APIアドレス
     */
    public static final String MOVIE_ID = "33";
    public static final String MUSIC_ID = "34";
    static final String SEARCH_URL = "https://itunes.apple.com/jp/rss/top{genreName}/limit=10/genre={subgenreId}/json";
    static final String LOOKUP_ID_URL = "https://itunes.apple.com/lookup?country=JP&id={id}";

    /**
     * プロキシ設定を必要とする場合のRestTemplate生成メソッド。
     *
     * @param host   ホスト名
     * @param portNo ポート番号
     * @return プロキシが設定されたrestTemplate
     */
    private RestTemplate myRest(String host, int portNo) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, portNo)));

        return new RestTemplate(factory);

    }

    private String getGenreName(String genreId) {
        if (genreId == null) {
            throw new IllegalArgumentException("geneId is null");
        } else switch (genreId) {
            case MOVIE_ID: return "movies";
            case MUSIC_ID: return "songs";
            default: throw new IllegalArgumentException("Unknown genre: " + genreId);
        }
    }

    @Override
    public List<Itemtop10> findTop10(String genreId, String subgenreId) throws IOException {
        // プロキシ設定が不要の場合
        RestTemplate rest = new RestTemplate();
        // プロキシ設定が必要の場合
        // RestTemplate rest = myRest("proxy.co.jp", 8080);

        String jsonString = rest.getForObject(SEARCH_URL, String.class, getGenreName(genreId), subgenreId);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> top10Map = (Map<String, Object>) mapper.readValue(jsonString, Map.class).get("feed");
        List<Map<String, Object>> top10List = (List<Map<String, Object>>) top10Map.get("entry");

        List<Itemtop10> top10 = new ArrayList<Itemtop10>();
        for (Map<String, Object> map : top10List) {
            Map<String, Map<String, Object>> mapId = (Map<String, Map<String, Object>>) map.get("id");
            top10.add(searchItemtop10(genreId, (String) mapId.get("attributes").get("im:id")));
        }
        return top10;
    }

    public Itemtop10 searchItemtop10(String genreId, String id) throws IOException {
        // プロキシ設定が不要の場合
        RestTemplate rest = new RestTemplate();
        // プロキシ設定が必要の場合
        // RestTemplate rest = myRest("proxy.co.jp", 8080);

        String jsonString = rest.getForObject(LOOKUP_ID_URL, String.class, id);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> mapItem = (Map<String, Object>) ((List<Object>) mapper.readValue(jsonString, Map.class).get("results")).get(0);

        // 参照する画像ファイルの大きさを変更
        String imageUrl = (String) mapItem.get("artworkUrl100");
        imageUrl = imageUrl.replaceAll("100x100bb.jpg", "400x400bb.jpg");

        if (genreId.equals(MOVIE_ID)) {
            Movietop10 movietop10 = new Movietop10();
            movietop10.setId(id);
            movietop10.setTitle((String) mapItem.get("trackName"));
            movietop10.setImage(imageUrl);
            movietop10.setSummary((String) mapItem.get("longDescription"));
            Double price = (Double) mapItem.get("collectionPrice");
            movietop10.setPrice(String.valueOf(price.intValue()));
            movietop10.setGenre((String) mapItem.get("primaryGenreName"));
            movietop10.setReleaseDate((String) mapItem.get("releaseDate"));
            return movietop10;
        } else if (genreId.equals(MUSIC_ID)) {
            Musictop10 musictop10 = new Musictop10();
            musictop10.setId(id);
            musictop10.setTitle((String) mapItem.get("trackName"));
            musictop10.setImage(imageUrl);
            musictop10.setArtist((String) mapItem.get("artistName"));
            Double price = (Double) mapItem.get("trackPrice");
            musictop10.setPrice(String.valueOf(price.intValue()));
            musictop10.setGenre((String) mapItem.get("primaryGenreName"));
            musictop10.setReleaseDate((String) mapItem.get("releaseDate"));
            return musictop10;
        } else {
            throw new SystemException();
        }
    }
}
