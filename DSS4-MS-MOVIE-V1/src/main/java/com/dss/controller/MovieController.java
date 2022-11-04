package com.dss.controller;

import com.dss.entity.ActorEntity;
import com.dss.entity.MovieEntity;
import com.dss.service.MovieService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/addMovie")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200, message = "Success")})
    @ResponseStatus(HttpStatus.OK)
    public MovieEntity addMovie(@RequestBody MovieEntity movie) {
        MovieEntity savedMovie = movieService.save(movie);
        return savedMovie;
    }

    @GetMapping("/findAllMovies")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200, message = "Success")})
    @ResponseStatus(HttpStatus.OK)
    public List<MovieEntity> findAllMovies() {
        List<MovieEntity> movieEntityList = null;
        movieEntityList = movieService.findAllMovies();
        return movieEntityList;
    }

    @GetMapping("/findByModel")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200, message = "Success")})
    @ResponseStatus(HttpStatus.OK)
    public List<MovieEntity> findByModel(@RequestBody MovieEntity movie) {
        List<MovieEntity> movieEntityList = null;
        movieEntityList = movieService.findByModel(movie);
        return movieEntityList;
    }

    @GetMapping("/findById/{movieId}")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200, message = "Success")})
    @ResponseStatus(HttpStatus.OK)
    public MovieEntity findById(@PathVariable(value = "movieId") Integer movieId) {
        MovieEntity movieEntity = null;
        movieEntity = movieService.findById(movieId);
        return movieEntity;
    }

    @PutMapping("/updateMovie/{movieId}")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200, message = "Success")})
    @ResponseStatus(HttpStatus.OK)
    public MovieEntity updateMovie(@PathVariable(value = "movieId") Integer movieId, @RequestBody MovieEntity movieEntity) {
        MovieEntity updatedMovieEntity = movieService.update(movieId, movieEntity);
        return updatedMovieEntity;
    }

    @DeleteMapping("/deleteMovie/{movieId}")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200, message = "Success")})
    @ResponseStatus(HttpStatus.OK)
    public MovieEntity deleteMovieById(@PathVariable(value = "movieId") Integer movieId) {
        Optional<MovieEntity> movieEntityOptional = movieService.delete(movieId);
        MovieEntity existingMovie = new MovieEntity();
        if (movieEntityOptional.isPresent()) {
            existingMovie = movieEntityOptional.get();
        }
        return existingMovie;
    }
}
