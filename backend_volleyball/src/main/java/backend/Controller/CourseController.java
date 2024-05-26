package backend.Controller;

import backend.Model.Course;
import backend.Model.Section;
import backend.Service.CourseService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins="*")
@RestController
public class CourseController {

    @RequestMapping("/course/chapter/{chapterId}")
    public List<Course> getCoursesByChapterId(@PathVariable int chapterId){
        return CourseService.getCoursesByChapterId(chapterId);
    }

    @RequestMapping("/chapter/section/{sectionId}")
    public List<Integer> getChapitreBySection(@PathVariable int sectionId){
        return CourseService.getChapitreBySection(sectionId);
    }

    @RequestMapping("/sections")
    public ArrayList<Section> getAllSections(){
        return CourseService.getAllSections();
    }
}
