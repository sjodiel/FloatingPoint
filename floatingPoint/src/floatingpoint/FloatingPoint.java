/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package floatingpoint;

import java.util.Scanner;

/**
 * ARITMÉTICA EM PONTO FLUTUANTE PADRAO IEEE754
 *
 * @author sjodiel
 */
public class FloatingPoint {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("");

        SingleFormat a = new SingleFormat(0f);
        SingleFormat b = new SingleFormat(2f);
        SingleFormat c = new SingleFormat(1);
        SingleFormat d = new SingleFormat((float) 0.2);

        //System.out.println(a.Soma(b));
        System.out.println(a.Diferenca(b));
        //System.out.println(a.Produto(b));
        //System.out.println(b.Divisao(a));
        //System.out.println(d.toString());
        //System.out.println(a.toString()); 

        /*int intBits = Integer.parseInt(a.toString(), 2);
        float myFloat = Float.intBitsToFloat(intBits);
        System.out.println(myFloat); 
         */
        int exp, exp2, mantissa;
        int sinal;
        double num = 0.2f;
        exp = (int) (Math.log(num) / Math.log(2)); // -> log_2 N (logaritmando/base do logaritmo)

        double base_exp = Math.pow(2, exp);// 2 a exp  

        double mantissa_ = (num) / base_exp; // -1 para tirar o valor implicito

        if (num >= 0) {
            sinal = 0;
        } else {
            sinal = 1;
            //num = -num;
        }

        System.out.println("Expoente " + exp);
        System.out.println("Base do exp: " + base_exp);
        System.out.println("Mantissa: " + mantissa_);

        mantissa = (int) (mantissa_ * (1 << 23));
        int exp_ = exp + 0x7f;//127

        System.out.println("Lim Total Mantissa: " + (1 << 23));
        System.out.println("Lim Total Expoente : " + (1 << 8));

        System.out.println("Expeonte Ajustado: " + exp_);
        System.out.println("Mantissa Ajustada: " + mantissa);

        String str = Integer.toBinaryString(exp_);
        System.out.println(str);

        String str2 = Integer.toBinaryString(mantissa);
        System.out.println(str2.substring(1));

        String mantissa2 = str2.charAt(0) + "." + str2.substring(1);
        System.out.println(sinal + " " + str + " " + str2.substring(1));

    }

    /*public static int menu() {

        Scanner entrada = new Scanner(System.in);
        int op;
        System.out.println("\tARITMÉTICA EM PONTO FLUTUANTE PADRAO IEEE754");
        System.out.println("1: Informar arquivo com produtos\n"
                + "2:Ordenar por nome do produto utilizando Selection Sort\n"
                + "3:Ordenar por fabricante utilizando Merge Sort\n"
                + "4:Ordenar por preço utilizando Quicksort\n"
                + "5:Ordenar por data de validade HeapSort\n"
                + "6:Selection Sort com lista encadeada\n"
                + "0:Sair\n"
        );
        //try{
        do {
            System.out.print("Escolha uma opcao: ");
            op = entrada.nextInt();
            if (op < 0 || op > 6) {
                System.out.println("Informe um valor entre 0 e 6!");
            }
        } while (op < 0 || op > 6);
        //}catch(InputMismatchException exc){
        //System.out.println("Erro: Valor infomado inválido");
        //System.err.println("Valor incorreto!\nMensagem: " + exc.getMessage());    
        //}
        return op;
    }//fim menu 
    */
}
