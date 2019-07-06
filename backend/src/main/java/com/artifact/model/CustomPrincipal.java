package com.artifact.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class CustomPrincipal implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String email;

}
