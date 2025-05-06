package paquete;

public class PrepareOrder implements Runnable {
    private final LockerGrid lockerGrid;
    private final RegistersContainer registers;
    private final int totalOrders;
    private final OrderGenerator generator = new OrderGenerator();
    private final FlowControl control;


    private static int generatedOrders = 0;

    public PrepareOrder(LockerGrid lockerGrid, RegistersContainer registers, int totalOrders,FlowControl control) {
        this.lockerGrid = lockerGrid;
        this.registers = registers;
        this.totalOrders = totalOrders;
this.control=control;

    }

    @Override
    public void run() {

        while (true) {
            int currentNum;
            synchronized (PrepareOrder.class) {
                if (generatedOrders >= totalOrders) break;
                currentNum=generatedOrders+1;
            }

            Locker locker = lockerGrid.getAvailableLocker();
            if (locker == null) {
                try {
                    Thread.sleep(50); // Espera si no hay casilleros o si el casillero esta fuera de servicio
                } catch (InterruptedException ignored) {
                }
                continue;
            }

            Order order = generator.getOrder(currentNum);
            if (order != null) {
                locker.setOrder(order);
                order.setLocker(locker);
                generatedOrders++;
                try {
                    Thread.sleep((long) (20 + Math.random() * 50)); // Simula trabajo variable
                } catch (InterruptedException ignored) {
                }
                synchronized (registers.getInPreparation()) {
                    registers.getInPreparation().addOrder(order);//agrega al registro de preparados
                    registers.getInPreparation().notifyAll();//notifica que se agrego el pedido
                    control.marcarPedidosPreparados();
                    Logger.logRegister("EN PREPARACION", order,control.getPedidosPreparados());
                    generator.removeO();
                }
            }
        }

    }
}
