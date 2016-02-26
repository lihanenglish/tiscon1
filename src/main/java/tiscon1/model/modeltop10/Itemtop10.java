package tiscon1.model.modeltop10;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * 商品格納用抽象化Model。
 *
 * @author fujiwara
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public abstract class Itemtop10 implements Serializable {
    @NonNull
    private String id;

    private String title;

    private String image;

    private String price;

    private String genre;

    private String releaseDate;
}
