package com.findjobbe.findjobbe.mapper.dto;

import com.findjobbe.findjobbe.enums.SocialLinkType;
import com.findjobbe.findjobbe.model.SocialLink;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialLinkDto {
  private SocialLinkType type;
  private String url;

  public SocialLinkDto(SocialLink socialLink) {
    this.type = socialLink.getType();
    this.url = socialLink.getUrl();
  }
}
