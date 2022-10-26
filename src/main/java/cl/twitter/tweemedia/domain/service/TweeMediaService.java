package cl.twitter.tweemedia.domain.service;

import cl.twitter.tweemedia.application.ports.input.usecases.TweeMediaUseCase;
import cl.twitter.tweemedia.domain.event.ProcessTwitterMedia;
import cl.twitter.tweemedia.infrastructure.input.rest.data.request.GetMediaRequest;
import cl.twitter.tweemedia.infrastructure.input.rest.data.response.GetMediaResponse;
import cl.twitter.tweemedia.infrastructure.output.persistance.MediaDataEntity;
import cl.twitter.tweemedia.infrastructure.output.persistance.MediaDataRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TweeMediaService implements TweeMediaUseCase {

    private static final String OK = "OK";
    private static final String ERROR = "Error";
    private static final String ERROR_WHEN_EXPORTING_DATA_FROM_PROFILE_DETAIL = "Error when exporting data from profile, Detail > ";

    @Value("${tweemedia.directory}")
    public String directory;

    private final MediaDataRepository mediaDataRepository;
    private final ProcessTwitterMedia twitterManagementService;

    @Override
    public GetMediaResponse getMediaFromProfile(GetMediaRequest media) {
        try {
            if (twitterManagementService.saveMedia(media.getGetPhotos(), media.getGetVideos(), media.getTwitterProfile(), directory, media.getRegistryCount())) {
                mediaDataRepository.save(buildMediaDataEntity(media));
                return buildProcessedMedia(true, twitterManagementService.getResponseMessage());
            } else {
                return buildProcessedMedia(false, twitterManagementService.getResponseMessage());
            }
        } catch (Exception e) {
            log.error(ERROR_WHEN_EXPORTING_DATA_FROM_PROFILE_DETAIL, e);
            return buildProcessedMedia(false, "Error when processing Media Data");
        }
    }

    private GetMediaResponse buildProcessedMedia(boolean result, String message) {
        return GetMediaResponse.builder()
                .dataDownloaded(result)
                .message(message).build();
    }

    private MediaDataEntity buildMediaDataEntity(GetMediaRequest media) {
        return MediaDataEntity.builder()
                .transactionRequest(LocalDateTime.now())
                .requestedProfile(media.getTwitterProfile())
                .getMediaPhoto(media.getGetPhotos())
                .getMediaVideo(media.getGetVideos()).build();

    }
}
