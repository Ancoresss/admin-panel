package com.adpanel.adpanel.service;

import com.adpanel.adpanel.model.Link;

public interface LinkService {
    void addLink(Link link);
    Link getLink(String link);
}
