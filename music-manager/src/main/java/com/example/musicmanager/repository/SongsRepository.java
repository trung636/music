package com.example.musicmanager.repository;

import com.example.musicmanager.entity.Songs;
import com.example.musicmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SongsRepository extends JpaRepository<Songs, Integer> {
        List<Songs> findAllByUser(User user);
        List<Songs> findAllByNameSongContaining(String keyword);
}
