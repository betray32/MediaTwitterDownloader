package cl.twitter.tweemedia.application.ports.input.usecases;

import cl.twitter.tweemedia.infrastructure.input.rest.data.request.GetMediaRequest;
import cl.twitter.tweemedia.infrastructure.input.rest.data.response.GetMediaResponse;

public interface TweeMediaUseCase {
    GetMediaResponse getMediaFromProfile(GetMediaRequest media);
}
