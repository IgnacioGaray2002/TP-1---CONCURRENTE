package paquete;

public class Main {
    public static void main(String[] args) {
        final int TOTAL_ORDERS = 500;


        LockerGrid lockerGrid = new LockerGrid();
        RegistersContainer registers = new RegistersContainer();
FlowControl control=new FlowControl(TOTAL_ORDERS);

        // Preparadores de pedidos (3 hilos)
        for (int i = 0; i < 3; i++) {
            new Thread(new PrepareOrder(lockerGrid, registers, TOTAL_ORDERS,control)).start();

        }

        // Despachadores de pedidos (2 hilos)
        for (int i = 0; i < 2; i++) {
            new Thread(new DispatchOrder(registers, lockerGrid,control)).start();
        }

        // Entregadores de pedidos (3 hilos)
        for (int i = 0; i < 3; i++) {
            new Thread(new CustomerDelivery(registers,control)).start();
        }

        // Verificadores de pedidos (2 hilos)
        for (int i = 0; i < 2; i++) {
            new Thread(new FinalVerification(registers,control)).start();
        }

        // Logger (1 hilo)
        new Thread(new Logger(registers,lockerGrid)).start();
    }
}