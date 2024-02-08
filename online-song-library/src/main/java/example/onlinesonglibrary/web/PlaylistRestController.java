package example.onlinesonglibrary.web;

import example.onlinesonglibrary.model.Playlist;
import example.onlinesonglibrary.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistRestController {

    private final PlaylistService playlistService;

    public PlaylistRestController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public List<Playlist> findAll() {
        return this.playlistService.findAll();
    }

    @GetMapping("/{artistId}/get")
    public List<Playlist> getAllPlaylistsContainingSongsByArtist(@PathVariable Long artistId) {
        return this.playlistService.findAllContainingSongsFromArtist(artistId);
    }

    @GetMapping("/apply-public-filter")
    public List<Playlist> getAllPublicPlaylistsWithFilter() {
        return this.playlistService.findAllPublicPlaylistsWithMaxThreeSongs();
    }

    @GetMapping("/{playlistId}/calculate-total-duration-of-songs")
    public Integer getTotalTimeOfSongsForPlaylist(@PathVariable Long playlistId) {
        return this.playlistService.getTotalTimeOfSongsForPlaylist(playlistId);
    }

    @PostMapping("/add")
    public ResponseEntity<Playlist> save(@RequestBody Playlist playlist) {
        return this.playlistService.save(playlist)
                .map(p -> ResponseEntity.ok().body(p))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/{playlistId}/add-song/{songId}")
    public ResponseEntity<Playlist> addSongToPlaylist(@PathVariable Long playlistId,
                                                      @PathVariable Long songId) {
        return this.playlistService.addSongToPlaylist(songId, playlistId)
                .map(playlist -> ResponseEntity.ok().body(playlist))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{playlistId}/delete")
    public ResponseEntity delete(@PathVariable Long playlistId) {
        this.playlistService.deleteById(playlistId);
        if (this.playlistService.findById(playlistId).isEmpty()) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();

    }
}
