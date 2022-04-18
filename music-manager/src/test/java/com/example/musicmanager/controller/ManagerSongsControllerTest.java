package com.example.musicmanager.controller;

import com.example.musicmanager.entity.Songs;
import com.example.musicmanager.entity.User;
import com.example.musicmanager.service.ManagerSongsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ManagerSongsControllerTest {

    @InjectMocks
    ManagerSongsController managerSongsController;

    @Mock
    private ManagerSongsService songsService;

    private static User user = new User("trung");

    private static List<Songs> songs = Arrays.asList(
            new Songs(1, "dan truong", user, "tinh khuc vang", "codeSong", new Date(System.currentTimeMillis())),
            new Songs(2, "dan truong", user, "tinh khuc vang", "codeSong", new Date(System.currentTimeMillis())),
            new Songs(3, "dan truong", user, "tinh khuc vang", "codeSong", new Date(System.currentTimeMillis()))

    );
    private static List<Songs> emptyList = null;
    private static Songs songNull = null;

    @Test
    void getAllMySong() {
        Mockito.when(songsService.getAllMySongs(user.getUsername())).thenReturn(songs);

        List<Songs> songs = managerSongsController.getAllMySong();

        Assertions.assertEquals(songs.size(), 3);
    }

    @Test
    void testListNullWhenGetAllMySong(){
        Mockito.when(songsService.getAllMySongs(user.getUsername())).thenReturn(emptyList);
        Assertions.assertThrows(NotFoundException.class, () -> managerSongsController.getAllMySong());
    }

    @Test
    void saveAndUpdateMySong() {
        Mockito.when(songsService.saveAndUpdateMySong(songs.get(0), user.getUsername())).thenReturn("success");

        String result = managerSongsController.saveAndUpdateMySong(songs.get(0));

        Assertions.assertEquals(result, "success");
    }

    @Test
    void getMySong() {

        Mockito.when(songsService.getMySongs(1)).thenReturn(songs.get(0));

        Songs song = managerSongsController.getMySong(1);

        Assertions.assertNotNull(song);
    }

    @Test
    void testGetMySongIsNull(){
        Mockito.when(songsService.getMySongs(1)).thenReturn(songNull);

        Assertions.assertThrows(NotFoundException.class, () -> managerSongsController.getMySong(1));

    }

    @Test
    void deleteMySong() {

    }

    @Test
    void searchMySong() {
    }
}