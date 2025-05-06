package paquete;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class OrderRegister {
    private final List<Order> orders= Collections.synchronizedList(new LinkedList<>());
   public OrderRegister(){


   }
    public  synchronized void  addOrder(Order order) {
        orders.add(order);
   notify();
    }

    public synchronized Order getRandomOrderAndRemove() {
        synchronized (orders) {
            if (orders.isEmpty()) return null;
            int index = (int) (Math.random() * orders.size());
            return orders.remove(index);
        }
    }

    public int size() {
        return orders.size();
    }

    public List<Order> getSnapshot() {
        synchronized (orders) {
            return new LinkedList<>(orders);
        }
    }
    public void removeO(Order order){ orders.remove(order);}

}
