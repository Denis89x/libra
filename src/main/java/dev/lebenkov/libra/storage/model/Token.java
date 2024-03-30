package dev.lebenkov.libra.storage.model;

import dev.lebenkov.libra.storage.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@Table(name = "token")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {

    @Id
    @Column(name = "id")
    @GeneratedValue
    Long tokenId;

    @Column(unique = true)
    String token;

    @Enumerated(EnumType.STRING)
    TokenType tokenType = TokenType.BEARER;

    boolean revoked;

    boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
}
