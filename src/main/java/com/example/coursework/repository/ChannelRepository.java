package com.example.coursework.repository;

import com.example.coursework.models.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Channel findChannelByTitle(String title);
    void deleteByTitle(String title);
}
