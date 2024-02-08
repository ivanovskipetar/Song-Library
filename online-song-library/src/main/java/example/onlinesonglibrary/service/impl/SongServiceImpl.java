package example.onlinesonglibrary.service.impl;

import example.onlinesonglibrary.model.Song;
import example.onlinesonglibrary.model.enumerations.Genre;
import example.onlinesonglibrary.model.exceptions.ArtistNotFoundException;
import example.onlinesonglibrary.repository.ArtistRepository;
import example.onlinesonglibrary.repository.SongRepository;
import example.onlinesonglibrary.service.SongService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public SongServiceImpl(SongRepository songRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Song> findAll() {
        return this.songRepository.findAll();
    }

    @Override
    public List<Song> findFirstThreeWithDurationBetween(Integer from_min, Integer to_min) {
        return this.songRepository.findAll()
                .stream()
                .filter(song -> song.getDurationInMinutes() >= from_min && song.getDurationInMinutes() <= to_min)
                .limit(3)
                .sorted(Comparator.comparing(Song::getDurationInMinutes).reversed())
                .toList();
    }

    @Override
    public List<Song> getSongsForArtist(Long artistId) {
        this.artistRepository.findById(artistId).orElseThrow(() -> new ArtistNotFoundException(artistId));

        return songRepository.getAllByArtist_Id(artistId);
    }

    @Override
    public Optional<Song> getSongWithLongestDurationForArtistAndFromGenre(Long artistId, String genre) {
        this.artistRepository.findById(artistId).orElseThrow(() -> new ArtistNotFoundException(artistId));
        Optional<Song> song = getSongsForArtist(artistId).stream()
                .filter(s -> s.getGenre().equals(Genre.valueOf(genre)))
                .max(Comparator.comparing(Song::getDurationInMinutes));
        return song;
    }
}
