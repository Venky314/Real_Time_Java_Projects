package com.ashok.it.usermanagement.service;

import java.util.List;

import com.ashok.it.usermanagement.dto.ApiResponse;
import com.ashok.it.usermanagement.dto.CityDto;
import com.ashok.it.usermanagement.dto.CountryDto;
import com.ashok.it.usermanagement.dto.QuoteApiResponseDto;
import com.ashok.it.usermanagement.dto.ResetPwdDto;
import com.ashok.it.usermanagement.dto.StateDto;
import com.ashok.it.usermanagement.dto.UserDto;

public interface UserService {
    
    ApiResponse<List<CountryDto>> getAllCountries();
    
    ApiResponse<List<StateDto>> getStatesByCountry(Integer countryId);
    
    ApiResponse<List<CityDto>> getCitiesByState(Integer stateId);
    
    ApiResponse<String> registerUser(UserDto userDto);
    
    ApiResponse<String> loginUser(String email, String password);
    
    ApiResponse<String> resetPassword(ResetPwdDto resetPwdDto);
    
    ApiResponse<QuoteApiResponseDto> getRandomQuote();
}
