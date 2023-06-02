package com.example.coursework.controllers;

import com.example.coursework.models.Channel;
import com.example.coursework.models.Programme;
import com.example.coursework.repository.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/channels")
public class ChannelsController {

    private final ChannelRepository channelRepository;

    @Autowired
    public ChannelsController(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @ModelAttribute("filterParameters")
    public List<String> getFilterParameters() {
        return new ArrayList<>(List.of("title", "time", "description")){};
    }

    @ModelAttribute("filterChannelsParameters")
    public List<String> getFilterChannelsParameters() {
        return new ArrayList<>(List.of("direct", "reversed")){};
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

    @GetMapping("/filterBy")
    public String filterByParameter(@RequestParam("filterParameter") String filterParameter,
                                    Model model) {
        List<Channel> channels = channelRepository.findAll();

        switch (filterParameter) {
            case "direct" -> channels.sort(Comparator.comparing(Channel::getTitle,
                    String.CASE_INSENSITIVE_ORDER));
            case "reversed" -> channels.sort(Comparator.comparing(Channel::getTitle,
                    String.CASE_INSENSITIVE_ORDER).reversed());
        }

        model.addAttribute("channels", channels);
        return "channels";
    }

    @GetMapping("/{title}/filterBy")
    public String filterByParameter(@PathVariable("title") String title,
            @RequestParam("filterParameter") String filterParameter,
                                    Model model) {
       Channel channel = channelRepository.findChannelByTitle(title);
       List<Programme> programmes = channel.getProgrammes();

        switch (filterParameter) {
            case "title" -> programmes.sort(Comparator.comparing(Programme::getTitle,
                    String.CASE_INSENSITIVE_ORDER));
            case "time" -> programmes.sort(Comparator.comparing(Programme::getTime,
                    String.CASE_INSENSITIVE_ORDER));
            case "description" -> programmes.sort(Comparator.comparing(Programme::getDescription,
                    String.CASE_INSENSITIVE_ORDER));
        }

        channel.setProgrammes(programmes);
        model.addAttribute("channel", channelRepository.findChannelByTitle(title));
        return "programme";
    }

}
