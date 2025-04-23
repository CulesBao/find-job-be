package com.findjobbe.findjobbe.mapper.dto;

import com.findjobbe.findjobbe.enums.SocialLinkType;
import com.findjobbe.findjobbe.model.SocialLink;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialLinkDto {
  private UUID id;
  private SocialLinkType type;
  @NotNull
  @NotBlank
  @Length(min = 1)
  private String url;

  public SocialLinkDto(SocialLink socialLink) {
    this.id = socialLink.getId();
    this.type = socialLink.getType();
    this.url = socialLink.getUrl();
  }
}
