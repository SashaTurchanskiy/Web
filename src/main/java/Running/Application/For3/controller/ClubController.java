package Running.Application.For3.controller;

import Running.Application.For3.Exception.ClubNotFoundException;
import Running.Application.For3.dto.ClubDto;
import Running.Application.For3.models.Club;
import Running.Application.For3.service.ClubService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ClubController {

    private ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("/clubs")
    public String listClubs(Model model){
    List<ClubDto> clubs = clubService.findAllClubs();
    model.addAttribute("clubs", clubs);
    return "clubs-list";
    }

    @GetMapping("/clubs/{clubId}")
    public String clubDetail(@PathVariable("clubId") long clubId, Model model){
        ClubDto clubDto = clubService.findClubById(clubId);
        model.addAttribute("club", clubDto);
        return "club-detail";
    }

    @GetMapping("/clubs/new")
    public String createClubFrom(Model model){
        Club club = new Club();
        model.addAttribute("club", club);
        return "clubs-create";
    }

    @GetMapping("/clubs/{clubId}/delete")
    public String deleteClub(@PathVariable("clubId")Long clubId){
        clubService.delete(clubId);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/search")
    public String searchClub(@RequestParam(value = "query") String query, Model model){
        List<ClubDto> clubs = clubService.searchClubs(query);
        model.addAttribute("clubs", clubs);
        return "clubs-list";
    }
    @ControllerAdvice
    public class GlobalControllerExceptionHandler {
        @ExceptionHandler(ClubNotFoundException.class)
        public ModelAndView handleClubNotFoundException(HttpServletRequest request, ClubNotFoundException ex) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("message", ex.getMessage());
            modelAndView.setViewName("errorPage"); // Вкажіть назву свого шаблону для сторінки помилки
            return modelAndView;
        }
    }


    @PostMapping("/clubs/new")
    public String saveClub(@Valid @ModelAttribute("club") ClubDto clubDto, BindingResult result, Model model){
        if (result.hasErrors()) {
            model.addAttribute("club", clubDto);
            return "clubs-create";
        }
        clubService.saveClub(clubDto);
        return "redirect:/clubs";
    }

    @GetMapping("/clubs/{clubId}/edit")
    public String editClubForm(@PathVariable("clubId") long clubId, Model model){
        ClubDto club = clubService.findClubById(clubId);
        model.addAttribute("club", club);
        return "club-edit";
    }
    @PostMapping("/clubs/{clubId}/edit")
    public String updateClub(@PathVariable("clubId") Long clubId,
                             @Valid @ModelAttribute("club") ClubDto club,
                             BindingResult result, Model model){
        if (result.hasErrors()){
            model.addAttribute("club", club);
            return "club-edit";
        }
        club.setId(clubId);
        clubService.updateClub(club);
        return "redirect:/clubs";
    }
}
