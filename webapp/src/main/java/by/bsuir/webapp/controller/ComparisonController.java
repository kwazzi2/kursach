package by.bsuir.webapp.controller;

import by.bsuir.webapp.exception.NotFoundEntityException;
import by.bsuir.webapp.model.tutor.Tutor;
import by.bsuir.webapp.repository.tutor.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ComparisonController {
    @Autowired
    TutorRepository tutorRepository;

    @GetMapping("/comparison")
    public String getComparison(HttpServletRequest request, Model model) throws NotFoundEntityException {
        Object attribute = request.getSession().getAttribute("comparisonList");
        List<Long> comparisonList = new ArrayList<>();
        if(attribute != null)
            comparisonList = (List<Long>) attribute;

        List<Tutor> tutors = new ArrayList<>();
        for(Long id : comparisonList) {
            Tutor tutor = tutorRepository.findByIdAndHidden(id, false).orElseThrow(NotFoundEntityException::new);
            tutors.add(tutor);
        }
        model.addAttribute("tutors", tutors);
        return "comparison";
    }

    @PostMapping("/comparison/delete/{tutorId}")
    public String addToCompare(@PathVariable Long tutorId, HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute("comparisonList");
        List<Long> comparisonList = new ArrayList<>();
        if(attribute != null)
            comparisonList = (List<Long>) attribute;
        comparisonList.remove(tutorId);
        request.getSession().setAttribute("comparisonList", comparisonList);
        return "redirect:/comparison";
    }
}
