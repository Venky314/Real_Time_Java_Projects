package com.ashok.it.usermanagement.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ashok.it.usermanagement.dto.ApiResponse;
import com.ashok.it.usermanagement.dto.CityDto;
import com.ashok.it.usermanagement.dto.CountryDto;
import com.ashok.it.usermanagement.dto.QuoteApiResponseDto;
import com.ashok.it.usermanagement.dto.ResetPwdDto;
import com.ashok.it.usermanagement.dto.StateDto;
import com.ashok.it.usermanagement.dto.UserDto;
import com.ashok.it.usermanagement.entity.CityEntity;
import com.ashok.it.usermanagement.entity.CountryEntity;
import com.ashok.it.usermanagement.entity.StateEntity;
import com.ashok.it.usermanagement.entity.UserEntity;
import com.ashok.it.usermanagement.repository.CityRepository;
import com.ashok.it.usermanagement.repository.CountryRepository;
import com.ashok.it.usermanagement.repository.StateRepository;
import com.ashok.it.usermanagement.repository.UserRepo;
import com.ashok.it.usermanagement.service.EmailService;
import com.ashok.it.usermanagement.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private CountryRepository countryRepository;
    
    @Autowired
    private StateRepository stateRepository;
    
    @Autowired
    private CityRepository cityRepository;
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Override
    public ApiResponse<List<CountryDto>> getAllCountries() {
        try {
            List<CountryEntity> countries = countryRepository.findAll();
            List<CountryDto> countryDtos = countries.stream()
                .map(country -> new CountryDto(country.getCountryId(), country.getCountryName()))
                .collect(Collectors.toList());
            return ApiResponse.success("Countries retrieved successfully", countryDtos);
        } catch (Exception e) {
            return ApiResponse.error("Failed to retrieve countries: " + e.getMessage());
        }
    }
    
    @Override
    public ApiResponse<List<StateDto>> getStatesByCountry(Integer countryId) {
        try {
            List<StateEntity> states = stateRepository.findByCountryId(countryId);
            List<StateDto> stateDtos = states.stream()
                .map(state -> new StateDto(state.getStateId(), state.getStateName(), state.getCountry().getCountryId()))
                .collect(Collectors.toList());
            return ApiResponse.success("States retrieved successfully", stateDtos);
        } catch (Exception e) {
            return ApiResponse.error("Failed to retrieve states: " + e.getMessage());
        }
    }
    
    @Override
    public ApiResponse<List<CityDto>> getCitiesByState(Integer stateId) {
        try {
            List<CityEntity> cities = cityRepository.findByStateId(stateId);
            List<CityDto> cityDtos = cities.stream()
                .map(city -> new CityDto(city.getCityId(), city.getCityName(), city.getState().getStateId()))
                .collect(Collectors.toList());
            return ApiResponse.success("Cities retrieved successfully", cityDtos);
        } catch (Exception e) {
            return ApiResponse.error("Failed to retrieve cities: " + e.getMessage());
        }
    }
    
    @Override
    public ApiResponse<String> registerUser(UserDto userDto) {
        try {
            if (userRepo.existsByEmail(userDto.getEmail())) {
                return ApiResponse.error("Email already exists");
            }
            
            CountryEntity country = countryRepository.findById(userDto.getCountryId())
                .orElseThrow(() -> new RuntimeException("Country not found"));
            StateEntity state = stateRepository.findById(userDto.getStateId())
                .orElseThrow(() -> new RuntimeException("State not found"));
            CityEntity city = cityRepository.findById(userDto.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found"));
            
            String tempPassword = generateTempPassword();
            
            UserEntity user = new UserEntity();
            user.setUserName(userDto.getUserName());
            user.setEmail(userDto.getEmail());
            user.setPassword(tempPassword);
            user.setPasswordUpdated("NO");
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setCountry(country);
            user.setState(state);
            user.setCity(city);
            user.setCreatedDate(LocalDateTime.now());
            user.setUpdatedDate(LocalDateTime.now());
            
            userRepo.save(user);
            
            boolean emailSent = emailService.sendRegistrationEmail(userDto.getEmail(), userDto.getUserName(), tempPassword);
            
            if (emailSent) {
                return ApiResponse.success("User registered successfully. Temporary password sent to email.");
            } else {
                return ApiResponse.success("User registered successfully. However, email sending failed. Temp password: " + tempPassword);
            }
        } catch (Exception e) {
            return ApiResponse.error("Registration failed: " + e.getMessage());
        }
    }
    
    @Override
    public ApiResponse<String> loginUser(String email, String password) {
        try {
            Optional<UserEntity> userOpt = userRepo.findByEmailAndPassword(email, password);
            if (userOpt.isPresent()) {
                UserEntity user = userOpt.get();
                if ("NO".equals(user.getPasswordUpdated())) {
                    return ApiResponse.success("Login successful. Please reset your password.", "RESET_REQUIRED");
                }
                return ApiResponse.success("Login successful");
            } else {
                return ApiResponse.error("Invalid email or password");
            }
        } catch (Exception e) {
            return ApiResponse.error("Login failed: " + e.getMessage());
        }
    }
    
    @Override
    public ApiResponse<String> resetPassword(ResetPwdDto resetPwdDto) {
        try {
            Optional<UserEntity> userOpt = userRepo.findByEmail(resetPwdDto.getEmail());
            if (userOpt.isPresent()) {
                UserEntity user = userOpt.get();
                if (!user.getPassword().equals(resetPwdDto.getOldPassword())) {
                    return ApiResponse.error("Old password is incorrect");
                }
                user.setPassword(resetPwdDto.getNewPassword());
                user.setPasswordUpdated("YES");
                user.setUpdatedDate(LocalDateTime.now());
                userRepo.save(user);
                
                boolean emailSent = emailService.sendPasswordResetEmail(resetPwdDto.getEmail(), user.getUserName());
                
                return ApiResponse.success("Password reset successfully");
            } else {
                return ApiResponse.error("User not found");
            }
        } catch (Exception e) {
            return ApiResponse.error("Password reset failed: " + e.getMessage());
        }
    }
    
    @Override
    public ApiResponse<QuoteApiResponseDto> getRandomQuote() {
        try {
            String url = "https://dummyjson.com/quotes/random";
            QuoteApiResponseDto quote = restTemplate.getForObject(url, QuoteApiResponseDto.class);
            return ApiResponse.success("Quote retrieved successfully", quote);
        } catch (Exception e) {
            return ApiResponse.error("Failed to retrieve quote: " + e.getMessage());
        }
    }
    
    private String generateTempPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }
}
