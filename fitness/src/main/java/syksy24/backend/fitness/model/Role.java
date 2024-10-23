//eri roolien määrittely (admin, user) että järjestelmä voi tarkitsaa oikeudet

package syksy24.backend.fitness.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
