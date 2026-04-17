package com.ashok.it.usermanagement.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    
    @Column(name = "uname")
    private String userName;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "pwd")
    private String password;
    
    @Column(name = "pwd_updated")
    private String passwordUpdated;
    
    @Column(name = "phno")
    private String phoneNumber;
    
    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;
    
    @ManyToOne
    @JoinColumn(name = "state_id")
    private StateEntity state;
    
    @ManyToOne
    @JoinColumn(name = "city_id")
    private CityEntity city;
    
    @Column(name = "created_dt")
    private LocalDateTime createdDate;
    
    @Column(name = "updated_dt")
    private LocalDateTime updatedDate;
}
