package am.itspace.projectscope.service;

import am.itspace.projectscope.model.Log;
import am.itspace.projectscope.model.Project;
import am.itspace.projectscope.model.User;
import am.itspace.projectscope.repository.LogRepository;
import am.itspace.projectscope.repository.ProjectRepository;
import am.itspace.projectscope.security.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LogService {
    private final SecurityService securityService;
    private final ProjectRepository projectRepository;
    private final LogRepository logRepository;
    public LogService(SecurityService securityService, ProjectRepository projectRepository, LogRepository logRepository) {
        this.securityService = securityService;
        this.projectRepository = projectRepository;
        this.logRepository = logRepository;
    }


    public Log saveLog(double hour, int id) {
        User user = securityService.fetchCurrentUser();
        List<Project> projectsList = projectRepository.findAllByUserId(user.getId());
        Log log = new Log();
        log.setId(user.getId());
        log.setProject(projectsList.get(id));
        log.setHours(hour);
        return logRepository.save(log);

    }
    public Log getLogById(Long id) {
        Log one = logRepository.getOne(id);
        return one;

    }

    public void deleteLog(Log log) {
        logRepository.delete(log);
    }


}
