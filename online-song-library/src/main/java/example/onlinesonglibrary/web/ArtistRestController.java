package example.onlinesonglibrary.web;

import example.onlinesonglibrary.model.Artist;
import example.onlinesonglibrary.model.Song;
import example.onlinesonglibrary.model.dto.ArtistDto;
import example.onlinesonglibrary.model.dto.ArtistDto2;
import example.onlinesonglibrary.service.ArtistService;
import example.onlinesonglibrary.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistRestController {

    private final ArtistService artistService;
    private final SongService songService;

    public ArtistRestController(ArtistService artistService, SongService songService) {
        this.artistService = artistService;
        this.songService = songService;
    }

    @GetMapping
    public List<Artist> findAll() {
        return this.artistService.findAll();
    }


    @GetMapping("/applyfilter")
    List<ArtistDto2> findAllBornBeforeYearAndNationality(@RequestParam Integer bornBeforeYear,
                                                         @RequestParam String nationality) {
        return this.artistService.findAllBornBeforeYearAndNationalityDto(bornBeforeYear, nationality);
    }

    @GetMapping("/{artistId}/get")
    ResponseEntity<ArtistDto> findArtistWithSongsSortedByTitleDesc(@PathVariable Long artistId) {
        return this.artistService.findArtistWithSongsSortedByTitleDesc(artistId)
                .map(a -> ResponseEntity.ok().body(a))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }


    @PostMapping("/add")
    public ResponseEntity<Artist> save(@RequestBody Artist artist) {
        return this.artistService.save(artist)
                .map(a -> ResponseEntity.ok().body(a))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/{artistId}/add-song")
    public ResponseEntity<Artist> addSongToArtist(@PathVariable Long artistId,
                                                  @RequestBody Song song) {
        return this.artistService.addSongToArtist(artistId, song)
                .map(artist -> ResponseEntity.ok().body(artist))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
