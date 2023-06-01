package com.hrodriguesdev;

public class ExceptionAlfa extends RuntimeException  /* RuntimeException */{
    /**
     * importante caso a exceção seja serializada
     */
    private static final long serialVersionUID = 1149241039409861914L;

    // constrói um objeto NumeroNegativoException com a mensagem passada por parâmetro
    public ExceptionAlfa(String msg){
        super(msg);
    }

    // contrói um objeto NumeroNegativoException com mensagem e a causa dessa exceção, utilizado para encadear exceptions
    public ExceptionAlfa(String msg, Throwable cause){
        super(msg, cause);
    }
}

