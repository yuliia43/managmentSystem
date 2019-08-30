package com.training.Trucking.controller;

import com.training.Trucking.dto.OrderDTO;
import com.training.Trucking.entity.Order;
import com.training.Trucking.entity.Route;
import com.training.Trucking.entity.User;
import com.training.Trucking.entity.enumeration.CargoType;
import com.training.Trucking.service.OrderService;
import com.training.Trucking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserAccountController {

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @GetMapping(value = "/user/personal-cabinet")
    public String getUserPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName()).orElseThrow(RuntimeException::new);
        model.addAttribute("user", user);
        model.addAttribute("orders", orderService.getAllOrdersOfUser(user).getOrdersList());
        return "user-cabinet.html";
    }

    @GetMapping(value = "/user/place-order")
    public String getOrderPage(Model model, OrderDTO orderDTO) {
        ArrayList<Route> routes = new ArrayList<Route>(orderService.getAllRoutes());

        Set<String> cities = routes.stream()
                .map(Route::getStartCity)
                .collect(Collectors.toSet());

        cities.addAll(routes.stream().map(Route::getEndCity).collect(Collectors.toSet()));

        model.addAttribute("cities", new ArrayList<>(cities));
        model.addAttribute("cargoTypes", CargoType.values());
        return "user-order.html";
    }

    @PostMapping(value = "/user/place-order")
    public String placeOrder(Model model, @Valid OrderDTO orderDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/user/place-order";
        }
        orderService.saveOrder(orderDTO);
        ;
        return "redirect:/user/personal-cabinet";
    }

    @GetMapping(value = "/user/personal-cabinet/pay")
    public String rejectOrder(@RequestParam("id") long id) {
        Order order = orderService.findOrderById(id)
                .orElseThrow(RuntimeException::new);
        order.setPaid(true);
        orderService.saveOrder(order);
        return "redirect:/user/personal-cabinet";
    }

}
