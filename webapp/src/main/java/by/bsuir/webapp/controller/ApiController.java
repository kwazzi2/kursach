package by.bsuir.webapp.controller;

import by.bsuir.webapp.model.tutor.Tutor;
import by.bsuir.webapp.repository.tutor.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class ApiController {
    @Autowired
    TutorRepository tutorRepository;
    @PostMapping("/comparison/add/{tutorId}")
    public ResponseEntity addToCompare(@PathVariable Long tutorId, HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute("comparisonList");
        List<Long> comparisonList = new ArrayList<>();
        if(attribute != null)
            comparisonList = (List<Long>) attribute;
        if(comparisonList.contains(tutorId))
            return ResponseEntity.badRequest().build();

        comparisonList.add(tutorId);
        request.getSession().setAttribute("comparisonList", comparisonList);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/chart/values")
    public ResponseEntity getChartValues() {
        List<Tutor> tutorList = tutorRepository.findAll(TutorRepository.Specs.noHidden());
        List<Double> ratings = tutorList.stream().map(tutor -> round(tutor.getRating(), 2)).toList();
        Map<Double, Long> ratingByCount = tutorList.stream().map(tutor -> round(tutor.getRating(), 2))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        List<Value> values = ratingByCount.entrySet().stream().map(e -> new Value(e.getKey(), e.getValue())).toList();
        return ResponseEntity.ok(values);
    }

    private Double round(Double i, int scale) {
        double s = Math.pow(10, scale);
        return Math.ceil(i * s) / s;
    }
    record Value(Double x, Long y) { }
}
