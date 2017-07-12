/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factoriel_thread;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Factoriel_thread_test {

    public  static void dosyaya_yaz(BigInteger sonuc) throws IOException {
        Writer wr = new FileWriter("Result.txt");
        wr.write(String.valueOf("Factoriel Result:"+sonuc)); // write int

        wr.flush();
        wr.close();
    }
    public static void main(String[] args) throws InterruptedException, IOException {
        thread_result parallel_calculation_result_1 = new thread_result();
        thread_result parallel_calculation_result_2 = new thread_result();
        thread_result series_calculation = new thread_result();

        BigInteger number = BigInteger.valueOf(1000); //number of factoriel
        
        
        //Seri hesaplama yapilir
        Runnable series_calc = new factoriel(BigInteger.valueOf(1), number, series_calculation);
        Thread series_thread = new Thread(series_calc);

        long series_calc_time_start = System.nanoTime();
        series_thread.start();
        series_thread.join();
        long series_calc_time_end = System.nanoTime();//hesaplama bitiyor
        double series_time_result = (series_calc_time_end - series_calc_time_start) / 1000000.0;
        
        //Paralel hesaplama yapilir
        ExecutorService parallel_executor = Executors.newFixedThreadPool(2);
        BigInteger interval = number.multiply(BigInteger.valueOf(4));
        interval = interval.divide(BigInteger.valueOf(10));

        parallel_executor.execute(new factoriel(BigInteger.valueOf(1), interval, parallel_calculation_result_1));
        parallel_executor.execute(new factoriel(interval.add(BigInteger.valueOf(1)), number, parallel_calculation_result_2));
        double parallel_calc_time_start = System.nanoTime();
        parallel_executor.shutdown();
        while (!parallel_executor.isTerminated()) {
        }

        BigInteger sonuc = parallel_calculation_result_1.get_thread_result.multiply(parallel_calculation_result_2.get_thread_result);

        long parallel_calc_time_end = System.nanoTime();
        double paralel_sure = (parallel_calc_time_end - parallel_calc_time_start) / 1000000.0;
        
        
        System.out.print("Number:"+number);
        System.out.print("\nSeries caluculating result: " + String.format("%.2f", series_time_result) + " ms.");
        System.out.print("\nParallel caluculating result: " + String.format("%.2f", paralel_sure) + " ms.");
        System.out.print("\nThe result was written to the file...\n");
        
        
        dosyaya_yaz(sonuc);
    }
    
}
