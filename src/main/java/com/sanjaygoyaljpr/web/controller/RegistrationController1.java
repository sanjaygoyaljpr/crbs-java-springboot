package com.sanjaygoyaljpr.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sanjaygoyaljpr.event.OnRegistrationCompleteEvent;
import com.sanjaygoyaljpr.persistence.model.Privilege;
import com.sanjaygoyaljpr.persistence.model.User;
import com.sanjaygoyaljpr.persistence.model.VerificationToken;
import com.sanjaygoyaljpr.service.IUserService;
import com.sanjaygoyaljpr.web.util.GenericResponse;

//@Controller
public class RegistrationController1 {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IUserService userService;

	// @Autowired
	// private ISecurityUserService securityUserService;

	@Autowired
	private MessageSource messages;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private Environment env;

	/*@Autowired
	private AuthenticationManager authenticationManager;*/

	public RegistrationController1() {
		super();
	}

	// Registration
	
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult, final HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user", "There is already a user registered with the email provided");
		}

		if (!StringUtils.isEmpty(user.getEmail()) && !(user.getEmail().endsWith("a3logics.in") || user.getEmail().endsWith("teemwurk.com"))) {
			bindingResult.rejectValue("email", "error.user", "Please use your A3logics or teemwurk domain email id");
		}

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			//final User registered = userService.registerNewUserAccount(user);

			//eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));
			
			modelAndView.addObject("successMessage", "You registered successfully. We will send you a confirmation message to your email account. ");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");

		}
		return modelAndView;
	}

	//@RequestMapping(value = "/user/registration", method = RequestMethod.POST)
	//@ResponseBody
	public GenericResponse registerUserAccount(@Valid final User user, final HttpServletRequest request) {
		LOGGER.debug("Registering user account with information: {}", user);
		//final User registered = userService.registerNewUserAccount(user);
		//eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));
		return new GenericResponse("success");
	}

	@RequestMapping(value = "/registrationConfirm", method = RequestMethod.GET)
	public String confirmRegistration(final HttpServletRequest request, final Model model, @RequestParam("token") final String token)
			throws UnsupportedEncodingException {
		Locale locale = request.getLocale();
		final String result = userService.validateVerificationToken(token);

		if (result.equals("valid")) {
			final User user = userService.getUser(token);

			authWithoutPassword(user);

			model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));

			return "redirect:/console.html?lang=" + locale.getLanguage();
		}

		model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
		model.addAttribute("expired", "expired".equals(result));
		model.addAttribute("token", token);
		return "redirect:/badUser.html?lang=" + locale.getLanguage();
	}

	// user activation - verification

	@RequestMapping(value = "/user/resendRegistrationToken", method = RequestMethod.GET)
	@ResponseBody
	public GenericResponse resendRegistrationToken(final HttpServletRequest request, @RequestParam("token") final String existingToken) {
		final VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
		final User user = userService.getUser(newToken.getToken());
		mailSender.send(constructResendVerificationTokenEmail(getAppUrl(request), request.getLocale(), newToken, user));
		return new GenericResponse(messages.getMessage("message.resendToken", null, request.getLocale()));
	}

	// Non-APIs
	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	private SimpleMailMessage constructResendVerificationTokenEmail(final String contextPath, final Locale locale, final VerificationToken newToken,
			final User user) {
		final String confirmationUrl = contextPath + "/registrationConfirm.html?token=" + newToken.getToken();
		final String message = messages.getMessage("message.resendToken", null, locale);
		return constructEmail("Resend Registration Token", message + " \r\n" + confirmationUrl, user);
	}

	private SimpleMailMessage constructEmail(String subject, String body, User user) {
		final SimpleMailMessage email = new SimpleMailMessage();
		email.setSubject(subject);
		email.setText(body);
		email.setTo(user.getEmail());
		email.setFrom(env.getProperty("support.email"));
		return email;
	}

	public void authWithoutPassword(User user) {
		List<Privilege> privileges = user.getRoles().stream().map(role -> role.getPrivileges()).flatMap(list -> list.stream()).distinct()
				.collect(Collectors.toList());

		List<GrantedAuthority> authorities = privileges.stream().map(p -> new SimpleGrantedAuthority(p.getName())).collect(Collectors.toList());

		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

}
