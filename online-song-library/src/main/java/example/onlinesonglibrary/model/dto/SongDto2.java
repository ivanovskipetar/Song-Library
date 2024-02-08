package example.onlinesonglibrary.model.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class SongDto2 {
    private String title;
    private LocalDate releaseDate;

    public SongDto2() {
    }

    public SongDto2(String title, LocalDate releaseDate) {
        this.title = title;
        this.releaseDate = releaseDate;
    }
}
