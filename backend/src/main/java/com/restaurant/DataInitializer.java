package com.restaurant;

import com.restaurant.domain.model.Restaurant;
import com.restaurant.domain.model.Role;
import com.restaurant.domain.model.RoleType;
import com.restaurant.domain.model.User;
import com.restaurant.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    final List<String> usernames = List.of("Alice","Jane","Joe","Bob","Harry","Michelle","Sara");
    final List<String> lastNames = List.of("Wood", "Sandoval", "Carr", "Russell", "Cohen", "Hughes", "Morton", "Warren", "Nicholls");
    final List<String> businessNames = List.of("Lonesome Dove", "Melting Pot", "Daytime Place", "Bobby’s", "Easy Eats",
            "Grubber Hub", "Fare & Feed");
    //email generated by username + businessNames
    final List<String> categories = List.of("All Season", "International", "Burgers", "Soul Foods",
            "Handmade Burgers", "Steak and fries", "Cheap wine and food");
    final String desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque egestas semper consequat. " +
            "Cras congue, sapien a hendrerit tempor, tortor quam volutpat justo, quis maximus neque ex at urna. ";

    final List<String> addressStreet = List.of("Pixel Ave", "Easy street", "Matterson St.", "Getty Ln", "Washington Ave", "Wilmar St.", "Superior rd");
    final List<String> city = List.of("St.Paul", "St. Louis", "New Hope", "Crystal", "Edina", "Medina", "Maple Grove");
    final String state = "MN";
    final String zip = "55555";
    final String country = "USA";
    final String imageUrl = "https://picsum.photos/200/300";
    final String phone = "000-000-00000";


    @Autowired private IRoleRepo roleRepo;
    @Autowired private IUserRepo userRepo;
    @Autowired private IRestaurantRepo restaurantRepo;
    @Autowired private IDishRepo dishRepo;
    @Autowired private IImageRepo imageRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        log.trace(" deleting all records .................... ");
//        uncomment to delete all records instead
//        roleRepo.deleteAll();
//        restaurantRepo.deleteAll();
//        userRepo.deleteAll();

        // initialize lists
        List<User> users = new ArrayList<>();
        List<Restaurant> restaurants = new ArrayList<>();

        // create role instances, add to a list to persist
        Role roleRegistered = new Role(RoleType.REGISTERED_USER);
        Role rolePublic = new Role(RoleType.PUBLIC_USER);
        Role roleAdmin = new Role(RoleType.ADMIN);
        Role roleOperator = new Role(RoleType.RESTAURANT_OPERATOR);
        List<Role> roles = List.of(roleRegistered, rolePublic, roleAdmin, roleOperator);


        for (int i = 0; i < 7; i++) {
            User user = new User();
            user.setFirstName(usernames.get(i));
            user.setLastName(lastNames.get(i));
            user.setUsername(user.getFirstName().toLowerCase() +"@restaurant.com");
            user.setPassword(passwordEncoder.encode("Password"));
            user.setEnabled(true);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            user.getAuthorities().add(roleRegistered);
            user.getAuthorities().add(rolePublic);
            users.add(user);


            Restaurant restaurant = new Restaurant();
            String businessName = businessNames.get(i).replace(" ", "_").toLowerCase();
            restaurant.setName(businessName);
            String uri = businessNames.get(i).replace(" ", "").toLowerCase();
            restaurant.setUrl(uri + "@restaurant.com");
            restaurant.setCategory(categories.get(i));
            restaurant.setDescription(desc);
            restaurant.setAddress1(addressStreet.get(i));
            restaurant.setCity(city.get(i));
            restaurant.setState(state);
            restaurant.setZip(zip);
            restaurant.setCountry(country);
            restaurant.setImgUrl(imageUrl);
            restaurant.setPhone(phone);
            restaurant.setHasLicense(true);
            restaurant.setActive(true);
            restaurant.setCreatedAt(LocalDateTime.now());
            restaurant.setUpdatedAt(LocalDateTime.now());

            restaurant.setUser(user); // associate the user to the restaurant
            restaurants.add(restaurant); // add the restaurant to the list

        }

        // SINGLE RUN TO CHECK FOR EXISTING USERS AND SEED ON APP START
        for (User user : users) {
            if (!userRepo.existsByUsername(user.getUsername())) {
                log.trace(" only adding when user does not exist");
                log.trace(" saving users :: {}", users);
                userRepo.saveAll(users);

                log.trace(" saving roles {}", roles);
                roleRepo.saveAll(roles);

                log.trace(" saving restaurant {}", restaurants);
                restaurantRepo.saveAll(restaurants);
                break;
            }
        }

        log.trace(" initializer done ..... ");

    }

}