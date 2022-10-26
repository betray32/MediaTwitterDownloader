package cl.twitter.tweemedia.infrastructure.input.rest.data.request;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Builder
@Data
public class GetMediaRequest implements Serializable {
    @NotBlank(message = "twitterProfile is mandatory")
    private String twitterProfile;

    @Range(min=0, max=1000000, message = "registryCount requested value are to high (max 1m)")
    @NotNull(message = "registryCount cannot be null")
    private Integer registryCount;

    @Range(min=0, max=1, message = "getPhotos accepted values are 0 and 1")
    @NotNull(message = "getPhotos cannot be null")
    private Integer getPhotos;

    @Range(min=0, max=1, message = "getVideos accepted values are 0 and 1")
    @NotNull(message = "getVideos cannot be null")
    private Integer getVideos;
}
