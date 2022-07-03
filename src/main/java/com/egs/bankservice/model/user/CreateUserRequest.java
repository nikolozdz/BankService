package com.egs.bankservice.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
public class CreateUserRequest {

    @JsonProperty(required = true)
    private String personalId;

    @JsonProperty(required = true)
    private String firstName;

    @JsonProperty(required = true)
    private String lastName;

    @JsonProperty
    private String address;

    @JsonProperty
    private String email;

    @JsonProperty(required = true)
    private Date dateOfBirth;

}
