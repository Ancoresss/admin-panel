package com.adpanel.adpanel.service;

import com.adpanel.adpanel.model.Link;
import com.adpanel.adpanel.repo.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkServiceImpl implements LinkService {
    @Autowired
    private LinkRepository linkRepository;

    @Override
    public Link getLink(String link) {
        return linkRepository.findByLink(link);
    }

    @Override
    public void addLink(Link link) {
        linkRepository.save(link);
    }
}
