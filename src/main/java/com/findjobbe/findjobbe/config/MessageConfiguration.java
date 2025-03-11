package com.findjobbe.findjobbe.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MessageConfiguration {

  static final String DEFAULT_ENCODING = "UTF-8";

  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames(
        "classpath:messages/ExceptionMessage",
        "classpath:messages/GeneralMessage",
        "classpath:messages/ValidationMesssage");
    messageSource.setDefaultEncoding(DEFAULT_ENCODING);
    return messageSource;
  }

  @Bean
  public LocalValidatorFactoryBean getValidator() {

    final LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
    bean.setValidationMessageSource(messageSource());
    return bean;
  }
}
