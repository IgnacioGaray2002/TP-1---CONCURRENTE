package paquete;

public class Order {
    private final int cont;
    private final int id;
    private OrderState state;
   private Locker locker;

    public Order(int cont,int id) {// Crea un pedido
        this.cont=cont;//le asigna un contador
        this.id = id;//Le asigna un id
        this.state = OrderState.ORDER_IN_PREPARATION;// se marca como EN PREPARACION
            this.locker=null;
    }

    public synchronized OrderState getState() {//Obtiene el estado del pedido
        return state;
    }

    public synchronized void setState(OrderState state) {//Setea el estado del pedido
        this.state = state;
    }

    public int getId() {//Retorna el ID
        return id;
    }
    public int getCont(){return cont;}

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public Locker getLocker() {
        return locker;
    }
}
