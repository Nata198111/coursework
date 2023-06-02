package com.example.coursework.controllers;

import com.example.coursework.models.AddChannelFormResponse;
import com.example.coursework.models.AddProgrammesFormResponse;
import com.example.coursework.models.Channel;
import com.example.coursework.models.Programme;
import com.example.coursework.repository.ChannelRepository;
import com.example.coursework.repository.ProgrammeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/adminPage")
public class AdminController {

    private final ChannelRepository channelRepository;
    private final AddChannelFormResponse addChannelFormResponse;
    private final AddProgrammesFormResponse addProgrammesFormResponse;
    private final ProgrammeRepository programmeRepository;

    @Autowired
    public AdminController(ChannelRepository channelRepository,
                           AddChannelFormResponse addChannelFormResponse,
                           AddProgrammesFormResponse addProgrammesFormResponse,
                           ProgrammeRepository programmeRepository) {
        this.addChannelFormResponse = addChannelFormResponse;
        this.channelRepository = channelRepository;
        this.addProgrammesFormResponse = addProgrammesFormResponse;
        this.programmeRepository = programmeRepository;
    }

    @GetMapping
    public String getChannels(Model model) {
        model.addAttribute("channels",
                channelRepository.findAll());
        return "addProgrammesToChannels";
    }

    @GetMapping("/addChannels")
    public String createChannels(Model model) {
        model.addAttribute("addChannelFormResponse", addChannelFormResponse);
        return "addChannel";
    }

    @GetMapping("{title}")
    public String getProgrammes(@PathVariable("title") String title,
                                Model model) {
        model.addAttribute("channel", channelRepository.findChannelByTitle(title));
        return "programmeForAdmin";
    }

    @PostMapping("/addChannels")
    public String processCreatingChannels(@ModelAttribute("addChannelFormResponse")
                                              AddChannelFormResponse addChannelFormResponse) {
        var channel = Channel.builder()
                .title(addChannelFormResponse.getTitle())
                .description(addChannelFormResponse.getDescription())
                .build();
        channelRepository.save(channel);
        return "redirect:/adminPage";
    }

    @GetMapping("/addProgrammes/{channel}")
    public String addProgrammes(@PathVariable("channel") String channel,
            Model model) {

        addProgrammesFormResponse.setTime("");
        addProgrammesFormResponse.setTitle("");
        addProgrammesFormResponse.setDescription("");

        model.addAttribute("channel", channel);
        model.addAttribute("addProgrammesFormResponse", addProgrammesFormResponse);
        return "addProgrammes";
    }

    @PostMapping("/addProgrammes/{channel}")
    public String processAddProgrammes(@PathVariable("channel") String channelTitle,
                                       @ModelAttribute("addProgrammesFormResponse")
                                       AddProgrammesFormResponse addProgrammesFormResponse) {
        Channel channel = channelRepository.findChannelByTitle(channelTitle);

        var programme = Programme.builder()
                .title(addProgrammesFormResponse.getTitle())
                .description(addProgrammesFormResponse.getDescription())
                .time(addProgrammesFormResponse.getTime())
                .channel(channel)
                .build();

        programmeRepository.save(programme);


        return "redirect:/adminPage";
    }

    @Transactional
    @DeleteMapping("/deleteProgrammes/{channel}")
    public String processDelete(@PathVariable("channel") String channelTitle) {

        Channel channel = channelRepository.findChannelByTitle(channelTitle);
        programmeRepository.deleteAllByChannel(channel);
        return "redirect:/adminPage";
    }

    @GetMapping("/editProgrammeByID/{id}")
    public String editProgramme(@PathVariable("id") Long id,
                                Model model) {
        Programme programme = programmeRepository.findById(id).get();

        addProgrammesFormResponse.setTitle(programme.getTitle());
        addProgrammesFormResponse.setDescription(programme.getDescription());
        addProgrammesFormResponse.setTime(programme.getTime());

        model.addAttribute("programme", programme);
        model.addAttribute("addProgrammesFormResponse", addProgrammesFormResponse);
        return "editProgramme";
    }

    @PatchMapping("/editProgrammeByID/{id}")
    public String processEditProgramme(@PathVariable("id") Long id,
                                       @ModelAttribute("addProgrammesFormResponse")
                                       AddProgrammesFormResponse addProgrammesFormResponse) {
        Programme programme = programmeRepository.findById(id).get();

        programme.setTitle(addProgrammesFormResponse.getTitle());
        programme.setDescription(addProgrammesFormResponse.getDescription());
        programme.setTime(addProgrammesFormResponse.getTime());

        programmeRepository.save(programme);
        return "redirect:/adminPage";
    }

    @Transactional
    @DeleteMapping("/deleteChannel/{channel}")
    public String processDeleteChannel(@PathVariable("channel") String channelTitle) {

        channelRepository.deleteByTitle(channelTitle);
        return "redirect:/adminPage";
    }

    @GetMapping("/editChannelByID/{id}")
    public String editChannel(@PathVariable("id") Long id,
                                Model model) {
        Channel channel = channelRepository.findById(id).get();

        addChannelFormResponse.setTitle(channel.getTitle());
        addChannelFormResponse.setDescription(channel.getDescription());

        model.addAttribute("channel", channel);
        model.addAttribute("addChannelFormResponse", addChannelFormResponse);
        return "editChannels";
    }

    @PatchMapping("/editChannelByID/{id}")
    public String processEditChannel(@PathVariable("id") Long id,
                                       @ModelAttribute("addChannelFormResponse")
                                       AddChannelFormResponse addChannelFormResponse) {
        Channel channel = channelRepository.findById(id).get();

        channel.setTitle(addChannelFormResponse.getTitle());
        channel.setDescription(addChannelFormResponse.getDescription());

        channelRepository.save(channel);
        return "redirect:/adminPage";
    }
}
