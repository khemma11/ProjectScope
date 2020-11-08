package am.itspace.projectscope.service;

import am.itspace.projectscope.dto.ProjectDto;
import am.itspace.projectscope.model.Project;
import am.itspace.projectscope.model.User;
import am.itspace.projectscope.repository.ProjectRepository;
import am.itspace.projectscope.repository.UserRepository;
import am.itspace.projectscope.security.service.SecurityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final SecurityService securityService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ProjectRepository projectRepository;

    public ProjectService(SecurityService securityService,
                          UserRepository userRepository,
                          ModelMapper modelMapper,
                          ProjectRepository projectRepository) {
        this.securityService = securityService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.projectRepository = projectRepository;
    }

    public Project createProject(ProjectDto project, String deadline, String name) throws ParseException {

        Project newProject = modelMapper.map(project, Project.class);
        User user = securityService.fetchCurrentUser();
        List<User> userList=userRepository.findAllByName(name);
        newProject.setMember(userList);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = dateFormat.parse(deadline);
        newProject.setId(user.getId());
        newProject.setDeadline(endDate);
        return projectRepository.save(newProject);
    }

    public List<Project> getAllByUserType(String userType) {
        List<Project> projects = projectRepository.findAll();
        projects = projects.stream()
                .filter(project -> project.getUser().getUsertype().name().equals(userType))
                .collect(Collectors.toList());
        return projects;
    }

    public List<Project> search(String name) {
            List<Project> allByName = projectRepository.findAllByNameContainingIgnoreCase(name);
            return allByName;
    }


    public List<Project> getProjectsByUserId(Long id) {
        List<Project> allByUserId = projectRepository.findAllByUserId(id);
        return allByUserId;

    }

    public Project getProjectById(Long id) {
       return projectRepository.getOne(id);
    }

    public void deleteProject(Project projectById) {
        projectRepository.delete(projectById);
    }
}
