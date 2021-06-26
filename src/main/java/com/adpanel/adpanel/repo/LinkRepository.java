package com.adpanel.adpanel.repo;

import com.adpanel.adpanel.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    Link findByLink(String link);
}
