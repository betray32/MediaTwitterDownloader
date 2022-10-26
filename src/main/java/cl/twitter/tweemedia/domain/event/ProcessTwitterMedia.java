package cl.twitter.tweemedia.domain.event;

import cl.twitter.tweemedia.domain.utils.Constants;
import cl.twitter.tweemedia.domain.utils.TweeMediaUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter4j.EntitySupport;
import twitter4j.MediaEntity;
import twitter4j.MediaEntity.Variant;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class ProcessTwitterMedia {
    private String responseMessage;

    public boolean saveMedia(Integer photos, Integer videos, String profile, String path, Integer registryCount) {
        ArrayList<Status> mediaList = null;
        try {
            log.info(Constants.GETTING_DATA_FROM_PROFILE, profile);
            mediaList = getMediaFromTimeline(profile, registryCount);

            if (mediaList == null) {
                responseMessage = Constants.ERROR_WHEN_CONNECTING_TO_TWITTER_ABORTING;
                return false;
            }
        } catch (TwitterException e) {
            responseMessage = Constants.ERROR_WHEN_CONNECTING_TO_TWITTER_ABORTING + e.getMessage();
            return false;
        }

        log.info(Constants.DATA_OK);
        log.info(Constants.FILTERING_MEDIA_FILES);

        List<MediaEntity> lPhotos = new ArrayList<>();
        List<MediaEntity> lVideos = new ArrayList<>();
        mediaFilter(mediaList, lPhotos, lVideos);
        if (NoMediaProfile(lPhotos, lVideos)) {
            responseMessage = Constants.THERE_S_NO_VIDEO_OR_PHOTO_DATA_TO_DOWNLOAD_FROM_REQUESTED_PROFILE;
            return false;
        }

        log.info("Photos [" + lPhotos.size() + "] Files");
        log.info("Videos [" + lVideos.size() + "] Files");
        log.info("Total of Media Found [" + (lPhotos.size() + lVideos.size()) + "] Files");
        log.info(Constants.WRITING_IN_DISK);

        if (lPhotos.size() > 0) savePhotos(photos, path, lPhotos);

        if (lVideos.size() > 0) saveVideos(videos, path, lVideos);

        responseMessage = Constants.MEDIA_RETRIEVED_SUCCESSFULLY_FROM_REQUESTED_PROFILE;
        log.info(Constants.MEDIA_RETRIEVED_SUCCESSFULLY_FROM_REQUESTED_PROFILE);
        return true;
    }

    private boolean NoMediaProfile(List<MediaEntity> lPhotos, List<MediaEntity> lVideos) {
        return lPhotos.isEmpty() && lVideos.isEmpty();
    }

    private void saveVideos(Integer videos, String path, List<MediaEntity> lVideos) {
        if (videos == 1) {
            log.info(Constants.SEPARATOR);
            log.info(Constants.DOWNLOADING_VIDEOS);
            for (MediaEntity v : lVideos) {
                Variant[] videoVariants = v.getVideoVariants();

                // get the best mp4 variant
                Variant betterQuality;
                int videoVariantCount = 0;
                while (true) {
                    if (videoVariants[videoVariantCount].getContentType().equals("video/mp4")) {
                        betterQuality = videoVariants[videoVariantCount];
                        break;
                    } else {
                        videoVariantCount++;
                    }
                }

                String urlVideo = betterQuality.getUrl().substring(0, betterQuality.getUrl().indexOf("?"));
                log.info("URL [" + urlVideo + "]");
                TweeMediaUtils.writeMediaIntoFile(urlVideo, path);
            }
            log.info(Constants.SEPARATOR);
        }
    }

    private void savePhotos(Integer photos, String path, List<MediaEntity> lPhotos) {
        if (photos == 1) {
            log.info(Constants.SEPARATOR);
            log.info(Constants.DOWNLOADING_PHOTOS);
            for (MediaEntity f : lPhotos) {
                String urlPhoto = f.getMediaURL();
                log.info("URL [" + urlPhoto + "]");
                TweeMediaUtils.writeMediaIntoFile(urlPhoto, path);
            }
            log.info(Constants.SEPARATOR);
        }
    }

    private void mediaFilter(ArrayList<Status> mediaList, List<MediaEntity> lFotos, List<MediaEntity> lVideos) {
        mediaList.stream().forEach(tweet -> {
            // discard retweets
            if (!tweet.getText().contains("RT")) {
                Arrays.stream(tweet.getMediaEntities()).forEach(mediaEntity -> {
                    if (Constants.PHOTO.equalsIgnoreCase(mediaEntity.getType())) {
                        lFotos.add(mediaEntity);
                    } else if (Constants.VIDEO.equalsIgnoreCase(mediaEntity.getType())) {
                        lVideos.add(mediaEntity);
                    }
                });
            }
        });
    }

    private ArrayList<Status> getMediaFromTimeline(String userId, int registryCount) throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        ArrayList<Status> completeTimeline = new ArrayList<>();

        // 200 is max allowed per page
        int tweetsForPage = 200;

        int pageNumber = 1;
        log.info("Retrieving Tweets... This might take some time...");
        while (true) {
            try {
                int size = completeTimeline.size(); // actual tweets count we got
                Paging page = new Paging(pageNumber, tweetsForPage);
                completeTimeline.addAll(twitter.getUserTimeline(userId, page));
                if (completeTimeline.size() == size) {
                    log.info("Tweets Obtained SuccessFully, Total Size: " + completeTimeline.size());
                    break;
                }
                pageNumber++;
                sleep(1000);
            } catch (TwitterException e) {
                System.out.println(e.getErrorMessage());
            }
        }

        // check if user want all tweets or less
        // registryCount = 0 means all tweets required
        if (registryCount == 0) {
            return completeTimeline;
        } else {
            ArrayList<Status> limitedTimeLine = new ArrayList<>();
            limitedTimeLine.addAll(completeTimeline.subList(0, registryCount));
            return limitedTimeLine;
        }
    }

    /**
     * need a sleep function to respect rate limit
     */
    static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}
