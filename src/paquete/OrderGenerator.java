package paquete;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class OrderGenerator {
   private static final int  ORDERS_MAX_NUMBER = 500;
    private int orderCounter=0 ;
    private Random random ;
    private final List<Order> orders=new ArrayList<>();

    public OrderGenerator(){

this.random=new Random();
    }
    public synchronized Order getOrder(int cont){
if (cont <= ORDERS_MAX_NUMBER)
{   orderCounter=cont;
    int ale=random.nextInt(355);
    int aux = orderCounter+ ale;
    Order aux2 = new Order(cont,aux);
    orders.add(aux2);
    return aux2;
}
        return null;
    }
   public synchronized void removeO() {orders.removeFirst();}



    public synchronized int getOrderCounter(){return orderCounter;}



}
