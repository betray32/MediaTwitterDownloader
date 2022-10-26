package cl.twitter.tweemedia.infrastructure.input.rest.data.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetMediaResponse {
    private boolean dataDownloaded;
    private String message;
}
