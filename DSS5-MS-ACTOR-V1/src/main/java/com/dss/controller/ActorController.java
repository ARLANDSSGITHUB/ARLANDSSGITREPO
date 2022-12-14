package com.dss.controller;

import com.dss.entity.ActorEntity;
import com.dss.service.ActorService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/actor")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @PostMapping("/addActor")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200, message = "Success")})
    @ResponseStatus(HttpStatus.OK)
    public ActorEntity addActor(@RequestBody ActorEntity actor) {
        ActorEntity savedActor = actorService.save(actor);
        return savedActor;
    }

    @GetMapping("/findAllActors")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200, message = "Success")})
    @ResponseStatus(HttpStatus.OK)
    public List<ActorEntity> findAllActors(){
        List<ActorEntity> actorEntityList = null;
        actorEntityList = actorService.findAllActors();
        return actorEntityList;
    }
    @GetMapping("/findByModel")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200, message = "Success")})
    @ResponseStatus(HttpStatus.OK)
    public List<ActorEntity> findByModel(@RequestBody ActorEntity actor){
        List<ActorEntity> actorEntityList = null;
        actorEntityList = actorService.findByModel(actor);
        return actorEntityList;
    }

    @GetMapping("/findById/{actorId}")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200, message = "Success")})
    @ResponseStatus(HttpStatus.OK)
    public ActorEntity findById(@PathVariable(value = "actorId") Integer actorId){
        ActorEntity actorEntity = null;
        actorEntity = actorService.findById(actorId);
        return actorEntity;
    }

    @PutMapping("/updateActor/{actorId}")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200, message = "Success")})
    @ResponseStatus(HttpStatus.OK)
    public ActorEntity updateActor(@PathVariable(value = "actorId") Integer actorId, @RequestBody ActorEntity actorEntity) {
        ActorEntity updatedActorEntity = actorService.update(actorId, actorEntity);
        return updatedActorEntity;
    }

    @DeleteMapping("/deleteActor/{actorId}")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 404, message = "Service not found"),
            @ApiResponse(code = 200, message = "Success")})
    @ResponseStatus(HttpStatus.OK)
    public ActorEntity deleteActorById(@PathVariable(value = "actorId") Integer actorId) {
        Optional<ActorEntity> actorEntityOptional = actorService.delete(actorId);
        ActorEntity existingActor = new ActorEntity();
        if(actorEntityOptional.isPresent()) {
            existingActor = actorEntityOptional.get();
        }
        return existingActor;
    }
}
