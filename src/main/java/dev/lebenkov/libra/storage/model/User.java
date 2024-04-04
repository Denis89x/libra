package dev.lebenkov.libra.storage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "_user")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("user_id")
    Long userId;

    @NotBlank(message = "Username should not be empty")
    @Size(min = 4, max = 25, message = "Username should be 4 - 20 symbols size")
    @Column(name = "username")
    String username;

    @NotBlank(message = "Email should not be empty")
    @Email(message = "Email should be correct")
    @Column(name = "email")
    String email;

    @NotBlank(message = "Password should not be empty")
    @Column(name = "password")
    String password;

    @Column(name = "registration_date")
    @JsonProperty("registration_date")
    LocalDate registrationDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Book> books = new ArrayList<>();

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
    List<Message> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
    List<Message> receivedMessages = new ArrayList<>();

    @OneToMany(mappedBy = "reviewer", fetch = FetchType.LAZY)
    List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "tradeSender", fetch = FetchType.LAZY)
    List<TradeRequest> sentRequests = new ArrayList<>();

    @OneToMany(mappedBy = "tradeReceiver", fetch = FetchType.LAZY)
    List<TradeRequest> receivedRequests = new ArrayList<>();
}