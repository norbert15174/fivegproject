package pl.projectfiveg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.projectfiveg.DTO.OrderJobDTO;
import pl.projectfiveg.DTO.TaskDTO;
import pl.projectfiveg.services.interfaces.ITaskService;
import pl.projectfiveg.specification.criteria.TaskSearchCriteria;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin("*")
public class TaskController {
    private final ITaskService taskService;

    @Autowired
    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity <TaskDTO> create(Principal principal , @RequestBody OrderJobDTO job) {
        return taskService.create(principal , job);
    }

    @GetMapping
    public ResponseEntity <List <TaskDTO>> getTasks(Principal principal , TaskSearchCriteria taskSearchCriteria) {
        return taskService.getTasks(principal , taskSearchCriteria);
    }

    @GetMapping("/{taskId}/download")
    public ResponseEntity <Resource> downloadFile(Principal principal , @PathVariable("taskId") Long taskId) {
        return taskService.getFile(principal , taskId);
    }
}
