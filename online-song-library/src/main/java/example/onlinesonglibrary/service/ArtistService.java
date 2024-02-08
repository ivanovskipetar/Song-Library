package example.onlinesonglibrary.service;

import example.onlinesonglibrary.model.Artist;
import example.onlinesonglibrary.model.Song;
import example.onlinesonglibrary.model.dto.ArtistDto;
import example.onlinesonglibrary.model.dto.ArtistDto2;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ArtistService {
    List<Artist> findAll();

    Optional<ArtistDto> findArtistWithSongsSortedByTitleDesc(Long artistId);

    List<Object[]> findAllBornBeforeYearAndNationality(Integer bornBeforeYear, String nationality);

    List<ArtistDto2> findAllBornBeforeYearAndNationalityDto(Integer bornBeforeYear, String nationality);

    Optional<Artist> findById(Long id);

    Optional<Artist> save(Artist artist);

    public Optional<Artist> addSongToArtist(Long artistId, Song song);

}
