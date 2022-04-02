package com.example.musicmanager.service;

import com.example.musicmanager.entity.Songs;

import java.util.List;


public interface ManagerSongsService {

    List<Songs> getAllMySongs(String username);

    String saveAndUpdateMySong(Songs songs, String username);

    void deleteMySong(int idSong, String username);

    List<Songs> searchMySongByKeyword(String keyword);

    Songs getMySongs(int idSong);
}
