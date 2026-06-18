package abc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAPI {

	@Autowired
	Service service;
	
	@PostMapping("task")
	public Task createLongRunningTask(@RequestBody TaskDefinition taskDef) {
		return this.service.createLongRunningTask(taskDef);
	}
	
}

@Component
class Service{
	public Task createLongRunningTask(TaskDefinition taskDef) {
		
	}
}

class TaskDefinition {
	
}

class Task {
	
	
}
