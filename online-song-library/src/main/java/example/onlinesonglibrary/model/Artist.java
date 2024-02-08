package example.onlinesonglibrary.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import example.onlinesonglibrary.model.Song;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String artistName;

    private LocalDate dateOfBirth;

    private String nationality;

    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Song> songs;

    public Artist() {
    }

    public Artist(String name, String artistName, LocalDate dateOfBirth, String nationality) {
        this.name = name;
        this.artistName = artistName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.songs = new ArrayList<>();
    }
}
