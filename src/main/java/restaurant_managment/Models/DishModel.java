package restaurant_managment.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import restaurant_managment.Observer.IObservable;
import restaurant_managment.Observer.IObserver;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dishes")
public class DishModel implements IObservable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Boolean isPopular;

  private Boolean isAvailable;

  private String name;

  private Double price;

  private String description;

  @Transient
  private List<IObserver> observers = new ArrayList<>();

  @Transient
  private Integer totalOrders;

  public void updatePopularity(EntityManager entityManager) {
    totalOrders = entityManager.createQuery("SELECT COUNT(o) FROM OrderModel o JOIN o.dishes d WHERE d.id = :dishId", Long.class)
      .setParameter("dishId", this.id)
      .getSingleResult().intValue();

    if (totalOrders >= 100 && !this.isPopular) {
      this.isPopular = true;
      notifyObservers("Dish " + this.name + " has become popular.");
    }
  }

  @Override
  public void addObserver(IObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(IObserver observer) {
    observers.remove(observer);
  }

  @Override
  public void notifyObservers(String message) {
    for (IObserver observer : observers) {
      observer.update(message);
    }
  }
}