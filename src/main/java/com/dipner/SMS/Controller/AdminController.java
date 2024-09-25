package com.dipner.SMS.Controller;

import com.dipner.SMS.DOA.CollectFeeDOA;
import com.dipner.SMS.DTO.*;
import com.dipner.SMS.Entity.*;
import com.dipner.SMS.Logs.CollectBusFeeLog;
import com.dipner.SMS.Logs.CollectFeeLog;
import com.dipner.SMS.Repository.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    static Log log = LogFactory.getLog(Student.class.getName());
    @Autowired
    private BusDetailsRepository busDetailsRepository;
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
    private BusStudentRepository busStudentRepository;

    @Autowired
    private  CollectBusFeeLogRepository collectBusFeeLogRepository;

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
    public ResponseEntity<String> addStudentWithParent(@RequestBody StudentParentDTO studentParentDTO) {
        // Check if the parent already exists by phone number
        String parentPhoneNumber = studentParentDTO.getParentPhoneNumber();
        Parent parent = parentRepository.findByPhoneNumber(parentPhoneNumber);

        if (parent == null) {
            // Parent does not exist, create a new parent
            parent = new Parent();
            parent.setParentName(studentParentDTO.getParentName());
            parent.setPhoneNumber(parentPhoneNumber);
            parent.setAddress(studentParentDTO.getParentAddress());
            parentRepository.save(parent); // Save the new parent
        }

        // Create and add the student
        Student student = new Student();
        student.setStudentName(studentParentDTO.getStudentName());
        student.setDob(studentParentDTO.getDob());
        student.setAdmissionYear(studentParentDTO.getAdmissionYear());
        student.setCurrentClass(studentParentDTO.getCurrentClass());
        student.setStillStudying(studentParentDTO.isStillStudying());
        student.setGender(studentParentDTO.getGender());

        // Calculate the student's age based on DOB
        LocalDate dob = studentParentDTO.getDob();
        if (dob != null) {
            int age = Period.between(dob, LocalDate.now()).getYears();
            student.setAge(age);
        } else {
            student.setAge(0);
        }

        // Associate the student with the parent
        student.setParent(parent);

        // Find the class by class name and associate it with the student
        SchoolClass schoolClass = schoolClassRepository.findByClassName(studentParentDTO.getCurrentClass());
        if (schoolClass != null) {
            student.setSchoolClass(schoolClass); // Assign school class to student
            student.setFee(schoolClass.getFees());
            schoolClass.setTotalStrength(schoolClass.getTotalStrength() + 1);
            schoolClass.setTotalPendingFee(schoolClass.getTotalPendingFee() + schoolClass.getFees());
            schoolClassRepository.save(schoolClass); // Save updated class
        } else {
            return ResponseEntity.badRequest().body("Class Not Found");
        }

        // Save the student entity
        studentRepository.save(student);

        // If the parent is new, also add the student to the parent's list of children
        if (parent.getChildren() == null) {
            parent.setChildren(new ArrayList<>());
        }
        parent.getChildren().add(student);

        // Save the parent again to ensure the student is linked in the parent's children list
        parentRepository.save(parent);

        // Handle bus student creation if comesByBus is true
        // Handle bus student creation if comesByBus is true
        if (studentParentDTO.isComesByBus()) {
            BusDetails busDetails = busDetailsRepository.findByBusNumber(studentParentDTO.getBusNumber());
            if (busDetails != null) {
                // Create a BusStudent entity
                BusStudent busStudent = new BusStudent();
                busStudent.setStudentName(student.getStudentName());
                busStudent.setMobileNumber(parent.getPhoneNumber());
                busStudent.setRemainingBalance(busDetails.getBusFee()); // Use bus fee as the initial balance
                busStudent.setBusNumber(studentParentDTO.getBusNumber());

                // Save the bus student
                busStudentRepository.save(busStudent);

                // Update the total_pending_fee and total_students in busDetails
                busDetails.setTotalPendingFee(busDetails.getTotalPendingFee() + busDetails.getBusFee());
                busDetails.setTotalStudents(busDetails.getTotalStudents() + 1);
                busDetailsRepository.save(busDetails); // Save updated bus details
            } else {
                return ResponseEntity.badRequest().body("Bus Not Found");
            }
        }


        log.info("Student added successfully: " + student.getStudentName());

        return ResponseEntity.ok("Student and Parent added/linked successfully.");
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

        // Get the current date and time
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Create and save fee collection log
        CollectFeeLog collectFeeLog = new CollectFeeLog(
                student.getStudentName(),
                parent.getPhoneNumber(),
                collectFeeDTO.getAmountPaying(),
                newBalance,
                collectFeeDTO.getPaymentMode(), // Include payment mode in the log
                currentDateTime // Set the current date and time
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


    // POST method to add BusDetails with ResponseEntity.ok
    @PostMapping("/addBusDetails")
    public ResponseEntity<BusDetails> addBusDetails(@RequestBody BusDetails busDetails) {
        BusDetails savedBusDetails = busDetailsRepository.save(busDetails);
        return ResponseEntity.ok(savedBusDetails); // Returning ResponseEntity.ok
    }


    @PostMapping("/checkBusFee")
    public ResponseEntity<?> checkBusFee(@RequestBody CheckBusFeeDTO checkBusFeeDTO) {
        BusStudent busStudent = busStudentRepository.findByStudentNameAndMobileNumber(
                checkBusFeeDTO.getStudentName(),
                checkBusFeeDTO.getMobileNumber()
        );

        if (busStudent == null) {
            // Return a 200 status with a message instead of bad request
            return ResponseEntity.ok(Collections.singletonMap("message", "Bus Student Not Found"));
        }

        double remainingBalance = busStudent.getRemainingBalance();
        return ResponseEntity.ok(Collections.singletonMap("remainingBalance", remainingBalance));
    }


    @PostMapping("/collectBusFee")
    public ResponseEntity<?> collectBusFee(@RequestBody CollectBusFeeDTO collectBusFeeDTO) {
        // Find the BusStudent by student name and mobile number
        BusStudent busStudent = busStudentRepository.findByStudentNameAndMobileNumber(
                collectBusFeeDTO.getStudentName(),
                collectBusFeeDTO.getMobileNumber()
        );

        if (busStudent == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Bus Student Not Found"));
        }

        // Get remaining balance and check if the amount to be paid is valid
        double remainingBalance = busStudent.getRemainingBalance();
        if (collectBusFeeDTO.getAmountPaying() > remainingBalance) {
            return ResponseEntity.badRequest().body(Map.of("message", "Amount exceeds the remaining balance"));
        }

        // Calculate new balance and update BusStudent entity
        double newBalance = remainingBalance - collectBusFeeDTO.getAmountPaying();
        busStudent.setRemainingBalance(newBalance);
        busStudentRepository.save(busStudent); // Save updated BusStudent

        // Find the corresponding BusDetails entity using the bus number
        BusDetails busDetails = busDetailsRepository.findByBusNumber(busStudent.getBusNumber());
        if (busDetails == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Bus details not found for the student"));
        }

        // Update the total pending fee in BusDetails
        double updatedTotalPendingFee = busDetails.getTotalPendingFee() - collectBusFeeDTO.getAmountPaying();
        busDetails.setTotalPendingFee(updatedTotalPendingFee);
        busDetailsRepository.save(busDetails); // Save updated BusDetails

        // Create and save bus fee collection log
        CollectBusFeeLog busFeeLog = new CollectBusFeeLog(
                busStudent.getStudentName(),
                busStudent.getMobileNumber(),
                collectBusFeeDTO.getAmountPaying(),
                newBalance,
                collectBusFeeDTO.getPaymentMode(), // Now this works
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) // Example for dateTime
        );
        collectBusFeeLogRepository.save(busFeeLog); // Save log

        // Return the updated balance along with a success message in JSON format
        return ResponseEntity.ok(Map.of("message", "Payment was successful", "remainingBalance", newBalance));
    }





    @GetMapping("/busBalances")
    public ResponseEntity<List<BusBalanceDTO>> getBusBalances() {
        List<BusDetails> buses = busDetailsRepository.findAll();
        List<BusBalanceDTO> busBalances = new ArrayList<>();

        for (BusDetails busDetails : buses) {
            double remainingBalance = busDetails.getTotalPendingFee(); // Assuming totalPendingFee is the remaining balance
            busBalances.add(new BusBalanceDTO(busDetails.getBusNumber(), remainingBalance));
        }

        return ResponseEntity.ok(busBalances);
    }


    @GetMapping("/studentsByBus")
    public ResponseEntity<?> getStudentsByBus(@RequestParam String busNumber) {
        // Find the bus by busNumber
        BusDetails busDetails = busDetailsRepository.findByBusNumber(busNumber);
        if (busDetails == null) {
            return ResponseEntity.badRequest().body("Bus Not Found");
        }

        // Get all students associated with the bus
        List<BusStudent> busStudents = busStudentRepository.findByBusNumber(busNumber);

        // If no students found, return an empty list
        if (busStudents.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        // Prepare the list of DTOs to return
        List<StudentBusFeeInfoDTO> studentBusFeeInfoList = busStudents.stream()
                .map(busStudent -> {
                    return new StudentBusFeeInfoDTO(
                            busStudent.getStudentName(),
                            busStudent.getMobileNumber(),  // Get student's phone number
                            busStudent.getRemainingBalance() // Remaining bus balance
                    );
                }).toList();

        return ResponseEntity.ok(studentBusFeeInfoList);
    }


}


/*
more updates in future,

1) in collect fee, add method of payment, [upi,cash, card] [done]
2) change to parent number from parent id while adding a student. [sloved]
3) in super admin, add class in ui [ temperoroly adjested using postman in school system. ]
4)
 */