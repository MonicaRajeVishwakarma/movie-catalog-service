package basics.tech.moviecatalogservice.service;

import basics.tech.moviecatalogservice.model.Rating;
import basics.tech.moviecatalogservice.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingInfo {

    @Autowired
    private RestTemplate restTemplate;


    @HystrixCommand(fallbackMethod = "getFallbackUserRatingData",
        commandProperties = {

            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "5"),
            @HystrixProperty(name = "circuitBreaker.errorPercentageThreshold",value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "5000")
        }
    )
    public UserRating getUserRatingData(String userId) {
        return restTemplate.getForObject
                ("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);
    }

    public UserRating getFallbackUserRatingData(String userId) {

        UserRating userRating = new UserRating();
        Rating rating = new Rating();
        rating.setMovieId("0");
        rating.setRating(0);
        userRating.setUserId(userId);
        userRating.setUserRating(Arrays.asList(rating));
        return userRating;

    }
}
