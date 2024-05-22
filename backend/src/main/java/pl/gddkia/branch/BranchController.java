package pl.gddkia.branch;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/branch")
public class BranchController {
    private final BranchService branchService;

    @GetMapping
    public List<BranchRest> gelAll() {
        return branchService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(branchService.getById(id));
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> loanRoadsData(@RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(branchService.createRoadsDetails(file.getInputStream()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRoadData(@PathVariable("id") Long id, @RequestBody final UpdateBranchDto updateRequest) {
        return ResponseEntity.ok(branchService.updateBranchDetails(id, updateRequest));
    }

}

