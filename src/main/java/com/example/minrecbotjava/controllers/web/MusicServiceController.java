package com.example.minrecbotjava.controllers.web;

import com.example.minrecbotjava.entity.MusicInfo;
import com.example.minrecbotjava.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("service")
public class MusicServiceController {

    private final MusicService musicService;

    @GetMapping("/all")
    @Operation(summary = "Посмотреть список всех заявок на оказание услуг звукозаписи")
    public List<MusicInfo> findAll() {
        return musicService.findAll();
    }

    @DeleteMapping("/all/remove")
    @Operation(summary = "Удалить все заявки на оказание услуг звукозаписи")
    public ResponseEntity<?> deleteAllMusicServices() {
        musicService.deleteAll();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/all/{id}")
    @Operation(summary = "Удалить заявку на оказание услуг звукозаписи по ID из базы данных")
    public ResponseEntity<?> deleteMusicServiceById(@PathVariable long id) {
        musicService.deleteMusicServiceById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all/{id}")
    @Operation(summary = "Получить заявку на оказание услуг звукозаписи по ID из базы данных")
    public MusicInfo findMusicServiceById(@PathVariable long id) {
        return musicService.findById(id);
    }

}
