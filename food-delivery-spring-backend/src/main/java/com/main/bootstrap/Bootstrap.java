package com.main.bootstrap;

import com.main.entity.*;
import com.main.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        Authority admin = saveAuthority("ADMIN");
        Authority employee = saveAuthority("EMPLOYEE");
        Authority user = saveAuthority("USER");

        User loreana = saveUser("Beatovic", "Loreana", "loreana@hotmail.com", true, "12345", admin);
        User boris = saveUser("Dimitrijevic", "Boris", "boris@hotmail.com", true, "12345", employee);
        User andrijana = saveUser("Molnar", "Andrijana", "andrijana@hotmail.com", true, "12345", user);
        User darko = saveUser("Molnar", "Darko", "darko@hotmail.com", false, "12345", user);

        Category chicken = saveCategory("Chicken");
        Category fruit = saveCategory("Fruit");
        Category iceCream = saveCategory("Ice Cream");
        Category drinks = saveCategory("Drinks");
        Category curryChicken = saveCategory("Curry Chicken");
        Category risoto = saveCategory("Risoto");

        saveProduct("Red Bull",8.59, drinks, "120 Calories", List.of("Sugar, Water, Secret Ingredients"), true ,"d1.png");
        saveProduct("Pepsi",4.59, drinks, "110 Calories", List.of("Sugar, Water, Secret Ingredients"), true ,"d2.png");
        saveProduct("Monster",7.29, drinks, "140 Calories", List.of("Sugar, Water, Secret Ingredients"), true ,"d3.png");
        saveProduct("Blue Lagoon",12.59, drinks, "70 Calories", List.of("Sprite, Blue juice, Alcohol"), true ,"d4.png");
        saveProduct("Sprite",4.59, drinks, "67 Calories", List.of("Sugar, Water, Secret Ingredients"), true ,"d5.png");
        saveProduct("Fanta",4.59, drinks, "67 Calories", List.of("Sugar, Water, Secret Ingredients"), true ,"d6.png");
        saveProduct("Orange Coffe",4.59, drinks, "67 Calories", List.of("Sugar, Water, Secret Ingredients"), true ,"d7.png");
        saveProduct("Coca Cola",4.59, drinks, "67 Calories", List.of("Sugar, Water, Secret Ingredients"), true ,"d8.png");

        saveProduct("Strawberry", 12.09, fruit, "122", List.of("Fresh Fruit"), true, "f1.png");
        saveProduct("Pineapple", 12.09, fruit, "122", List.of("Fresh Pineapple"), true, "f2.png");
        saveProduct("Blackberry", 12.09, fruit, "122", List.of("Fresh Blackberry"), true, "f3.png");
        saveProduct("Grape", 12.09, fruit, "122", List.of("Fresh Grape"), true, "f4.png");
        saveProduct("Sweet Starawberry", 12.09, fruit, "122", List.of("Fresh Strawberry"), true, "f5.png");
        saveProduct("Pomegranate", 12.09, fruit, "122", List.of("Fresh Pomegranate"), true, "f6.png");
        saveProduct("Raspberry", 12.09, fruit, "122", List.of("Fresh Rasberry"), true, "f7.png");
        saveProduct("Cherry", 12.09, fruit, "122", List.of("Fresh Cherry"), true, "f8.png");
        saveProduct("Bannana", 12.09, fruit, "122", List.of("Fresh Banana"), true, "f9.png");
        saveProduct("Watermellon", 12.09, fruit, "122", List.of("Fresh Watermellon"), true, "f10.png");

        saveProduct("Coocked chicken with sauce", 28.59, chicken,"487 Calories", List.of("Chickenn, sauce, potato, peanut"), true, "c1.png");
        saveProduct("Fried hot chicken", 38.59, chicken,"487 Calories", List.of("Chickenn, hot pappers"), true, "c2.png");
        saveProduct("Mixed chicken", 33.59, chicken,"487 Calories", List.of("Chickenn, dry chicken, coock chicken with salat on the side"), true, "c3.png");
        saveProduct("Kentucky Chicken", 24.59, chicken,"487 Calories", List.of("Kentucky chicken with special ingredients"), true, "c4.png");
        saveProduct("Coocked chicken with salad", 59.59, chicken,"487 Calories", List.of("Chickenn, healthy fresh salat"), true, "c6.png");
        saveProduct("Chciken wings with potato", 21.59, chicken,"487 Calories", List.of("Chickenn Wings", "Potato"), true, "c7.png");


        Product product15 = saveProduct("Curry chicken with sauce", 99.59, curryChicken, "783 Calories", List.of("Sauce", "Chicken", "Vegetables"), true, "cu1.png");
        Product product14 = saveProduct("Curry chicken with vegetables 2", 99.59, curryChicken, "783 Calories", List.of("Sauce", "Chicken", "Vegetables"), true, "cu2.png");
        Product product13 = saveProduct("Curry chicken with vegetables 3 ", 99.59, curryChicken, "783 Calories", List.of("Sauce", "Chicken", "Vegetables"), true, "cu3.png");
        Product product12 = saveProduct("Curry chicken with vegetables 4", 99.59, curryChicken, "783 Calories", List.of("Sauce", "Chicken", "Vegetables"), true, "cu4.png");


        Product product11 = saveProduct("Chocolate Vanilla", 8.59, iceCream, "199 Calories", List.of("Chocolate", "Vanila", "Toping chocolate"), true, "i1.png");
        Product product10 = saveProduct(" Vanilla", 8.59, iceCream, "199 Calories", List.of("Chocolate", "Vanila", "Toping chocolate"), true, "i2.png");
        Product product9 = saveProduct("Biscuit IceCream", 8.59, iceCream, "199 Calories", List.of("Chocolate", "Vanila", "Toping chocolate"), true, "i3.png");
        Product product8 = saveProduct("Random Flavor", 8.59, iceCream, "199 Calories", List.of("Chocolate", "Vanila", "Toping chocolate"), true, "i4.png");
        Product product7 = saveProduct("Strawberry", 8.59, iceCream, "199 Calories", List.of("Chocolate", "Vanila", "Toping chocolate"), true, "i5.png");
        Product product6 = saveProduct("Coup One Flavor", 8.59, iceCream, "199 Calories", List.of("Chocolate", "Vanila", "Toping chocolate"), true, "i6.png");
        Product product5 = saveProduct("Straberry Mixed", 8.59, iceCream, "199 Calories", List.of("Chocolate", "Vanila", "Toping chocolate"), true, "i7.png");


        Product product4 = saveProduct("Risoto 1", 81.51, risoto, "399 Calories", List.of("Risoto", "Chicken"), true, "r1.png");
        Product product3 = saveProduct("Risoto 2", 12.99, risoto, "399 Calories", List.of("Risoto", "Chicken"), true, "r2.png");
        Product product2 = saveProduct("Risoto 3", 19.50, risoto, "399 Calories", List.of("Risoto", "Chicken"), true, "r3.png");
        Product product1 = saveProduct("Risoto 4", 13.50, risoto, "399 Calories", List.of("Risoto", "Chicken"), true, "r4.png");
        Product product = saveProduct("Risoto 5", 18.50, risoto, "399 Calories", List.of("Risoto", "Chicken"), true, "r5.png");

        saveOrder(andrijana, List.of(product1, product2, product3), LocalDateTime.now().minusWeeks(2), "DELIVERED", true);
        saveOrder(darko, List.of(product1, product2, product3), LocalDateTime.now().plusHours(8), "CANCELED", false);
        saveOrder(andrijana, List.of(product12, product7, product1), LocalDateTime.now().minusDays(5), "DELIVERED", true);
        saveOrder(andrijana, List.of(product1, product2, product3), LocalDateTime.now().minusDays(2), "DELIVERED", true);

        saveOrder(andrijana, List.of(product1, product2, product3), LocalDateTime.now(), "RETURNED", true);
        saveOrder(andrijana, List.of(product1, product2, product3), LocalDateTime.now(), "RETURNED", false);
        saveOrder(andrijana, List.of(product1, product2, product3), LocalDateTime.now(), "RETURNED", false);


    }



    public void saveOrder(User user, List<Product> products, LocalDateTime time, String status, boolean isPaid){
        List<OrderItem> orderItems = new ArrayList<>();
        Order order = new Order();
        products.forEach(product -> {
            int quantity = new Random().nextInt(5);
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .order(order)
                    .quantity(quantity)
                    .created(LocalDateTime.now())
                    .totalPrice(product.getPrice() * quantity)
                    .build();
            orderItems.add(orderItem);
        });

        order.setUser(user);
        order.setEstimatedDeliveryTime(time.plusHours(1));
        order.setOrderStatus(status);
        order.setOrderItems(orderItems);
        order.setPaid(isPaid);
        order.setCreated(LocalDateTime.now());
        order.setPhoneNumber("123456789");
        order.setDeliveryAdress("Bistricka 4");
        order.setDeliveredTime(time.plusMinutes(75));
        order.setTotalQuantity(orderItems.stream().mapToInt(OrderItem::getQuantity).sum());
        order.setTotalPrice(orderItems.stream().mapToDouble(OrderItem::getTotalPrice).sum());
        orderRepository.save(order);
    }

    public Product saveProduct(String name, double price, Category category, String calories, List<String> ingredients, boolean status, String image){
        return productRepository.save(Product.builder()
                                .calories(calories)
                                .name(name)
                                .created(LocalDateTime.now())
                                .price(price)
                                .category(category)
                                .ingredients(ingredients)
                                .status(status)
                                .image(image)
                                .build());
    }
    public User saveUser(String lastName, String firstName, String emailAdress, boolean status, String password, Authority authority){
        return  userRepository.save(User.builder()
                                        .lastName(lastName)
                                        .firstName(firstName)
                                        .enabled(status)
                                        .created(LocalDateTime.now())
                                        .email(emailAdress)
                                        .authority(authority)
                                        .password(passwordEncoder.encode(password))
                                        .imageName("avatar.png")
                                        .build());
    }

    public Authority saveAuthority(String authorityName){
        return  authorityRepository.save(Authority.builder()
                                                  .name(authorityName)
                                                  .created(LocalDateTime.now())
                                                  .build());
    }
    public Category saveCategory(String categoryName){
      return categoryRepository.save(Category.builder()
                                             .created(LocalDateTime.now())
                                             .name(categoryName)
                                             .build());
    };



}
