package example.onlinesonglibrary.service.impl;

import example.onlinesonglibrary.model.Artist;
import example.onlinesonglibrary.model.Playlist;
import example.onlinesonglibrary.model.Song;
import example.onlinesonglibrary.model.exceptions.ArtistNotFoundException;
import example.onlinesonglibrary.model.exceptions.PlaylistNotFoundException;
import example.onlinesonglibrary.model.exceptions.SongIsAlreadyInPlaylistException;
import example.onlinesonglibrary.model.exceptions.SongNotFoundException;
import example.onlinesonglibrary.repository.ArtistRepository;
import example.onlinesonglibrary.repository.PlaylistRepository;
import example.onlinesonglibrary.repository.SongRepository;
import example.onlinesonglibrary.service.PlaylistService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistServiceImpl implements PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public PlaylistServiceImpl(PlaylistRepository playlistRepository,
                               SongRepository songRepository, ArtistRepository artistRepository) {
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public Optional<Playlist> findById(Long playlistId) {
        return this.playlistRepository.findById(playlistId);
    }

    @Override
    public void deleteById(Long playlistId) {
        this.playlistRepository.deleteById(playlistId);
    }

    @Override
    public Integer getTotalTimeOfSongsForPlaylist(Long playlistId) {
        Playlist playlist = this.playlistRepository.findById(playlistId).orElseThrow(() -> new PlaylistNotFoundException(playlistId));

        return playlist.getSongs().stream()
                .mapToInt(Song::getDurationInMinutes)
                .sum();
    }

    @Override
    public List<Playlist> findAll() {
        return this.playlistRepository.findAll();
    }

    @Override
    public List<Playlist> findAllContainingSongsFromArtist(Long artistId) {
        Artist artist = this.artistRepository.findById(artistId).orElseThrow(() -> new ArtistNotFoundException(artistId));

        List<Playlist> allPlaylistsContainingSongsFromArtist = this.findAll()
                .stream()
                .filter(playlist -> playlist.getSongs()
                        .stream()
                        .anyMatch(song -> song.getArtist().equals(artist)))
                .collect(Collectors.toList());

        allPlaylistsContainingSongsFromArtist.forEach(playlist -> playlist.getSongs().sort(Comparator.reverseOrder()));

        return allPlaylistsContainingSongsFromArtist;
    }

    @Override
    public List<Playlist> findAllPublicPlaylistsWithMaxThreeSongs() {
        return this.playlistRepository.findAll()
                .stream()
                .filter(playlist -> !playlist.isPrivate() && playlist.getSongs().size() < 4)
                .toList();
    }

    @Override
    public Optional<Playlist> save(Playlist p) {
        Playlist playlist = new Playlist(p.getName(), p.isPrivate());
        return Optional.of(this.playlistRepository.save(playlist));
    }

    @Override
    public Optional<Playlist> addSongToPlaylist(Long songId, Long playlistId) {
        Song song = this.songRepository.findById(songId).orElseThrow(() -> new SongNotFoundException(songId));
        Playlist playlist = this.playlistRepository.findById(playlistId).orElseThrow(() -> new PlaylistNotFoundException(playlistId));

        if (playlist.getSongs().contains(song))
            throw new SongIsAlreadyInPlaylistException(song.getId(), song.getTitle(), playlist.getId(), playlist.getName());

        playlist.getSongs().add(song);
        return Optional.of(this.playlistRepository.save(playlist));
    }
}
