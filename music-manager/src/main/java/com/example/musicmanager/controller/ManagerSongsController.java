package com.example.musicmanager.controller;

import com.example.musicmanager.constant.EventPaths;
import com.example.musicmanager.entity.Songs;
import com.example.musicmanager.service.ManagerSongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ManagerSongsController {

    @Autowired
    private ManagerSongsService managerSongService;

    @GetMapping(EventPaths.MY_SONG)
    public List<Songs> getAllMySong(Principal principal){
        List<Songs> listSong = managerSongService.getAllMySongs("trung");
        if(listSong.isEmpty()){
            throw new NotFoundException("my song is empty!!");
        }
        return listSong;
    }

    @PostMapping(EventPaths.MY_SONG)
    public String saveAndUpdateMySong(@Valid @RequestBody Songs songs, Principal principal){
        String result = managerSongService.saveAndUpdateMySong(songs, "trung");
        return "success";
    }

    @GetMapping(EventPaths.MY_SONG +"/{idSong}" )
    public Songs getMySong(@PathVariable(name = "idSong") int idSong){
        return managerSongService.getMySongs(idSong);
    }

    @DeleteMapping(EventPaths.MY_SONG)
    public String deleteMySong(
                    @RequestParam(required = true,defaultValue = "0") int idSong,
                    Principal principal){
        if(idSong==0){
            return "false!!";
        }
        managerSongService.deleteMySong(idSong, "trung");
        return "Success!!!";
    }

    @GetMapping(EventPaths.SEARCH)
    public List<Songs> searchMySong(@RequestParam(required = true, defaultValue = "") String keyword){
            List<Songs> result = managerSongService.searchMySongByKeyword(keyword);
            return result;
    }



}
