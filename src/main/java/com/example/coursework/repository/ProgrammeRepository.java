package com.example.coursework.repository;

import com.example.coursework.models.Channel;
import com.example.coursework.models.Programme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammeRepository extends JpaRepository<Programme, Long> {
    void deleteAllByChannel(Channel channel);
}
