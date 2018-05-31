
package factoriel_thread;


import java.math.BigInteger;


   class factoriel implements Runnable {

        BigInteger factoriel = BigInteger.valueOf(1);
        BigInteger last_interval_number;
        BigInteger first_interval_number;
        thread_result result;

        public factoriel(BigInteger b, BigInteger s ,thread_result sonuc) {
            first_interval_number = b;
            last_interval_number = s;
            this.result=sonuc;
        }

        @Override
        public void run() {

            for (int i = first_interval_number.intValue(); i <= last_interval_number.intValue(); i++) {

                factoriel = factoriel.multiply(BigInteger.valueOf(i));
            }
            result.get_thread_result=factoriel;

        }
    }
