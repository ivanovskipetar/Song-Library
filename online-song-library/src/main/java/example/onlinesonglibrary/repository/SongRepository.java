package example.onlinesonglibrary.repository;

import example.onlinesonglibrary.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> getAllByArtist_Id(Long artistId);
}
