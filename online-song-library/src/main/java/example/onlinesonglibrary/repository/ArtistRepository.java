package example.onlinesonglibrary.repository;

import example.onlinesonglibrary.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    void deleteByNameAndArtistName(String name, String artistName);

    @Query("SELECT a.artistName,a.name FROM Artist a WHERE YEAR(a.dateOfBirth) < :year AND a.nationality = :nationality")
    List<Object[]> findArtistsByBirthYearAndNationality(@Param("year") int year,
                                                        @Param("nationality") String nationality);
}
