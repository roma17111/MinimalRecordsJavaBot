package com.example.minrecbotjava.repository;

import com.example.minrecbotjava.entity.MusicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicInfoRepository extends JpaRepository<MusicInfo,Long> {

}
