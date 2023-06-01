package com.example.coursework.controllers;


import com.example.coursework.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/channels")
public class ChannelsController {

    private final ChannelRepository channelRepository;

    @Autowired
    public ChannelsController(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @GetMapping
    public String getChannels(Model model) {
        model.addAttribute("channels",
                channelRepository.findAll());
        return "channels";
    }

    @GetMapping("{title}")
    public String getProgrammes(@PathVariable("title") String title,
            Model model) {
        model.addAttribute("channel", channelRepository.findChannelByTitle(title));
        return "programme";
    }
}
