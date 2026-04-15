package ec.edu.uce.cifrados;

public class CifradoVigenere implements Cifrador {
    private String llave;

    public CifradoVigenere(String llave) {
        this.llave = llave.toUpperCase().replaceAll("[^A-Z]", "");

    }

    @Override
    public String cifrar(String texto) {
        StringBuilder resultado = new StringBuilder();
        //indice clave
        int indiceLlave = 0;

        for(int i=0; i<texto.length();i++){
            char c = texto.charAt(i); //sacar la letra actual
            if(Character.isLetter(c)){
                // Detectamos si el texto actual esta en mayuscula
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                //calculamos cuanto debemos desplazarnos
                char letraLlave = llave.charAt(indiceLlave % llave.length());
                int desplazamiento = letraLlave - 'A'; // Convertimos la letra a un número del 0 al 25

                //(Posición letra original + desplazamiento de la llave) % 26
                char nuevoCaracter = (char) (base + (c - base + desplazamiento) % 26);
                resultado.append(nuevoCaracter);

                //Avanzamos el indice de la llave
                indiceLlave++;
            }else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }
}
