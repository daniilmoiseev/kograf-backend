package ru.kograf.backend.model;


import static jakarta.persistence.CascadeType.DETACH;
import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REFRESH;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kograf_section")
@Data
@NoArgsConstructor
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToMany(cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinTable(
            name = "kograf_user_to_section",
            joinColumns = {@JoinColumn(name = "section_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}
    )
    private List<User> users;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "leader_id", referencedColumnName = "id", nullable = false)
    private User leader;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "conference_id", referencedColumnName = "id", nullable = false)
    private Conference conference;
}
