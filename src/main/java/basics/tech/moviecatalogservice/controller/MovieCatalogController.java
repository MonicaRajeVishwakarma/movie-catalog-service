package basics.tech.moviecatalogservice.controller;

import basics.tech.moviecatalogservice.model.CatalogItem;
import basics.tech.moviecatalogservice.model.Movie;
import basics.tech.moviecatalogservice.model.Rating;
import basics.tech.moviecatalogservice.model.UserRating;
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


        UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/"+userId,UserRating.class);

        return userRating.getUserRating().stream().map(rating -> {
                   Movie movie = restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);
                    return new CatalogItem(movie.getName(), "I like this", rating.getRating());
        }


        ).collect(Collectors.toList());
       // return Collections.singletonList(new CatalogItem("The Promise","I like this",4));
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