package com.training.Trucking.controller;

import com.training.Trucking.entity.Order;
import com.training.Trucking.entity.Role;
import com.training.Trucking.entity.User;
import com.training.Trucking.entity.enumeration.OrderStatus;
import com.training.Trucking.service.OrderService;
import com.training.Trucking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/admin/cabinet")
    public String getAdminCabinet(Model model) {

        List<Order> orders = orderService.getAllUsersOrders().getOrdersList();
        model.addAttribute("newOrders", orders.stream()
                .filter(x -> x.getState().equals(OrderStatus.CREATED))
                .collect(Collectors.toList()));

        model.addAttribute("ordersInProcess", orders.stream()
                .filter(x -> x.getState().equals(OrderStatus.PROCESSING))
                .collect(Collectors.toList()));

        model.addAttribute("completedOrders", orders.stream()
                .filter(x -> x.getState().equals(OrderStatus.COMPLETED))
                .collect(Collectors.toList()));

        model.addAttribute("rejectedOrders", orders.stream()
                .filter(x -> x.getState().equals(OrderStatus.REJECTED))
                .collect(Collectors.toList()));

        return "admin-orders.html";
    }

    @GetMapping(value = "/admin/cabinet/confirm")
    public String confirmOrder(@RequestParam("id") long id) {
        Order order = orderService.findOrderById(id)
                .orElseThrow(RuntimeException::new);
        order.setState(OrderStatus.PROCESSING);
        orderService.saveOrder(order);
        return "redirect:/admin/cabinet";
    }

    @GetMapping(value = "/admin/cabinet/complete")
    public String completeOrder(@RequestParam("id") long id) {
        Order order = orderService.findOrderById(id)
                .orElseThrow(RuntimeException::new);
        order.setState(OrderStatus.COMPLETED);
        orderService.saveOrder(order);
        return "redirect:/admin/cabinet";
    }

    @GetMapping(value = "/admin/cabinet/reject")
    public String rejectOrder(@RequestParam("id") long id) {
        Order order = orderService.                                                                                                                                                     findOrderById(id)
                .orElseThrow(RuntimeException::new);
        order.setState(OrderStatus.REJECTED);
        orderService.saveOrder(order);
        return "redirect:/admin/cabinet";
    }

    @GetMapping(value = "/admin/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers().getUsers());
        return "admin-users.html";
    }

    @GetMapping(value = "/admin/users/ban")
    public String userBan(@RequestParam("id") long id) {
        User user = userService.findById(id).orElseThrow(RuntimeException::new);
        if (user.getRoles().stream().anyMatch(x -> x.getName().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/users";
        }
        user.setIsBanned(true);
        userService.saveUser(user);
        return "redirect:/admin/users";
    }


    //TODO param email, not id
    @GetMapping(value = "/admin/users/promote")
    public String userPromote(@RequestParam("id") long id) {
        User user = userService.findById(id).orElseThrow(RuntimeException::new);
        Collection<Role> roles = user.getRoles();
        roles.add(new Role("ROLE_ADMIN"));
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin/users";
    }
}
