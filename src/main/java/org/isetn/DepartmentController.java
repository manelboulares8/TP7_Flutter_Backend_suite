// DepartmentController.java
package org.isetn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin("*")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ClasseRepository classeRepository;

    // Get all departments
    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Get department by ID
    @GetMapping("/{id}")
    public Department getDepartmentById(@PathVariable Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
    }

    // Create new department
    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        return departmentRepository.save(department);
    }

    // Update department
    @PutMapping("/{id}")
    public Department updateDepartment(@PathVariable Long id, @RequestBody Department departmentDetails) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        department.setNomDept(departmentDetails.getNomDept());
        
        return departmentRepository.save(department);
    }

    // Delete department
    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        
        // Check if department has classes
        List<Classe> classes = classeRepository.findByDepartmentCodDept(id);
        if (!classes.isEmpty()) {
            throw new RuntimeException("Cannot delete department with existing classes");
        }
        
        departmentRepository.delete(department);
    }

    // Get classes by department
    @GetMapping("/{id}/classes")
    public List<Classe> getClassesByDepartment(@PathVariable Long id) {
        return classeRepository.findByDepartmentCodDept(id);
    }
}