package example.onlinesonglibrary.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SongIsAlreadyInPlaylistException extends RuntimeException {
    public SongIsAlreadyInPlaylistException(Long songId, String songTitle, Long playlistId, String playlistName) {
        super(String.format("Song with title %s and id %d is already in this playlist named %s with id %d", songTitle, songId, playlistName, playlistId));
    }
}
