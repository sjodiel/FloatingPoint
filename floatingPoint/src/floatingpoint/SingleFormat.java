/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package floatingpoint;

/**
 * Single Format 
 * 
 * @author sjodiel
 */
public class SingleFormat implements Comparable, Cloneable {

    //Significando (23 bits) para o numero IEE754 de simples precisao
    protected long significando;

    //Exponente (8) bits para o numero IEE754 de simples precisao
    protected int exponente;

    //Sinal (1 bit) para o numero IEE754 simples precisao
    protected int sinal;

    /**
     * Construtor pradrao. Cria uma SingleFormat a partir de um numero do tipo
     * double.
     *
     * @param num Numero double
     */
    public SingleFormat(double num) {

        //Casos especiais
        if (Double.isNaN(num)) { // Not a Number
            this.significando = Constantes.NAN.significando;
            this.sinal = Constantes.NAN.sinal;
            this.exponente = Constantes.NAN.exponente;
        } else if (num == Double.NEGATIVE_INFINITY) { // infinito negativo
            this.significando = Constantes.MENOS_INFINITO.significando;
            this.sinal = Constantes.MENOS_INFINITO.sinal;
            this.exponente = Constantes.MENOS_INFINITO.exponente;
        } else if (num == Double.POSITIVE_INFINITY) { // infinito positivo
            this.significando = Constantes.INFINITO.significando;
            this.sinal = Constantes.INFINITO.sinal;
            this.exponente = Constantes.INFINITO.exponente;
        } else if (num == 0) { // numero for 0
            this.exponente = 0;
            this.significando = 0;
            if (1.0 / num == Double.POSITIVE_INFINITY) //PORQUE 0.0==-0.0 eh TRUE
            {
                this.sinal = 0;
            } else {
                this.sinal = 1;
            }

        } //Caso de numeros normais
        else {
            //verifica se numero eh negativo ou positivo
            // sendo positivo, o bit de sinal recebe 0
            // negativo, o bit de sinal recebe 1
            if (num >= 0) {
                sinal = 0;
            } else {
                sinal = 1;
                num = -num;
            }

            // Para numeros positivos
            if (num > 1) {
                //extraindo o expoente do numero, usnado logaritmo
                exponente = (int) (Math.log(num) / Math.log(2)); // > log_2 (Num) 

                //verificando a base do expoente para opter o significando
                double base_exp = Math.pow(2, exponente);

                // obtendo a significando e retirando a parte inteira: 1.5 - 1 = 0.5
                double significando_double = (num) / base_exp - 1.0;

                //pode haver erros de arredondamentos, que fazem a significando ficar negativo	
                if (significando_double < 0) {
                    significando_double = 0;
                }
                significando = (long) (significando_double * Constantes.SIGNIFICANDO); //(valor * (1<<23))

            } else { // para numeros negativo (num<1)
                exponente = (int) Math.ceil(-Math.log(num) / Math.log(2)); //> log_2 (Num)
                significando = (long) (((num * Math.pow(2, exponente) - 1) * Constantes.SIGNIFICANDO));
                exponente = -exponente;
            }

            //colocar o exponente em representacao polarizada
            if (num != 0) {
                exponente = exponente + Constantes.POLARIZACAO;
            }

        }
    }

    /**
     * Imprime em uma String, no formato: Simple: Sinal Exponente Significando
     *
     * @return String seguindo o formato Simple: Sinal Expoente Significando
     */
    @Override
    public String toString() {
        //trata os casos especiais
        if (this.compareTo(Constantes.INFINITO) == 0) {
            return "Infinito" + ": " + sinal + " " + Integer.toBinaryString(exponente) + " " + "00000000000000000000000";
        } else if (this.compareTo(Constantes.MENOS_INFINITO) == 0) {
            return "Menos Infinito" + ": " + sinal + " " + Integer.toBinaryString(exponente) + " " + "00000000000000000000000";
        } else if (this.compareTo(Constantes.NAN) == 0) {
            return "NaN" + ": " + sinal + " " + Integer.toBinaryString(exponente) + " " + Long.toBinaryString(significando);
        } else if (this.exponente == 0 && this.significando == 0 && this.sinal == 0) {
            return "0.0" + " : " + sinal + " " + "00000000" + " " + "00000000000000000000000";
        } else if (this.exponente == 0 && this.significando == 0 && this.sinal == 1) {
            return "-0.0" + " : " + sinal + " " + "00000000" + " " + "00000000000000000000000";
        }

        String temp;
        temp = Integer.toBinaryString(this.exponente);

        //gera uma sequencia de bits para o expoente 
        //e preenche com zeros a esquerda ate ter 8 bits
        String exponente = "";
        for (int i = temp.length(); i < 8; i++) {
            exponente = exponente.concat("0");
        }
        exponente = exponente.concat(temp);

        //gera uma sequencia de bits para a significando
        //e preenche com zeros a esquerda ate que tenha 23 bits
        temp = Long.toBinaryString(this.significando);

        String significando = "";

        for (int i = temp.length(); i < 23; i++) {
            significando = significando.concat("0");
        }
        significando = significando.concat(temp);

        return this.NumeroReal() + "" + " : " + sinal + " " + exponente + " " + significando;
        //return sinal + "" + exponente + "" + significando;

    }

    /**
     * Construtor de copia.
     *
     * @param n Numero de simples precisao, a patir do qual iniciliza-se o novo
     * numero
     */
    public SingleFormat(SingleFormat n) {
        exponente = n.exponente;
        significando = n.significando;
        sinal = n.sinal;
    }

    /**
     * Construtor parametrizado. Este construtor esta reservado para construir
     * os casos especiais
     *
     * @param s Sinal
     * @param e Expoente
     * @param m Significando
     */
    protected SingleFormat(int s, int e, long m) {
        this.sinal = s;
        this.significando = m;
        this.exponente = e;
    }

    /**
     * Cria uma copia de um numero de simples precisao
     *
     * @param o Numero de simples precisao
     * @return Copia do numero de simples precisao <em>o</em>
     */
    public Object clone(Object o) {
        return new SingleFormat((SingleFormat) o);
    }

    /**
     * Metodo de SOMA
     *
     * @param n2
     * @return
     */
    public SingleFormat Soma(SingleFormat n2) {

        //TRATA os casos especias
        //um dos somando eh 0
        if (this.exponente == 0 && this.significando == 0) {
            return new SingleFormat(n2);
        } else if (n2.exponente == 0 && n2.significando == 0) {
            return new SingleFormat(this);
        } //SE Somaddos NUMEROS OpostoS, RESULTADO VALE 0
        else if (this.exponente == n2.exponente && this.significando == n2.significando && this.sinal != n2.sinal
                && this.exponente < 0x7f) {
            return new SingleFormat(Constantes.ZERO);
        } //um dos eh NAN
        else if (this.exponente == 0xff && this.significando != 0 || n2.exponente == 0xff && n2.significando != 0) {
            return new SingleFormat(Constantes.NAN);
        } else if (this.exponente == 0xff && this.significando == 0 && this.sinal == 0) {
            if (n2.exponente == 0xff && n2.significando == 0 && n2.sinal == 1) {
                return new SingleFormat(Constantes.NAN);
            } else {
                return new SingleFormat(Constantes.INFINITO);
            }
        } else if (this.exponente == 0xff && this.significando == 0 && this.sinal == 1) {
            if (n2.exponente == 0xff && n2.significando == 0 && n2.sinal == 0) {
                return new SingleFormat(Constantes.NAN);
            } else {
                return new SingleFormat(Constantes.MENOS_INFINITO);
            }
        } else if (n2.exponente == 0xff && n2.significando == 0 && n2.sinal == 0) {
            if (this.exponente == 0xff && this.significando == 0 && this.sinal == 1) {
                return new SingleFormat(Constantes.NAN);
            } else {
                return new SingleFormat(Constantes.INFINITO);
            }
        } else if (n2.exponente == 0xff && n2.significando == 0 && n2.sinal == 1) {
            if (this.exponente == 0xff && this.significando == 0 && this.sinal == 0) {
                return new SingleFormat(Constantes.NAN);
            } else {
                return new SingleFormat(Constantes.MENOS_INFINITO);
            }
        }

        int exp1 = this.exponente;
        long significando1 = this.significando | Constantes.SIGNIFICANDO;
        int sinal1 = this.sinal;

        int exp2 = n2.exponente;
        long significando2 = n2.significando | Constantes.SIGNIFICANDO;
        int sinal2 = n2.sinal;

        //ajustando os expoente e a significando
        //Adiciona no signficando o 1.0 que IEEE 754 tem implicito
        //Se o 1 operando eh > 2, desloca o significando do segundo para a direita
        //Se o 2 > 1,descocla o signficando do primeiro para a direita
        if (exp1 > exp2) {
            if (exp1 - exp2 < 32) {
                significando2 = (significando2) >> (exp1 - exp2);
            } else {
                significando2 = 0;
            }

            //exp2 = exp1;
        } else if (exp1 < exp2) {
            if (exp2 - exp1 < 32) {
                significando1 = (significando1) >> (exp2 - exp1);
            } else {
                significando1 = 0;
            }
            exp1 = exp2;
        }

        if (sinal1 != sinal2) // se os operandos tem sinal diferente
        {
            if (significando2 > significando1) {
                significando1 = -significando1;
            } else {
                significando2 = -significando2;
            }
        }

        significando1 = significando1 + significando2;

        //
        if (sinal1 != sinal2) //SE OS OPERANDOS TEM SINAL DIFERENTE
        {

            if (this.exponente < n2.exponente || (this.exponente == n2.exponente && this.significando < n2.significando)) {
                sinal1 = sinal2;
            }
            int dsp = 0; //descolamento 
            while ((significando1 << dsp) < Constantes.SIGNIFICANDO) {
                dsp++;
            }
            significando1 = (significando1 << dsp) & Constantes.BITS_SIGNIFICANDO;
            exp1 -= dsp;

        } else {
            int dsp = (int) (significando1 >> 24) & 0x1;
            exp1 += dsp;
            significando1 = (significando1 >> dsp) & Constantes.BITS_SIGNIFICANDO;//REMOVe O '1' IMPLÍCITO
        }
        //overflow
        if (exp1 >= 0xff) {
            if (sinal1 == 0) {
                return new SingleFormat(Constantes.MAX_SINGLE_FORMAT);
            } else {
                return new SingleFormat(Constantes.MIN_SINGLE_FORMAT);
            }
        }

        return new SingleFormat(sinal1, exp1, significando1);
    }

    /**
     * Gera um numero real em formato simple: sinal + exponente + nantissa
     *
     * @return O nume no formato double
     */
    public double NumeroReal() {
        // fazendo o tramaneto dos casos especias, zero, menos zero, infinito e menos infintio
        if (this.significando == 0 && this.exponente == 0 && this.sinal == 0) {
            return 0.0;
        } else if (this.significando == 0 && this.exponente == 0 && this.sinal == 1) {
            return -0.0;
        } else if (this.significando == Constantes.INFINITO.significando && this.exponente == Constantes.INFINITO.exponente
                && this.sinal == Constantes.INFINITO.sinal) {
            return Double.POSITIVE_INFINITY;
        } else if (this.significando == Constantes.MENOS_INFINITO.significando && this.exponente == Constantes.MENOS_INFINITO.exponente
                && this.sinal == Constantes.MENOS_INFINITO.sinal) {
            return Double.NEGATIVE_INFINITY;
        } else if (this.exponente == 0xff && this.significando != 0) {
            return Double.NaN;
        }

        //AJUSTA A SIGNIFICANDO, ADICIONANDO O 1 IMPLÍCITO:
        //OBS CRIAR UM METODO PARA ISSO..
        double sign_double = (double) (this.significando | Constantes.SIGNIFICANDO) / Constantes.SIGNIFICANDO;
        double real = sign_double * Math.pow(2, this.exponente - Constantes.POLARIZACAO);

        //Ajutar o sinal
        if (this.sinal == 1) {
            return -real;
        } else {
            return real;
        }
    }

    /**
     * Compara dois numeros
     *
     * @param o Segundo operando da comparacao
     * @return Resultado da comparacao entre dois numeros de simples precisao: 1
     * se op1 > op2; 0 se op1==op2; -1 em outro caso.
     */
    @Override
    public int compareTo(Object o) {
        SingleFormat op2 = (SingleFormat) o;

        if (this.significando == 0 && this.exponente == 0 && op2.significando == 0 && op2.exponente == 0) {
            return 0;
        }
        if (this.sinal > op2.sinal) {
            return -1;
        } else if (this.sinal < op2.sinal) {
            return 1;
        } else if (this.exponente > op2.exponente) {
            return 1;
        } else if (this.exponente < op2.exponente) {
            return -1;
        } else {
            return new Long(this.significando).compareTo(op2.significando);
        }
    }

}
