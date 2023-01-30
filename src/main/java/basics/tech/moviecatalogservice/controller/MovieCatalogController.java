package basics.tech.moviecatalogservice.controller;

import basics.tech.moviecatalogservice.model.CatalogItem;
import basics.tech.moviecatalogservice.model.Movie;
import basics.tech.moviecatalogservice.model.Rating;
import basics.tech.moviecatalogservice.model.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    private RestTemplate restTemplate;

    //RestTemplate restTemplate = new RestTemplate();
    WebClient.Builder webClientBuilder = WebClient.builder();

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        UserRating userRating = getUserRatingData(userId);
        return userRating.getUserRating().stream().map(rating -> {
                    return getCatalogItem(rating);
                }
        ).collect(Collectors.toList());
       // return Collections.singletonList(new CatalogItem("The Promise","I like this",4));
    }

    @HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
    private CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject
                ("http://movie-info-service/movies/"+ rating.getMovieId(),
                        Movie.class);
        return new CatalogItem(movie.getName(), "I like this",
                rating.getRating());
    }

    private CatalogItem getFallbackCatalogItem(Rating rating) {
        return new CatalogItem("Movie not found", "",
                rating.getRating());

    }
    @HystrixCommand(fallbackMethod = "getFallbackUserRatingData")
    private UserRating getUserRatingData(String userId) {
        return restTemplate.getForObject
                ("http://rating-data-service/ratingsdata/users/" + userId, UserRating.class);
    }

    private UserRating getFallbackUserRatingData(String userId) {

        UserRating userRating = new UserRating();
        Rating rating = new Rating();
        rating.setMovieId("0");
        rating.setRating(0);
        userRating.setUserId(userId);
        userRating.setUserRating(Arrays.asList(rating));
        return userRating;

    }
    public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId) {
        return Arrays.asList(new CatalogItem("No movie", "", 0));
    }
}


 /*Movie movie = webClientBuilder
                            .build()
                            .get()
                            .uri("http://localhost:8082/movies/"+rating.getMovieId())
                            .retrieve()
                            .bodyToMono(Movie.class)
 .block();*/

/* List<Rating> ratings = Arrays.asList(
                new Rating("1234",4),
                new Rating("5678",3)
        );*/