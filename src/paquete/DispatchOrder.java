package paquete;

public class DispatchOrder implements Runnable {
    private final RegistersContainer registers;
    private final LockerGrid lockerGrid;
    private final FlowControl control;



    public DispatchOrder(RegistersContainer registers, LockerGrid lockerGrid,FlowControl control) {
        this.registers = registers;
        this.lockerGrid = lockerGrid;
        this.control=control;

    }

    @Override
    public void run() {
        while (true) {
            Order order;
            synchronized (registers.getInPreparation()){
                while((order=registers.getInPreparation().getRandomOrderAndRemove()) == null) {
                    try {
                        registers.getInPreparation().wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        continue;
                    }
                }
            }
            
                boolean infoCorrecta = Math.random() < 0.85;
                Locker auxL = order.getLocker();//obtengo el locker donde esta guardado el pedido
                if (infoCorrecta) {
                    order.setState(OrderState.ORDER_IN_TRANSIT);//seteo el esado de la orden
                    registers.getInTransit().addOrder(order);//agrego al registro en transito
                    control.marcarPedidosDespachados();//aumento la cantidad de pedidos despachado
                    Logger.logRegister("DESPACHADO", order, control.getPedidosDespachados());
                    auxL.release();//libera el locker
                    auxL.removeOrder();//elimina la orden del locker
                    order.setLocker(null);//elimina el locker de la orden


                } else {

                    order.setState(OrderState.FAILED_ORDER);
                    registers.getFailed().addOrder(order);
                    //Buscar y marcar casillero como fuera de servicio
                    lockerGrid.outService();//contador de casillas fuera de servicio
                    auxL.removeOrder();//elimina la orden del casillero
                    auxL.markOutOfService();//marca el casillero como averiado
                    order.setLocker(null);//setea el locker nulo en la orden
                    Logger.logRegister("FALLIDO fuera de servicio", order,registers.getFailed().size());

                }

            try {
                Thread.sleep((long) (50 + Math.random() * 80));
            } catch (InterruptedException e) {Thread.currentThread().interrupt(); continue;}
            synchronized (registers.getInTransit()) {registers.getInTransit().notifyAll();}

        }


    }

}
