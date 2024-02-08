package example.onlinesonglibrary.web;

import example.onlinesonglibrary.model.Playlist;
import example.onlinesonglibrary.model.Song;
import example.onlinesonglibrary.service.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongRestController {
    private final SongService songService;

    public SongRestController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public List<Song> findAll() {
        return songService.findAll();
    }

    @GetMapping("/{artistId}/get")
    public List<Song> findAllSongsForArtist(@PathVariable Long artistId) {
        return songService.getSongsForArtist(artistId);
    }

    @GetMapping("/{artistId}/longest-song-from-genre")
    public ResponseEntity<Song> findLongestSongForArtist(@PathVariable Long artistId,
                                                         @RequestParam String genre) {
        return this.songService.getSongWithLongestDurationForArtistAndFromGenre(artistId, genre)
                .map(song -> ResponseEntity.ok().body(song))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/apply-duration-sort-filter")
    public List<Song> getFirstThreeSorted(@RequestParam Integer from_min,
                                          @RequestParam Integer to_min) {
        return this.songService.findFirstThreeWithDurationBetween(from_min, to_min);
    }
}
