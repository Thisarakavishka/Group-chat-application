package entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class user{
    private String userName;
    private String password;
    private String status;
}
