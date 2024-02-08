package example.onlinesonglibrary.service.impl;

import example.onlinesonglibrary.model.Artist;
import example.onlinesonglibrary.model.Song;
import example.onlinesonglibrary.model.dto.ArtistDto;
import example.onlinesonglibrary.model.dto.ArtistDto2;
import example.onlinesonglibrary.model.dto.SongDto2;
import example.onlinesonglibrary.model.exceptions.ArtistAlreadyHasMadeSongException;
import example.onlinesonglibrary.model.exceptions.ArtistNotFoundException;
import example.onlinesonglibrary.repository.ArtistRepository;
import example.onlinesonglibrary.repository.SongRepository;
import example.onlinesonglibrary.service.ArtistService;
import org.springframework.stereotype.Service;


import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository,
                             SongRepository songRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }

    @Override
    public List<Artist> findAll() {
        return artistRepository.findAll();
    }

    @Override
    public Optional<Artist> findById(Long id) {
        return artistRepository.findById(id);
    }

    @Override
    public Optional<ArtistDto> findArtistWithSongsSortedByTitleDesc(Long artistId) {
        Artist a = this.findById(artistId).orElseThrow(() -> new ArtistNotFoundException(artistId));
        List<Song> songs = songRepository.getAllByArtist_Id(a.getId());
        List<SongDto2> songsDtos2 = songs.stream()
                .map(s -> new SongDto2(s.getTitle(), s.getReleaseDate()))
                .sorted(Comparator.comparing(SongDto2::getTitle).reversed())
                .toList();
        ArtistDto artistDto = new ArtistDto(a.getId(), a.getName(), a.getArtistName(), a.getDateOfBirth(), a.getNationality(), songsDtos2);
        return Optional.of(artistDto);
    }

    @Override
    public List<Object[]> findAllBornBeforeYearAndNationality(Integer bornBeforeYear, String nationality) {
        return this.artistRepository.findArtistsByBirthYearAndNationality(bornBeforeYear, nationality);
    }

    @Override
    public List<ArtistDto2> findAllBornBeforeYearAndNationalityDto(Integer bornBeforeYear, String nationality) {
        List<Artist> artistsBornBeforeYear = this.artistRepository.findAll()
                .stream()
                .filter(artist -> artist.getDateOfBirth().getYear() < bornBeforeYear && artist.getNationality().equals(nationality))
                .toList();
        return artistsBornBeforeYear.stream()
                .map(artist -> new ArtistDto2(artist.getName(), artist.getArtistName()))
                .collect(Collectors.toList());

    }

    @Override
    public Optional<Artist> save(Artist artist) {
        this.artistRepository.deleteByNameAndArtistName(artist.getName(), artist.getArtistName());
        Artist newArtist = new Artist(artist.getName(), artist.getArtistName(), artist.getDateOfBirth(), artist.getNationality());
        this.artistRepository.save(newArtist);
        return Optional.of(newArtist);
    }

    @Override
    public Optional<Artist> addSongToArtist(Long artistId, Song song) {
        Artist artist = this.artistRepository.findById(artistId).orElseThrow(() -> new ArtistNotFoundException(artistId));
        Song newSong = new Song(song.getTitle(), song.getDurationInMinutes(), artist, song.getGenre());

        if (artist.getSongs().stream()
                .filter(s -> s.equals(newSong))
                .collect(Collectors.toList()).size() > 0)
            throw new ArtistAlreadyHasMadeSongException(artistId, newSong.getId(), newSong.getTitle());

        artist.getSongs().add(newSong);
        newSong.setArtist(artist);
        this.songRepository.save(newSong);
        this.artistRepository.save(artist);

        return Optional.of(artist);
    }
}
