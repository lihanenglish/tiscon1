package tiscon1.model.modeltop10;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tiscon1.model.modeltop10.Itemtop10;

/**
 * 音楽情報格納用Model。
 *
 * @author fujiwara
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Musictop10 extends Itemtop10 {
    private String artist;
    private String album;
}
