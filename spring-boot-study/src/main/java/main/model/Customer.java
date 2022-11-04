package main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 志雄
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {
    private Integer id;
    private String userName;
    private String passWord;
}
