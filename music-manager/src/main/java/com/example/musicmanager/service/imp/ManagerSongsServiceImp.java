package com.example.musicmanager.service.imp;

import com.example.musicmanager.entity.Songs;
import com.example.musicmanager.repository.SongsRepository;
import com.example.musicmanager.repository.UserRepository;
import com.example.musicmanager.service.ManagerSongsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerSongsServiceImp implements ManagerSongsService {

    @Autowired
    private SongsRepository songsRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Songs> getAllMySongs(String username) {
        List<Songs> listSongs = songsRepository.findAllByUser(userRepository.findByUsername(username));
        return listSongs;
    }

    @Override
    public String saveAndUpdateMySong(Songs songs, String username) {
        songs.setUser(userRepository.findByUsername(username));
        songsRepository.save(songs);
        return "success";
    }

    @Override
    public void deleteMySong(int idSong, String username) {
        songsRepository.deleteById(idSong);
    }

    @Override
    public List<Songs> searchMySongByKeyword(String keyword) {
        List<Songs> result = songsRepository.findAllByNameSongContaining(keyword);
        return result;
    }

    @Override
    public Songs getMySongs(int idSong) {
        Optional<Songs> songs= songsRepository.findById(idSong);
        return songs.get();
    }

}
