package ru.kograf.backend.model;


import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REFRESH;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kograf.backend.model.enums.Role;
import ru.kograf.backend.model.enums.UserStatus;

@Entity
@Table(name = "kograf_user")
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String organization;

    @Column(name = "academic_degree")
    private String academicDegree;

    @Column(name = "academic_title")
    private String academicTitle;

    @Column(name = "orc_id")
    private String orcId;

    @Column(name = "rinc_id")
    private String rincId;

    @ManyToMany(mappedBy = "users", cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private List<Conference> conferences;

    @OneToMany(mappedBy = "user", cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private List<Job> jobs;

    @Column(name = "profile_picture")
    private String profilePicture;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    public User(String fullName, String phone, String email, String organization, String academicDegree,
            String academicTitle, String password, Role role, UserStatus status) {
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.organization = organization;
        this.academicDegree = academicDegree;
        this.academicTitle = academicTitle;
        this.password = password;
        this.role = role;
        this.status = status;
    }
}
