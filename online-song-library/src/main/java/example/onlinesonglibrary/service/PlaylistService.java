package example.onlinesonglibrary.service;

import example.onlinesonglibrary.model.Playlist;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface PlaylistService {
    List<Playlist> findAll();

    List<Playlist> findAllContainingSongsFromArtist(Long artistId);

    List<Playlist> findAllPublicPlaylistsWithMaxThreeSongs();

    Optional<Playlist> findById(Long playlistId);

    void deleteById(Long playlistId);

    Integer getTotalTimeOfSongsForPlaylist(Long playlistId);

    Optional<Playlist> save(Playlist playlist);

    Optional<Playlist> addSongToPlaylist(Long songId, Long playlistId);
}
