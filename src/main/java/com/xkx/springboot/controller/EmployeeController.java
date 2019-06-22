package com.xkx.springboot.controller;

import com.xkx.springboot.dao.DepartmentDao;
import com.xkx.springboot.dao.EmployeeDao;
import com.xkx.springboot.entities.Department;
import com.xkx.springboot.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
public class EmployeeController {

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    DepartmentDao departmentDao;

    //查询所有员工，返回列表页面
    @GetMapping(value = "/emps")
    public String list(Model model) {
        Collection<Employee> employees = employeeDao.getAll();
        model.addAttribute("emps", employees);
        //thymeleaf会默认拼串
        //classpath:/templates/xxx.html.
        return "emp/list";
    }

    /**
     * 来到员工添加页面
     *
     * @return
     */
    @GetMapping("/emp")
    public String toAddPage(Model model) {

        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);
        return "emp/add";
    }

    @PostMapping("/emp")
    public String addEmp(Employee employee) {
        System.out.println("保存的员工信息" + employee);
        employeeDao.save(employee);
        //来到员工列表页面
        return "redirect:/emps";
    }

    /**
     * 来到员工编辑页面，查出当前员工，在页面回显
     *
     * @return
     */
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id, Model model) {
        Employee employee = employeeDao.get(id);
        model.addAttribute("emp", employee);

        //查出所有部门
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("depts", departments);

        return "emp/add";
    }

    /**
     * 员工修改
     *
     * @param employee
     * @return
     */
    @PutMapping("/emp")
    public String updateEmployee(Employee employee) {

        employeeDao.save(employee);
        return "redirect:/emps";
    }

    /**
     * 员工删除
     */
    @DeleteMapping("/emp/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        employeeDao.delete(id);
        return "redirect:/emps";
    }

}
