package com.hillel.demo.core.application.contoller;

import com.hillel.demo.config.util.JwtUtil;
import com.hillel.demo.core.application.dto.LoginRequestDto;
import com.hillel.demo.core.application.dto.LoginResponseDto;
import com.hillel.demo.core.domain.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api("Login Controller authenticates user and generates token to login")
@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation("Login operation")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto) throws Exception {
        authenticate(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getUsername());
        final String token = jwtUtil.generateToken(userDetails);
        return new LoginResponseDto(token);
    }

    @ApiOperation("Authentication method")
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("Wrong credentials", e);
        }
    }
}
