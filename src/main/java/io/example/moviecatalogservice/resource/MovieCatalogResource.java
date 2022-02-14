package io.example.moviecatalogservice.resource;

import io.example.moviecatalogservice.models.CatalogItem;
import io.example.moviecatalogservice.models.Movie;
import io.example.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
   private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder builder;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){



        //get all rated movie Ids
        List<Rating> ratings = Arrays.asList(
                new Rating("123", 4),
                new Rating("523", 3)
        );


        // TODO: Explain this return block
      return   ratings.stream().map(rating ->
      {
         Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);

//         Movie movie = builder.build()
//                 .get()
//                 .uri("http://localhost:8082/movies/" + rating.getMovieId())
//                 .retrieve()
//                 .bodyToMono(Movie.class)
//                 .block();

         return new CatalogItem(movie.getName(), "Desc",rating.getRating());

      }).collect(Collectors.toList());





        //for each movie ID, call movie info service and get details

        //Put them all together

    }
}
