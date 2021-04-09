package com.demka.demkaserver.entities.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdatePasswordEntity {
    private String email;
    private String masterKey;
    private String newPassword;
}