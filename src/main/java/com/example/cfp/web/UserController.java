package com.example.cfp.web;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Deprecated // Should be removed, only used by the current UI
public class UserController {

	@RequestMapping("/me")
	public Principal user(Principal principal) {
		return principal;
	}

}
