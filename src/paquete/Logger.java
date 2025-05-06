package paquete;

import java.lang.Thread.State;

public class Logger implements Runnable {
    private final RegistersContainer registers;
    private final LockerGrid lockers;
    

    public Logger(RegistersContainer registers,LockerGrid lockers) {
        this.registers = registers;
        this.lockers=lockers;
        
    }
    public static synchronized void logRegister(String tipo, Order order,int control1){

        System.out.println(" Registro --> " +  tipo + " - CONT - " + order.getCont() + " - ID  - " + order.getId());
        System.out.println(" el FLOW control ------>" + tipo + " CANTIDAD : "+ control1);
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        while (true) {
            int failed = registers.getFailed().size();
            int verified = registers.getVerified().size();
           // int outService = lockers.getFailed();//fuera de servicio

            System.out.println("LOG [+" + (System.currentTimeMillis() - start) + "ms] -> Fallidos: " + failed + ", Verificados: " + verified );
            if ((failed + verified ) >= 500) break;

            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {}
        }
        long end=System.currentTimeMillis();
        long timeTotal=end-start;
        double timeSeconds=timeTotal/1000.0;

        System.out.println("\n** Estado final de los lockers **");

        int total = 0;
        int usados = 0;
        int fueraServicio = 0;
        int sumaOcupaciones = 0;
        for (int i = 0; i < lockers.getRows(); i++) {
            for (int j = 0; j < lockers.getCols(); j++) {
                Locker locker = lockers.getLocker(i,j);
                if(locker!=null){
                System.out.println("Locker - X: " + i +
                                   ", Y: " + j +
                                   ", Estado: " + locker.getState() +
                                   ", Veces ocupado: " + locker.getCounter());
                total++;
                if (locker.getCounter() > 0) usados++;
                if (locker.getState() == LockerState.OUT_OF_SERVICE) fueraServicio++;
                sumaOcupaciones += locker.getCounter();
                }
            }
        }
    
        double porcentajeFuera = 100.0 * fueraServicio / total;
        double promedioUso = total - fueraServicio > 0 ? (double) sumaOcupaciones / (total - fueraServicio) : 0.0;
    
        System.out.println("\n** Resumen  Lockers **");
        System.out.println("Total de lockers: " + total);
        System.out.println("Lockers usados al menos una vez: " + usados);
        System.out.println("Suma ocupaciones : " + sumaOcupaciones);
        System.out.println("Lockers fuera de servicio: " + fueraServicio + " (" + String.format("%.2f", porcentajeFuera) + "%)");
        System.out.println("Promedio de veces ocupado (excluyendo fuera de servicio): " + String.format("%.2f", promedioUso));
        System.out.println("\n** Tiempo  de Procesamiento **");
        System.out.println("\n** Tiempo Total **" + timeTotal + " [ms]");
        System.out.println("\n** Tiempo Total en segundos  **" + timeSeconds + "[s]");
    } 
    


}
