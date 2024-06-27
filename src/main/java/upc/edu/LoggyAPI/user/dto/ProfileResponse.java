package upc.edu.LoggyAPI.user.dto;

import lombok.Data;

@Data
public class ProfileResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String genre;
    private String birthdate;
    private String address;
    private Long user;
}
