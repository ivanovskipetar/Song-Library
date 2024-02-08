package example.onlinesonglibrary.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime dateOfCreation;

    private boolean isPrivate;

    @ManyToMany
    private List<Song> songs;

    public Playlist() {
    }

    public Playlist(String name, boolean isPrivate) {
        this.name = name;
        this.dateOfCreation = LocalDateTime.now();
        this.isPrivate = isPrivate;
        this.songs = new ArrayList<>();
    }
}
