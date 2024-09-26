package hiber.dao;

import hiber.model.User;
import org.hibernate.NonUniqueResultException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User findUserByCarModelAndSeries(String model, int series) {
      TypedQuery<User> query = sessionFactory.getCurrentSession()
              .createQuery("FROM User WHERE car.model = :model AND car.series = :series", User.class);
      query.setParameter("model", model);
      query.setParameter("series", series);

      try {
         return query.getSingleResult();
      } catch (NonUniqueResultException e) {
         throw new IllegalStateException("Запрос вернул более одного результата для " +
                 "модели " + model + "и серии " + series, e);
      } catch (NoResultException e) {
         return null;
      }
   }

}
