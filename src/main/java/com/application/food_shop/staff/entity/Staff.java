package com.application.food_shop.staff.entity;

import com.application.food_shop.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="staff")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String position;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long editorUserId;
}
