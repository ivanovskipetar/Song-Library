package example.onlinesonglibrary.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import example.onlinesonglibrary.model.enumerations.Genre;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Comparator;

@Data
@Entity
//
public class Song implements Comparable<Song> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer durationInMinutes;

    private LocalDate releaseDate;

    @ManyToOne
    @JsonBackReference // bez ova davashe nekoj infinite recurrsion
    private Artist artist;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    public Song() {
    }

    public Song(String title, Integer durationInMinutes, Artist artist, Genre genre) {
        this.title = title;
        this.durationInMinutes = durationInMinutes;
        this.releaseDate = LocalDate.now();
        this.artist = artist;
        this.genre = genre;
    }

    //moze da zezne drug sort
    @Override
    public int compareTo(Song other) {
        int res = this.artist.getName().compareTo(other.artist.getName());

        if(res == 0){
            res = this.artist.getDateOfBirth().compareTo(other.artist.getDateOfBirth());
        }
        return res;
    }
}
