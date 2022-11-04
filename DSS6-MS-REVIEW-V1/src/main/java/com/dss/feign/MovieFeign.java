package com.dss.feign;

import com.dss.entity.MovieEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(value="movie-service", url="localhost:9007/api/movie")
public interface MovieFeign {
    @RequestMapping(method = RequestMethod.GET, value = "/findById/{movieId}", consumes = "application/json", produces="application/json")
    public MovieEntity findMovieById(@PathVariable("movieId") int movieId);
}
