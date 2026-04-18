package com.ashok.it.counsellorsportal.Controller;

import com.ashok.it.counsellorsportal.Model.Counsellor;
import com.ashok.it.counsellorsportal.Services.CounsellorService;
import com.ashok.it.counsellorsportal.Dto.DashboardResponseDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CounsellorController {

    @Autowired
    private CounsellorService counsellorService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("counsellor", new Counsellor());
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("counsellor", new Counsellor());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("counsellor") Counsellor counsellor, Model model) {
        boolean status = counsellorService.saveCounsellor(counsellor);
        if (status) {
            model.addAttribute("smsg", "Registration Successful");
        } else {
            model.addAttribute("emsg", "Email already exists");
        }
        return "register";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("counsellor") Counsellor counsellor, Model model, HttpSession session) {
        Counsellor loggedInCounsellor = counsellorService.login(counsellor.getEmail(), counsellor.getPwd());
        if (loggedInCounsellor != null) {
            session.setAttribute("counsellorId", loggedInCounsellor.getCounsellorId());
            session.setAttribute("counsellorName", loggedInCounsellor.getName());
            return "redirect:/dashboard";
        } else {
            model.addAttribute("emsg", "Invalid Credentials");
            return "index";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Integer counsellorId = (Integer) session.getAttribute("counsellorId");
        if (counsellorId == null) {
            return "redirect:/";
        }
        
        DashboardResponseDto dashboard = counsellorService.getDashboardInfo(counsellorId);
        model.addAttribute("dashboard", dashboard);
        model.addAttribute("counsellorName", session.getAttribute("counsellorName"));
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/edit-profile")
    public String editProfile(Model model, HttpSession session) {
        Integer counsellorId = (Integer) session.getAttribute("counsellorId");
        if (counsellorId == null) {
            return "redirect:/";
        }
        
        Counsellor counsellor = counsellorService.getCounsellorById(counsellorId);
        model.addAttribute("counsellor", counsellor);
        return "edit-profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute("counsellor") Counsellor counsellor, 
                               @RequestParam(required = false) String currentPassword,
                               @RequestParam(required = false) String newPassword,
                               @RequestParam(required = false) String confirmPassword,
                               Model model, HttpSession session) {
        Integer counsellorId = (Integer) session.getAttribute("counsellorId");
        if (counsellorId == null) {
            return "redirect:/";
        }
        
        // Verify the counsellor is updating their own profile
        if (!counsellor.getCounsellorId().equals(counsellorId)) {
            model.addAttribute("emsg", "Unauthorized access");
            return "edit-profile";
        }
        
        boolean status = counsellorService.updateProfile(counsellor, currentPassword, newPassword, confirmPassword);
        if (status) {
            model.addAttribute("smsg", "Profile updated successfully");
            // Update session name if it was changed
            session.setAttribute("counsellorName", counsellor.getName());
        } else {
            model.addAttribute("emsg", "Failed to update profile. Please check your current password.");
        }
        
        return "edit-profile";
    }
}
