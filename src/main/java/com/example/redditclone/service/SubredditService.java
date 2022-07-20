package com.example.redditclone.service;

import com.example.redditclone.dto.SubredditDto;
import com.example.redditclone.exception.ResourceNotFoundException;
import com.example.redditclone.mapper.SubredditMapper;
import com.example.redditclone.model.Subreddit;
import com.example.redditclone.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SubredditService {

    @Autowired
    private final SubredditRepository subredditRepository;
    @Autowired
    private final SubredditMapper subredditMapper;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit subredditToSave = subredditMapper.mapDtoToSubreddit(subredditDto);
        Subreddit subreddit = subredditRepository.save(subredditToSave);
        subredditDto.setId(subreddit.getId());
        return subredditDto;
    }


    @Transactional
    public SubredditDto getSubreddit(Long id) {
        Subreddit subreddit = subredditRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subreddit with id" + id + " not found"));
        return subredditMapper.mapSubredditToDto(subreddit);
    }

    @Transactional
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToDto)
                .collect(Collectors.toList());
    }
}
