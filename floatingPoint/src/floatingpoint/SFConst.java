/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package floatingpoint;

/**
 *
 * @author sjodiel
 */
public class SFConst {

    //Polarizacao para simples precisao no IEEE754 = 2^10-1
    protected static final int POLARIZACAO = 0x7f; //127

    //Representa o valor limite para o significando em IEEE754 com simples precisao
    protected static final long SIGNIFICANDO = (1l << 23);

    //Representa os 23 bits que tem o significando 2^23-1
    protected static final long BITS_SIGNIFICANDO = 0x7FFFFF; //8388607
 
    //Representa o valor NAN no padrao IEEE754 com simples precisao
    protected static final SingleFormat NAN = new SingleFormat(0, 0xff, (long) 1 << 23); // 255

    //Representa o valor INFINITO no padrao IEEE754 com simples precisao
    protected static final SingleFormat INFINITO = new SingleFormat(0, 0xff, 0);

    //Representa o valor -INFINITO no padrao IEEE754 com simples precisao
    protected static final SingleFormat MENOS_INFINITO = new SingleFormat(1, 0xff, 0);

    //Representa o valor ZERO no padrao IEEE754 com simples precisao
    protected static final SingleFormat ZERO = new SingleFormat(0, 0, 0);

    //Representa o valor -ZERO no padrao IEEE754 com simples precisao
    protected static final SingleFormat MENOSZERO = new SingleFormat(1, 0, 0);

    //Representa o valor maximo representavel no padrao IEEE754 com simples precisao
    protected static final SingleFormat MAX_SINGLE_FORMAT = new SingleFormat(0, 0xfe, ((long) 1 << 23) - 1);

    //Representa o valor minimo representavel no padrao IEEE754 com simples precisao
    protected static final SingleFormat MIN_SINGLE_FORMAT = new SingleFormat(1, 0xfe, ((long) 1 << 23) - 1);

}
