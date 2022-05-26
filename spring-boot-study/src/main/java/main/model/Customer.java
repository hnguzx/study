package main.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 志雄
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Integer id;
    private String username;
    private String password;
}
