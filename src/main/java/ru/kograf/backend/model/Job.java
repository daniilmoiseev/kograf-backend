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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kograf_job")
@Data
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(name = "co_authors")
    private String coAuthors;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {PERSIST, MERGE, REFRESH, DETACH})
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @OneToOne(cascade = {MERGE, REFRESH, DETACH})
    @JoinColumn(name = "conference_id")
    private Conference conference;

    @OneToOne(cascade = {MERGE, REFRESH, DETACH})
    @JoinColumn(name = "section_id")
    private Section section;

    @OneToMany(mappedBy = "job", cascade = {PERSIST, MERGE, REFRESH, DETACH})
    private List<Comment> comments;

    @Column(name = "date_time", nullable = false)
    private ZonedDateTime dateTime;

    @Column(name = "file_name")
    private String fileName;
}
