package am.itspace.projectscope.repository;

import am.itspace.projectscope.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    List<Project> findAllByUserId(Long id);
    List<Project> findAllByNameContainingIgnoreCase(String name);


}
