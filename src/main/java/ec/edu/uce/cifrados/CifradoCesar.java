package ec.edu.uce.cifrados;

public class CifradoCesar implements Cifrador {
    private int llave;

    //Constructor inicicial
    public CifradoCesar(int llave) {
        this.llave = llave;
    }


    @Override
    public String cifrar(String texto) {
        StringBuilder resultado = new StringBuilder();
        for(char c : texto.toCharArray()){
            if(Character.isLetter(c)){
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                resultado.append((char) base + (c- base+this.llave)%26);
                System.out.println("");
            }else {
                resultado.append(c);
            }
        }
        return resultado.toString();
    }
}
