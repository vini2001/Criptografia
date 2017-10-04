package br.vini.vinic.criptografiarsa;



public class AritmeticaModular {

    private static char cadeiaDeLetras[] = {
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x',
            'y','z','0','1','2','3','4','5','6','7','8','9',',',';','.','!','@','#','$','%','¨','&','*','(',')',
            '-','_','+','=','[',']','{','}','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P',
            'Q','R','S','T','U','V','W','X','Y','Z','c','?','/','>','<','|','º',':','é','`','´','^','°','í',
            'ó','ô','ú','á','â','ç','î','û','ã','ê','ê'
    };

    public static boolean ehPrimo(long num)
    {
        if (num == 3 || num == 2 || num == 1) return false;

        for (long i = 2; i <= num / 2; i++)
        {
            if (num % i == 0) return false;
        }

        return true;
    }

    public static int substituir(char caractere)
    {
        int pos;
        for(int i = 0; i < 108; i++){
            if(caractere == cadeiaDeLetras[i]){
                return i+101;
            }
        }
        return 250;
    }

    public static int calcularResto(long numero, int E, int N)
    {
        long sobrando = 1;

        do {
            if (E % 2 == 1) {
                E--;
                sobrando = (sobrando * numero) % N;
            }
            if (E > 0) {
                E /= 2;
                numero = restoQuadrado(numero, N);
            }
        } while (E > 0);

        return (int)sobrando;
    }

    public static long restoQuadrado(long numero, long n)
    {
        long exp = numero * numero;
        return exp % n;
    }

    public static char substituir2(int n)
    {
        if (n == 250) return ' ';
        return cadeiaDeLetras[n-101];
    }

    public static int calcularInverso(int E, int fTgt)
    {

        int operacoes[][] = new int[100][5];
        int jafoi = 0;

        operacoes[jafoi][0] = fTgt % E;
        operacoes[jafoi][1] = 1;
        operacoes[jafoi][2] = fTgt;
        operacoes[jafoi][3] = -fTgt / E;
        operacoes[jafoi][4] = E;

        while (operacoes[jafoi][0] != 1)
        {
            jafoi++;

            operacoes[jafoi][0] = operacoes[jafoi - 1][4] % operacoes[jafoi - 1][0];
            operacoes[jafoi][1] = operacoes[jafoi - 1][1];
            operacoes[jafoi][2] = operacoes[jafoi - 1][4];
            operacoes[jafoi][3] = -operacoes[jafoi - 1][4] / operacoes[jafoi - 1][0];
            operacoes[jafoi][4] = operacoes[jafoi - 1][0];

        }

            /*
		     * [0] = resto
		     * [1] = times do dividendo
		     * [2] = dividendo
		     * [3] = quociente
		     * [4] = divisor
		     */

        int master = jafoi;
        jafoi--;



        if (jafoi >= 0) while (operacoes[jafoi][2] != fTgt)
        {

            operacoes[master][1] += operacoes[master][3] * operacoes[jafoi][3];
            operacoes[master][4] = operacoes[jafoi][2];

            int n1, n2;

            n1 = operacoes[master][1];
            n2 = operacoes[master][2];

            operacoes[master][1] = operacoes[master][3];
            operacoes[master][2] = operacoes[master][4];

            operacoes[master][3] = n1;
            operacoes[master][4] = n2;

            jafoi--;
        }


        if (jafoi >= 0) operacoes[master][1] += operacoes[master][3] * operacoes[jafoi][3];
            else
        {
            int n1 = operacoes[master][1];
            int n2 = operacoes[master][2];

            operacoes[master][1] = operacoes[master][3];
            operacoes[master][2] = operacoes[master][4];

            operacoes[master][3] = n1;
            operacoes[master][4] = n2;
        }

        if (operacoes[master][1] < 0) operacoes[master][1] += fTgt;


        int D = operacoes[master][1];

        return D;
    }

}
