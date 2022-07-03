package com.egs.bankservice.shared.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDto implements Serializable {

    private long id;

    private String firstName;

    private String lastName;

    private String personalId;

    private String address;

    private String email;

    private Date dateOfBirth;
}
