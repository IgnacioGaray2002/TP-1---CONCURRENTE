package paquete;

public class Locker {
    private LockerState state = LockerState.EMPTY;//cuando esta vacio
    private int counter = 0;
    private int x;
    private int y;
    private Order order;

    public Locker(int x, int y, Order order) {
    }

    public synchronized LockerState getState() {//retorna el estado
        return state;
    }

    public synchronized boolean isAvailable() {//esta disponible
        return this.state == LockerState.EMPTY;
    }

    public synchronized void occupy() {// cambia el estado a ocupado
        if (state == LockerState.EMPTY) {
            state = LockerState.IN_USE;

        }
    }

    public synchronized void release() {//libera el estado de usado a ocupado
        if (state == LockerState.IN_USE) {
            state = LockerState.EMPTY;
        }
    }

    public synchronized void markOutOfService() {//marca la casilla fuera de servicio
        this.state = LockerState.OUT_OF_SERVICE;
    }

    public synchronized boolean isOut() {
        return this.state == LockerState.OUT_OF_SERVICE;
    }

    public synchronized int getCounter() {//devuelve la cantidad que fue usada la casilla
        return counter;
    }

    public void setX(int x) {//setea la fila 
        this.x = x;
    }

    public int getX() {//retorna la fila
        return x;
    }

    public void setY(int y) {//setea la columna
        this.y = y;
    }

    public int getY() {//retorna la columna
        return y;
    }
    public void setOrder(Order order) {this.order = order;counter++;}
    public Order getOrder() {return order;}
    public void removeOrder() {this.order = null;}
}
