package com.javatpoint.web;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.javatpoint.models.Message;
import com.javatpoint.repositories.MessageRepository;

@Controller
public class SimpleController {
    @Value("${spring.application.name}")
    String appName;
    @Autowired
    private MessageRepository messageRepository;

//    @GetMapping("/user/home")
//    public String userhome(Model model) {
//        return "userhome";
//    }
//    @GetMapping("/admin/home")
//    public String adminhome(Model model) {
//        return "adminhome";
//    }

    @PostMapping("/messages")
    public String saveMessage(Message message) {
        messageRepository.save(message);
        return "redirect:/home";
    }
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", appName);
        return "home";
    }
//    @GetMapping("/login")
//    public String login(Model model) {
//        return "login";
//    }
//    
    @GetMapping("/sample")
    public String showForm() {
        return "sample";
    }
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
          = new ReloadableResourceBundleMessageSource();
         
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

}