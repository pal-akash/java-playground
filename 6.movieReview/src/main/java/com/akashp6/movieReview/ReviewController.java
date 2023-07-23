package com.akashp6.movieReview;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Map<String, String> payLoad){
        return new ResponseEntity<Review>(reviewService.createReview(payLoad.get("reviewBody"), payLoad.get("imdbId")), HttpStatus.CREATED);
    }

    @RequestMapping("/deletereview")
    public ResponseEntity<?> deleteReview(@RequestParam("id") ObjectId reviewId){
        return new ResponseEntity<String>(reviewService.deleteReview(reviewId), HttpStatus.OK);
    }


}
