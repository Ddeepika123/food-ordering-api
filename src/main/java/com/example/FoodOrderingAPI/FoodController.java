package com.example.FoodOrderingAPI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/food")
public class FoodController {


        private final Map<Integer, MenuItem> menu = new LinkedHashMap<>();
        private final List<MenuItem> currentOrder = new ArrayList<>();

        public FoodController() {
            menu.put(1, new MenuItem(1, "Margherita Pizza", 250.0));
            menu.put(2, new MenuItem(2, "Veg Burger", 120.0));
            menu.put(3, new MenuItem(3, "French Fries", 90.0));
            menu.put(4, new MenuItem(4, "Pasta Alfredo", 220.0));
            menu.put(5, new MenuItem(5, "Cold Coffee", 110.0));
        }

        // GET /food/menu
        @GetMapping("/menu")
        public List<MenuItem> getMenu() {
            return new ArrayList<>(menu.values());
        }

        // POST /food/order/{itemID}
        @PostMapping("/order/{itemID}")
        public ResponseEntity<String> addToOrder(@PathVariable int itemID) {
            MenuItem item = menu.get(itemID);
            if (item == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Item with ID " + itemID + " not found in the menu");
            }
            currentOrder.add(item);
            return ResponseEntity.ok(item.getName() + " added to the current order");
        }

        // GET /food/order
        @GetMapping("/order")
        public List<MenuItem> getCurrentOrder() {
            return currentOrder;
        }

        // GET /food/order/total
        @GetMapping("/order/total")
        public double getTotalBill() {
            return currentOrder.stream()
                    .mapToDouble(MenuItem::getPrice)
                    .sum();
        }

}
