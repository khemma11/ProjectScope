package am.itspace.projectscope.rest;

import am.itspace.projectscope.dto.ProjectDto;
import am.itspace.projectscope.model.Project;
import am.itspace.projectscope.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@Slf4j
public class ProjectEndpoint {

    private final ProjectService projectService;

    public ProjectEndpoint(ProjectService projectService) {
        this.projectService = projectService;
    }


    @PostMapping("/add")
    public Project createProject(@RequestBody ProjectDto project, String deadline, String name) throws ParseException {
        return projectService.createProject(project, deadline,name);
    }

    @GetMapping("/byTeamLeader")
    public List<Project> getProjectsByUserType(@RequestParam String userType) {
        return projectService.getAllByUserType(userType);
    }

    @GetMapping("/search")
    ResponseEntity<?> search(@RequestParam(name = "q") String name) {
        return ResponseEntity.ok( projectService.search(name));
    }


    @GetMapping("/UserId/{userId}")
    public List<Project> getProjectsByUserId(@PathVariable Long userId) {
        return projectService.getProjectsByUserId(userId);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Project> deleteProject(@PathVariable Long id) {
        Project projectById = projectService.getProjectById(id);
        projectService.deleteProject(projectById);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }


}
