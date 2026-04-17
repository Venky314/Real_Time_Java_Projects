package com.ashok.it.usermanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ashok.it.usermanagement.dto.ApiResponse;
import com.ashok.it.usermanagement.dto.CityDto;
import com.ashok.it.usermanagement.dto.CountryDto;
import com.ashok.it.usermanagement.dto.QuoteApiResponseDto;
import com.ashok.it.usermanagement.dto.ResetPwdDto;
import com.ashok.it.usermanagement.dto.StateDto;
import com.ashok.it.usermanagement.dto.UserDto;
import com.ashok.it.usermanagement.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class WebController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }
    
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String email, 
                       @RequestParam String password,
                       HttpSession session,
                       RedirectAttributes redirectAttributes) {
        ApiResponse<String> response = userService.loginUser(email, password);
        
        if ("SUCCESS".equals(response.getStatus())) {
            session.setAttribute("userEmail", email);
            session.setAttribute("userName", email.split("@")[0]);
            
            if ("RESET_REQUIRED".equals(response.getData())) {
                redirectAttributes.addFlashAttribute("message", "Please reset your password");
                return "redirect:/reset-password";
            }
            
            return "redirect:/dashboard";
        } else {
            redirectAttributes.addFlashAttribute("error", response.getMessage());
            return "redirect:/login";
        }
    }
    
    @GetMapping("/register")
    public String registerPage(Model model) {
        ApiResponse<List<CountryDto>> countriesResponse = userService.getAllCountries();
        if ("SUCCESS".equals(countriesResponse.getStatus())) {
            model.addAttribute("countries", countriesResponse.getData());
        }
        model.addAttribute("userDto", new UserDto());
        return "register";
    }
    
    @PostMapping("/register")
    public String register(@ModelAttribute UserDto userDto, RedirectAttributes redirectAttributes) {
        ApiResponse<String> response = userService.registerUser(userDto);
        
        if ("SUCCESS".equals(response.getStatus())) {
            redirectAttributes.addFlashAttribute("success", response.getMessage());
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("error", response.getMessage());
            return "redirect:/register";
        }
    }
    
    @GetMapping("/reset-password")
    public String resetPasswordPage(Model model) {
        model.addAttribute("resetPwdDto", new ResetPwdDto());
        return "reset-password";
    }
    
    @PostMapping("/reset-password")
    public String resetPassword(@ModelAttribute ResetPwdDto resetPwdDto,
                               RedirectAttributes redirectAttributes) {
        ApiResponse<String> response = userService.resetPassword(resetPwdDto);
        
        if ("SUCCESS".equals(response.getStatus())) {
            redirectAttributes.addFlashAttribute("success", response.getMessage());
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("error", response.getMessage());
            return "redirect:/reset-password";
        }
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            return "redirect:/login";
        }
        
        ApiResponse<QuoteApiResponseDto> quoteResponse = userService.getRandomQuote();
        if ("SUCCESS".equals(quoteResponse.getStatus())) {
            model.addAttribute("quote", quoteResponse.getData());
        }
        
        model.addAttribute("userName", session.getAttribute("userName"));
        return "dashboard";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
    
    @GetMapping("/api/states/{countryId}")
    public String getStatesByCountry(@PathVariable Integer countryId, Model model) {
        ApiResponse<List<StateDto>> response = userService.getStatesByCountry(countryId);
        if ("SUCCESS".equals(response.getStatus())) {
            model.addAttribute("states", response.getData());
        }
        return "fragments/state-options :: stateOptions";
    }
    
    @GetMapping("/api/cities/{stateId}")
    public String getCitiesByState(@PathVariable Integer stateId, Model model) {
        ApiResponse<List<CityDto>> response = userService.getCitiesByState(stateId);
        if ("SUCCESS".equals(response.getStatus())) {
            model.addAttribute("cities", response.getData());
        }
        return "fragments/city-options :: cityOptions";
    }
}
