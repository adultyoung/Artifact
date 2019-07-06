package com.artifact.controller;

import com.artifact.model.CustomPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ResourceController {
    @GetMapping("/context")
    @PreAuthorize("hasAuthority('role_admin')")
    public String context() {
        CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication()
                                    .getPrincipal();
        return principal.getUsername() + " " + principal.getEmail();
    }

    @GetMapping("/secured")
    @PreAuthorize("hasAuthority('role_admin')")
    public String secured(CustomPrincipal principal) {
        return principal.getUsername() + " " + principal.getEmail();
    }

    @GetMapping("/resource")
    public String resource() {
        return UUID.randomUUID().toString();
    }
}