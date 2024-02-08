package example.onlinesonglibrary.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ArtistDto {

    private Long id;

    private String name;

    private String artistName;

    private LocalDate dateOfBirth;

    private String nationality;

    private List<SongDto2> songs;

    public ArtistDto(Long id, String name, String artistName, LocalDate dateOfBirth, String nationality, List<SongDto2> songDtos2) {
        this.id = id;
        this.name = name;
        this.artistName = artistName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.songs = songDtos2;
    }
}

