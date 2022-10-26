package cl.twitter.tweemedia.infrastructure.input.rest;

import cl.twitter.tweemedia.application.ports.input.usecases.TweeMediaUseCase;
import cl.twitter.tweemedia.infrastructure.input.rest.data.request.GetMediaRequest;
import cl.twitter.tweemedia.infrastructure.input.rest.data.response.GetMediaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TweeMediaRestAdapter {

    private final TweeMediaUseCase tweeMediaUseCase;

    @PostMapping(path = "/GetMedia", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get media from Twitter Profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = " Media stored successfully in desired location",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request, please check your payload",
                    content = @Content)})
    public ResponseEntity<GetMediaResponse> getMediaFromProfile(@Valid @RequestBody GetMediaRequest media) {
        return ResponseEntity.ok().body(tweeMediaUseCase.getMediaFromProfile(media));
    }


}
