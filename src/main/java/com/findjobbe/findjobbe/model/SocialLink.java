package com.findjobbe.findjobbe.model;

import com.findjobbe.findjobbe.enums.SocialLinkType;
import jakarta.persistence.*;

@Entity
@Table(name = "social_links")
public class SocialLink extends AbstractEntity {
    @Column(nullable = false, name = "type")
    @Enumerated(EnumType.STRING)
    private SocialLinkType type;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
}
