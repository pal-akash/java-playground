package com.akashp6.movieReview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;


    public List<Movie> allMovies(){
        return movieRepository.findAll();
    }

    public Optional<Movie> singleMovie(String imdbId){
        return movieRepository.findMovieByImdbId(imdbId);
    }

    public List<Review> getReviews(String imdbId){

        Movie movie = movieRepository.findMovieByImdbId(imdbId).get();

        List<Review> allReviews = movie.getReviewIds();

        return allReviews;
    }
}
