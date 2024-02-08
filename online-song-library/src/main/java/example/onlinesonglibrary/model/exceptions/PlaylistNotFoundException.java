package example.onlinesonglibrary.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PlaylistNotFoundException extends RuntimeException{
    public PlaylistNotFoundException(Long playlistId) {
        super(String.format("Playlist with id %d is not found", playlistId));
    }

}
