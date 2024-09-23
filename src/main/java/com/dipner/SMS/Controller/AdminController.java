package com.dipner.SMS.Controller;

import com.dipner.SMS.DOA.CollectFeeDOA;
import com.dipner.SMS.DTO.*;
import com.dipner.SMS.Entity.*;
import com.dipner.SMS.Logs.CollectFeeLog;
import com.dipner.SMS.Repository.*;
import jakarta.transaction.Transactional;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    static Log log = LogFactory.getLog(Student.class.getName());
    @Autowired
    private CollectFeeLogRepository collectFeeLogRepository;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @Autowired
    private CollectFeeDOA collectFeeDOA;

    @Autowired
    private BusDetailsRepository busDetailsRepository;
    @Autowired
    private BusStudentRepository busStudentRepository;

    private final User radiantUser = new User("radiantUser", "radiant123");

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User userLogin) {
        // Validate username and password (this is just a placeholder logic)
        if ("user".equals(userLogin.getUsername()) && "1234".equals(userLogin.getPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }



    @PostMapping("/addStudent")
    @Transactional
    public ResponseEntity<String> addStudentWithParent(@RequestBody StudentParentDTO studentParentDTO) {
        try {
            // Check if the parent already exists by phone number
            String parentPhoneNumber = studentParentDTO.getParentPhoneNumber();
            Parent parent = parentRepository.findByPhoneNumber(parentPhoneNumber);

            if (parent == null) {
                // Create a new parent if not found
                parent = new Parent();
                parent.setParentName(studentParentDTO.getParentName());
                parent.setPhoneNumber(parentPhoneNumber);
                parent.setAddress(studentParentDTO.getParentAddress());
                parentRepository.save(parent);
            }

            // Create and add the student
            Student student = new Student();
            student.setStudentName(studentParentDTO.getStudentName());
            student.setDob(studentParentDTO.getDob());
            student.setAdmissionYear(studentParentDTO.getAdmissionYear());
            student.setCurrentClass(studentParentDTO.getCurrentClass());
            student.setStillStudying(studentParentDTO.isStillStudying());
            student.setGender(studentParentDTO.getGender());

            // Calculate and set the student's age based on DOB
            LocalDate dob = studentParentDTO.getDob();
            if (dob != null) {
                student.setAge(Period.between(dob, LocalDate.now()).getYears());
            } else {
                student.setAge(0);
            }

            // Associate the student with the parent
            student.setParent(parent);

            // Find the class by class name and associate it with the student
            SchoolClass schoolClass = schoolClassRepository.findByClassName(studentParentDTO.getCurrentClass());
            if (schoolClass == null) {
                return ResponseEntity.badRequest().body("Class Not Found");
            }

            student.setSchoolClass(schoolClass);
            student.setFee(schoolClass.getFees());

            // Update school class details
            schoolClass.setTotalStrength(schoolClass.getTotalStrength() + 1);
            schoolClass.setTotalPendingFee(schoolClass.getTotalPendingFee() + schoolClass.getFees());
            schoolClassRepository.save(schoolClass);

            // Save the student before processing bus details
            studentRepository.save(student);

            // Handle bus-related logic if the student uses the bus
            if (studentParentDTO.isUsesBus()) {
                String busNumber = studentParentDTO.getBusNumber();

                // Validate bus number
                BusDetails busDetails = busDetailsRepository.findByBusNumber(busNumber);
                if (busDetails == null) {
                    return ResponseEntity.badRequest().body("Bus number not matched. Please provide a valid bus number.");
                }

                // Create BusStudent association
                BusStudent busStudent = new BusStudent();
                busStudent.setBusNumber(busNumber);
                busStudent.setStudentName(student.getStudentName());
                busStudent.setMobileNumber(parent.getPhoneNumber());
                busStudent.setBalanceFee(student.getFee());
                busStudentRepository.save(busStudent);

                // Update bus details
                busDetails.setTotalStudents(busDetails.getTotalStudents() + 1);
                busDetails.setTotalPendingFee(busDetails.getTotalPendingFee() + student.getFee());
                busDetailsRepository.save(busDetails);

                student.setUsesBus(true);
            } else {
                student.setUsesBus(false);
            }

            // Add the student to the parent's list of children if not already added
            if (parent.getChildren() == null) {
                parent.setChildren(new ArrayList<>());
            }
            parent.getChildren().add(student);

            // Save parent after associating the student
            parentRepository.save(parent);

            log.info("Student added successfully: " + student.getStudentName());
            return ResponseEntity.ok("Student and Parent added/linked successfully with bus details.");

        } catch (Exception e) {
            log.error("Error adding student: " + e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the student.");
        }
    }


    // Endpoint to create a class
    @PostMapping("/createClass")
    public ResponseEntity<String> createClass(@RequestBody SchoolClassDTO schoolClassDTO) {
        SchoolClass existingClass = schoolClassRepository.findByClassName(schoolClassDTO.getClassName());
        if (existingClass != null) {
            return ResponseEntity.badRequest().body("Class Already Exists");
        }

        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setClassName(schoolClassDTO.getClassName());
        schoolClass.setFees(schoolClassDTO.getFees());
        schoolClass.setTotalStrength(schoolClassDTO.getTotalStrength());
        schoolClass.setTotalPendingFee(schoolClassDTO.getTotalPendingFee());

        schoolClassRepository.save(schoolClass);
        log.info("Class created successfully: " + schoolClassDTO.getClassName());

        return ResponseEntity.ok("Class Created Successfully");
    }

    // Endpoint to check balance fee for a student
    @PostMapping("/checkBalanceFee")
    public ResponseEntity<?> checkBalanceFee(@RequestBody CheckBalanceFeeDTO checkBalanceFeeDTO) {
        Parent parent = parentRepository.findByPhoneNumber(checkBalanceFeeDTO.getPhoneNumber());
        if (parent == null) {
            return ResponseEntity.badRequest().body("Parent Not Found");
        }

        List<Student> students = parent.getChildren().stream()
                .filter(student -> student.getStudentName().equals(checkBalanceFeeDTO.getStudentName()) &&
                        student.getCurrentClass().equals(checkBalanceFeeDTO.getCurrentClass()))
                .toList();

        if (students.isEmpty()) {
            return ResponseEntity.badRequest().body("Student Not Found in the given class");
        }

        Student student = students.get(0);
        double remainingBalance = student.getFee();

        return ResponseEntity.ok(Collections.singletonMap("remainingBalance", remainingBalance));
    }

    @PostMapping("/collectFee")
    public ResponseEntity<?> collectFee(@RequestBody CollectFeeDTO collectFeeDTO) {
        Parent parent = parentRepository.findByPhoneNumber(collectFeeDTO.getPhoneNumber());
        if (parent == null) {
            return ResponseEntity.badRequest().body("Parent Not Found");
        }

        List<Student> students = parent.getChildren().stream()
                .filter(student -> student.getStudentName().equals(collectFeeDTO.getStudentName()) &&
                        student.getCurrentClass().equals(collectFeeDTO.getCurrentClass()))
                .toList();

        if (students.isEmpty()) {
            return ResponseEntity.badRequest().body("Student Not Found in the given class");
        }

        Student student = students.get(0);
        double remainingBalance = student.getFee();

        if (collectFeeDTO.getAmountPaying() > remainingBalance) {
            return ResponseEntity.badRequest().body("Amount exceeds the remaining balance");
        }

        double newBalance = remainingBalance - collectFeeDTO.getAmountPaying();
        student.setFee(newBalance);
        studentRepository.save(student); // Save updated student

        // Update class pending fee
        SchoolClass schoolClass = student.getSchoolClass();
        if (schoolClass != null) {
            double updatedTotalPendingFee = schoolClass.getTotalPendingFee() - collectFeeDTO.getAmountPaying();
            schoolClass.setTotalPendingFee(updatedTotalPendingFee);
            schoolClassRepository.save(schoolClass); // Save updated class
        } else {
            return ResponseEntity.badRequest().body("Class Not Found for the Student");
        }

        // Create and save fee collection log
        CollectFeeLog collectFeeLog = new CollectFeeLog(
                student.getStudentName(),
                parent.getPhoneNumber(),
                collectFeeDTO.getAmountPaying(),
                newBalance,
                collectFeeDTO.getPaymentMode() // Include payment mode in the log
        );
        collectFeeLogRepository.save(collectFeeLog); // Save log

        // Return a JSON response with the new balance and payment mode
        return ResponseEntity.ok(Map.of("remainingBalance", newBalance, "paymentMode", collectFeeDTO.getPaymentMode()));
    }



    @PostMapping("/sendWhatsAppMessage")
    public ResponseEntity<?> sendWhatsAppMessage(@RequestBody WhatsAppMessageDTO messageDTO) {
        // Here, you will invoke the Puppeteer script
        // You can run it as a separate process or integrate it directly
        // This is a placeholder for your Puppeteer implementation
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("node", "/Users/dipnerreddy/Desktop/Projects/schoolSoftware/school-software-frontend/src/sendMessage.js", messageDTO.getPhoneNumber(), messageDTO.getMessage());
            Process process = processBuilder.start();
            process.waitFor(); // wait for the process to finish
            return ResponseEntity.ok("Message sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message: " + e.getMessage());
        }
    }



    @GetMapping("/studentsByClass")
    public ResponseEntity<?> getStudentsByClass(@RequestParam String className) {
        // Find the class by className
        SchoolClass schoolClass = schoolClassRepository.findByClassName(className);
        if (schoolClass == null) {
            return ResponseEntity.badRequest().body("Class Not Found");
        }

        // Get all students associated with the class
        List<Student> students = studentRepository.findBySchoolClass(schoolClass);

        // If no students found, return an empty list
        if (students.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        // Prepare the list of DTOs to return
        List<StudentFeeInfoDTO> studentFeeInfoList = students.stream()
                .map(student -> {
                    Parent parent = student.getParent(); // Get parent associated with the student
                    return new StudentFeeInfoDTO(
                            student.getStudentName(),
                            parent.getPhoneNumber(),  // Get parent's phone number
                            student.getFee()          // Remaining balance
                    );
                }).toList();

        return ResponseEntity.ok(studentFeeInfoList);
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

    // Endpoint to get all parents (optional)
    @GetMapping("/parents")
    public List<Parent> getAllParents() {
        return parentRepository.findAll();
    }

    // Endpoint to get all students (optional)
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


    @PostMapping("/addBus")
    public ResponseEntity<String> addBus(@RequestBody BusDetails busDetails) {
        // Check if the bus already exists
        if (busDetailsRepository.findByBusNumber(busDetails.getBusNumber()) != null) {
            return ResponseEntity.badRequest().body("Bus with this number already exists.");
        }

        // Save the new bus details
        busDetailsRepository.save(busDetails);
        return ResponseEntity.ok("Bus details added successfully.");
    }
}


/*
more updates in future,

1) in collect fee, add method of payment, [upi,cash, card] [done]
2) change to parent number from parent id while adding a student. [sloved]
3) in super admin, add class in ui [ temperoroly adjested using postman in school system. ]
4)
 */