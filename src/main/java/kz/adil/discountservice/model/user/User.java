package kz.adil.discountservice.model.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@Document(collection = "users")
public class User implements UserDetails {
    @Id
    private String name;
    private String password;
    private LocalDate createdDate;
    private List<UserType> types;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return types.stream()
                .map(type->new SimpleGrantedAuthority(type.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
