package com.ashok.it.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashok.it.usermanagement.dto.ApiResponse;
import com.ashok.it.usermanagement.dto.QuoteApiResponseDto;
import com.ashok.it.usermanagement.dto.ResetPwdDto;
import com.ashok.it.usermanagement.dto.UserDto;
import com.ashok.it.usermanagement.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management API", description = "APIs for user management operations")
public class UserRestController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/countries")
    @Operation(summary = "Get all countries", description = "Retrieves list of all countries")
    public ResponseEntity<ApiResponse<?>> getAllCountries() {
        return ResponseEntity.ok(userService.getAllCountries());
    }
    
    @GetMapping("/states/{countryId}")
    @Operation(summary = "Get states by country", description = "Retrieves states for a given country ID")
    public ResponseEntity<ApiResponse<?>> getStatesByCountry(@PathVariable Integer countryId) {
        return ResponseEntity.ok(userService.getStatesByCountry(countryId));
    }
    
    @GetMapping("/cities/{stateId}")
    @Operation(summary = "Get cities by state", description = "Retrieves cities for a given state ID")
    public ResponseEntity<ApiResponse<?>> getCitiesByState(@PathVariable Integer stateId) {
        return ResponseEntity.ok(userService.getCitiesByState(stateId));
    }
    
    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Registers a new user and sends temporary password via email")
    public ResponseEntity<ApiResponse<String>> registerUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.registerUser(userDto));
    }
    
    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates user with email and password")
    public ResponseEntity<ApiResponse<String>> loginUser(
            @RequestParam String email, 
            @RequestParam String password) {
        return ResponseEntity.ok(userService.loginUser(email, password));
    }
    
    @PostMapping("/reset-password")
    @Operation(summary = "Reset password", description = "Resets user password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestBody ResetPwdDto resetPwdDto) {
        return ResponseEntity.ok(userService.resetPassword(resetPwdDto));
    }
    
    @GetMapping("/dashboard/quote")
    @Operation(summary = "Get random quote", description = "Retrieves a random quote for dashboard")
    public ResponseEntity<ApiResponse<QuoteApiResponseDto>> getRandomQuote() {
        return ResponseEntity.ok(userService.getRandomQuote());
    }
}
