package am.itspace.projectscope.rest;

import am.itspace.projectscope.model.Log;
import am.itspace.projectscope.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class LogEndpoint {

    private final LogService logService;

    public LogEndpoint(LogService logService) {
        this.logService = logService;
    }

    @PostMapping("/addLog")
    public Log crateLog(@RequestParam double hour,
                        @RequestParam int id) {
        return logService.saveLog( hour, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Log> deleteLog(@PathVariable Long id) {
        Log log = logService.getLogById(id);
        logService.deleteLog(log);
        return ResponseEntity.ok().build();
    }
}
