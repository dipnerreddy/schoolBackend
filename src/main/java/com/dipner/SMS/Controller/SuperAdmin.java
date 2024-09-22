package com.dipner.SMS.Controller;

import com.dipner.SMS.DTO.ClassBalanceDTO;
import com.dipner.SMS.DTO.UpdateStudentStatusDTO;
import com.dipner.SMS.Entity.*;
import com.dipner.SMS.Repository.ParentRepository;
import com.dipner.SMS.Repository.SchoolClassRepository;
import com.dipner.SMS.Repository.StudentRepository;
import com.dipner.SMS.Repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/superAdmin")
public class SuperAdmin {

    @Autowired
    private SchoolClassRepository schoolClassRepository;
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private StudentRepository studentRepository;

    private final User superUser = new User("admin", "admin");

    @PostMapping("/superLogin")
    public String login(@RequestBody User user) {
        if (isValidUser(user)) {
            return "Login successful!";
        }
        return "Invalid credentials!";
    }
    private boolean isValidUser(User user) {
        return
                (user.getUsername().equals(superUser.getUsername()) &&
                        user.getPassword().equals(superUser.getPassword()));
    }

    // Endpoint to set class fees
    @PostMapping("/setClassFee")
    public ResponseEntity<String> setClassFee(@RequestParam String className, @RequestParam double fee) {
        SchoolClass schoolClass = schoolClassRepository.findByClassName(className);
        if (schoolClass == null) {
            return ResponseEntity.badRequest().body("Class Not Found");
        }

        schoolClass.setFees(fee);
        schoolClass.setTotalPendingFee(schoolClass.getTotalStrength() * fee);
        schoolClassRepository.save(schoolClass);

        return ResponseEntity.ok("Class Fee Updated Successfully");
    }



    @GetMapping("/classBalances")
    public ResponseEntity<List<ClassBalanceDTO>> getClassBalances() {
        List<SchoolClass> classes = schoolClassRepository.findAll();
        List<ClassBalanceDTO> classBalances = new ArrayList<>();

        for (SchoolClass schoolClass : classes) {
            double remainingBalance = schoolClass.getTotalPendingFee(); // Assuming totalPendingFee is the remaining balance
            classBalances.add(new ClassBalanceDTO(schoolClass.getClassName(), remainingBalance));
        }

        return ResponseEntity.ok(classBalances);
    }

    // POST method to add a worker
    @PostMapping("/addWorker")
    public ResponseEntity<String> addWorker(@RequestBody Worker worker) {
        // Calculate the age based on the worker's date of birth
        LocalDate dob = worker.getDob();
        if (dob != null) {
            int age = Period.between(dob, LocalDate.now()).getYears();
            worker.setAge(age); // Set the calculated age
        } else {
            worker.setAge(0); // Default to 0 if DOB is not provided
        }

        // Check for existing worker with the same workerNumber (mobile number)
        if (workerRepository.findByWorkerNumber(worker.getWorkerNumber()) != null) {
            return ResponseEntity.badRequest().body("Worker with the same mobile number already exists.");
        }

        // Save the worker
        workerRepository.save(worker);

        return ResponseEntity.ok("Worker added successfully.");
    }

    // GET method to retrieve worker details by name and mobile number
    @GetMapping("/worker")
    public ResponseEntity<Worker> getWorker(@RequestParam String name, @RequestParam String mobileNumber) {
        Worker worker = workerRepository.findByWorkerNameAndWorkerNumber(name, mobileNumber);
        if (worker == null) {
            return ResponseEntity.badRequest().body(null); // or a suitable response
        }

        return ResponseEntity.ok(worker);
    }

    @PostMapping("/updateStudentStatus")
    public ResponseEntity<String> updateStudentStatus(@RequestBody UpdateStudentStatusDTO updateStatusDTO) {
        // Retrieve the parent using the provided phone number
        Parent parent = parentRepository.findByPhoneNumber(updateStatusDTO.getPhoneNumber());
        if (parent == null) {
            return ResponseEntity.badRequest().body("Parent Not Found.");
        }

        // Find the student associated with the parent using the provided student name
        List<Student> students = parent.getChildren().stream()
                .filter(student -> student.getStudentName().equals(updateStatusDTO.getStudentName()))
                .toList();

        if (students.isEmpty()) {
            return ResponseEntity.badRequest().body("Student Not Found.");
        }

        // Update the stillStudying status for the first matched student
        Student student = students.get(0);
        student.setStillStudying(updateStatusDTO.isStillStudying());
        studentRepository.save(student); // Save the updated student

        return ResponseEntity.ok("Student status updated successfully.");
    }


}
