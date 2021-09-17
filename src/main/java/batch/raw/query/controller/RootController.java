package batch.raw.query.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@EnableScheduling
public class RootController {

    private static final Logger log = LoggerFactory.getLogger(RootController.class);


    @GetMapping(path = "launch")
    public ResponseEntity<String> importAll() {
       BatchController.runBatch();
       return ResponseEntity.ok("Finished Batch ! Check logs !");
    }

}

