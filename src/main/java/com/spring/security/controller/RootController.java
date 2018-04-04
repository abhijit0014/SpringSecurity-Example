package com.spring.security.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.security.entity.User;
import com.spring.security.entity.User.Role;
import com.spring.security.service.UserService;
import com.spring.security.service.VerificationTokenService;


@Controller
public class RootController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final VerificationTokenService verificationTokenService;
	public RootController(UserService userService, AuthenticationManager authenticationManager,
			VerificationTokenService verificationTokenService) {
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.verificationTokenService = verificationTokenService;
	}

	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}	
	
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute(new User());
		return "signup";
	}
	
	@RequestMapping(value="/signup", method = RequestMethod.POST)
	public String signup(@ModelAttribute @Valid User user, BindingResult result) {
		if (result.hasErrors()) return "signup";
		user.getRoles().add(Role.UNVERIFIED);
		userService.register(user);
		return "redirect:/";
	}	
	
	@RequestMapping(value="/emailVerification/{token}",method=RequestMethod.GET)
	@ResponseBody
	public String emailVerification(@PathVariable String token, HttpServletRequest request, 
			HttpServletResponse response) {
		User user = verificationTokenService.verifyToken(token);
		if(user!=null) {
			userService.enable(user.getUser_id());
			authWithAuthManager(request,user.getEmail(), user.getPassword());
			return "redirect:/login";
		}
		return "new verification link has been sent";
	}
	
	// auto login after registration
	private void authWithAuthManager(HttpServletRequest request, String username, String password) {
	    UsernamePasswordAuthenticationToken authToken = 
	    		new UsernamePasswordAuthenticationToken(username, password);
	    authToken.setDetails(new WebAuthenticationDetails(request));
	    
	    Authentication authentication = authenticationManager.authenticate(authToken);
	    SecurityContextHolder.getContext().setAuthentication(authentication);
	}	
}
