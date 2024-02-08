package example.onlinesonglibrary.service;

import example.onlinesonglibrary.model.Song;

import java.util.List;
import java.util.Optional;

public interface SongService {
    List<Song> findAll();

    List<Song> findFirstThreeWithDurationBetween(Integer from_min, Integer to_min);

    List<Song> getSongsForArtist(Long artistId);

    Optional<Song> getSongWithLongestDurationForArtistAndFromGenre(Long artistId, String genre);
}
