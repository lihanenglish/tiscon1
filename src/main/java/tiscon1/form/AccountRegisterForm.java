package tiscon1.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author kawasima
 */
@Data
public class AccountRegisterForm implements Serializable {
    @Size(min = 1, max = 100)
    @NotEmpty
    private String name;


    @NotEmpty
    private String email;

    @Size(min = 6,max = 20)
    @NotEmpty
    private String password;

}
