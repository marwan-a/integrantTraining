package com.javatpoint.controllers;


import java.util.Calendar;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import com.javatpoint.HibernateUtil;
import com.javatpoint.dto.UserDto;
import com.javatpoint.exceptions.EmailExistsException;
import com.javatpoint.models.UserRecord;
import com.javatpoint.repositories.UserRepository;
import com.javatpoint.repositories.ConfirmationTokenRepository;
import com.javatpoint.services.EmailSenderService;
import com.javatpoint.services.UserService;
import com.javatpoint.validations.ValidationSequence;
import com.javatpoint.verification.ConfirmationToken;
import com.javatpoint.web.GenericResponse;;
@RestController
public class RegistrationController {
	@Autowired
    private MessageSource messages;
	@Autowired
	private UserService userService; 
	@Autowired
	ApplicationEventPublisher eventPublisher;
	@Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private EmailSenderService emailSenderService;
	@RequestMapping(value = "/user/registration", method = RequestMethod.GET)
	public String showRegistrationForm(WebRequest request, Model model) {
	    UserDto userDto = new UserDto();
	    userDto.setName("test");
	    model.addAttribute("user", userDto);
	    return "registration";
	}
	
	@RequestMapping(value = "/user/registration", method = RequestMethod.POST)
	public ModelAndView registerUserAccount(
	  @ModelAttribute("user") @Validated(ValidationSequence.class) @Valid UserDto accountDto, 
	  BindingResult result, 
	  WebRequest request, 
	  Errors errors) {
	    
		if (result.hasErrors()) {
	        return new ModelAndView("registration", "user", accountDto);
	    }
	     
	    UserRecord registered = createUserAccount(accountDto);
	    if (registered == null) {
	        result.rejectValue("email", "email.registered");
	        return new ModelAndView("registration", "user", accountDto);
	    }
	    else
	    {
		    Session session=HibernateUtil.getSessionFactory().openSession();
	    	Transaction transaction = session.beginTransaction();

            ConfirmationToken confirmationToken = new ConfirmationToken(registered);
	        session.save(confirmationToken);
	        SimpleMailMessage mailMessage = new SimpleMailMessage();
	        mailMessage.setTo(registered.getEmail());
	        mailMessage.setSubject("Complete Registration!");
	        mailMessage.setFrom("marwanayman1998@gmail.com");
	        mailMessage.setText("To confirm your account, please click here : "
	        +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());

	        emailSenderService.sendEmail(mailMessage);
	        ModelAndView modelAndView=new ModelAndView();
	        modelAndView.addObject("emailId", registered.getEmail());
	        modelAndView.setViewName("successfulRegisteration");
	        transaction.commit();
	        session.close();
	        return modelAndView;
	    }
	}
	@RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {	
		try {
			ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
	        if(token != null)
	        {	
	            UserRecord user = userRepository.findByEmail(token.getUser().getEmail());
	            if (!user.isEnabled())
	            {
	            	Calendar cal = Calendar.getInstance();
	                if ((token.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	            		modelAndView.addObject("message", "Expired Token");
	            		modelAndView.setViewName("expiredToken");
	                }
	                else
	                {
	                    user.setEnabled(true);
	                	userRepository.save(user);
	                    modelAndView.setViewName("accountVerified");
	                }
	            }
	            else
	            {
	            	modelAndView.addObject("message", "This account is already verified");
	        		modelAndView.setViewName("alreadyVerified");
	            }
	        }
	        else
	        {
	            modelAndView.addObject("message","The link is invalid or broken!");
	            modelAndView.setViewName("invalidToken");
	        }
		}
		catch(EntityNotFoundException e)
		{
			System.out.println("-------------------------------");
			System.out.println("in catch block");
			System.out.println("-------------------------------");
			modelAndView.addObject("message","This account does not exist");
            modelAndView.setViewName("noAccount");
            return modelAndView;
		}
        return modelAndView;
    }
	private UserRecord createUserAccount(UserDto accountDto) {
		UserRecord registered = null;
	    try {
	        registered = userService.registerNewUserAccount(accountDto);
	    } catch (EmailExistsException e) {
	        return null;
	    }
	    return registered;
	}
	
// 			Resend Verification
	@RequestMapping(value = "/user/resendRegistrationToken", method = RequestMethod.GET)
	@ResponseBody
	public GenericResponse resendRegistrationToken(
	  HttpServletRequest request, @RequestParam("token") String existingToken) {
        ConfirmationToken token = userService.getVerificationToken(existingToken);
        token.setExpiryDate();
        confirmationTokenRepository.save(token);
	    UserRecord user = userService.getUser(token.getConfirmationToken());
	    SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Resend Registration Token");
        mailMessage.setFrom("marwanayman1998@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
        +"http://localhost:8080/confirm-account?token="+token.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);
	 
	    return new GenericResponse(
	      messages.getMessage("message.resendToken", null, request.getLocale()));
	}
	
}
