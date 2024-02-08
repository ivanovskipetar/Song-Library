package example.onlinesonglibrary.model.dto;

import lombok.Data;

@Data
public class ArtistDto2 {
    private String name;
    private String artistName;

    public ArtistDto2(String name, String artistName) {
        this.name = name;
        this.artistName = artistName;
    }
}
