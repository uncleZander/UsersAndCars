package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
   private static SessionFactory sessionFactory;

   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context =
              new AnnotationConfigApplicationContext(AppConfig.class);

      sessionFactory = context.getBean(SessionFactory.class);
      Session session = sessionFactory.openSession();
      UserService userService = context.getBean(UserService.class);

      Car car1 = new Car("Model A", 1001);
      session.save(car1);
      User user1 = new User("User1", "Lastname1", "user1@mail.ru", car1);
      session.save(user1);

      Car car2 = new Car("Model B", 1002);
      session.save(car2);
      User user2 = new User("User2", "Lastname2", "user2@mail.ru", car2);
      session.save(user2);

      Car car3 = new Car("Model C", 1003);
      session.save(car3);
      User user3 = new User("User3", "Lastname3", "user3@mail.ru", car3);
      session.save(user3);

      Car car4 = new Car("Model D", 1004);
      session.save(car4);
      User user4 = new User("User4", "Lastname4", "user4@mail.ru", car4);
      session.save(user4);

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = " + user.getId());
         System.out.println("First Name = " + user.getFirstName());
         System.out.println("Last Name = " + user.getLastName());
         System.out.println("Email = " + user.getEmail());
         if (user.getCar() != null) {
            System.out.println("Car Model = " + user.getCar().getModel());
            System.out.println("Car Series = " + user.getCar().getSeries());
         }
         System.out.println();
      }

      User userByCarModelAndSeries = userService.findUserByCarModelAndSeries("Model B", 1002);
      System.out.println("User by Car Model and Series: ");
      System.out.println("Id = " + userByCarModelAndSeries.getId());
      System.out.println("First Name = " + userByCarModelAndSeries.getFirstName());
      System.out.println("Last Name = " + userByCarModelAndSeries.getLastName());
      System.out.println("Email = " + userByCarModelAndSeries.getEmail());
      System.out.println("Car Model = " + userByCarModelAndSeries.getCar().getModel());
      System.out.println("Car Series = " + userByCarModelAndSeries.getCar().getSeries());


      session.close();
      context.close();
   }
}
