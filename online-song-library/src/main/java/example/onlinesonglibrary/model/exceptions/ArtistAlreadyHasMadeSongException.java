package example.onlinesonglibrary.model.exceptions;

public class ArtistAlreadyHasMadeSongException extends RuntimeException{
    public ArtistAlreadyHasMadeSongException(Long artistId, Long songId, String songName) {
        super(String.format("Artist with id %d already has the song with id %d named %s",artistId,songId,songName));
    }
}
