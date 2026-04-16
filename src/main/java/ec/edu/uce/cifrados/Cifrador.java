package ec.edu.uce.cifrados;

public interface Cifrador {
    //Todos los cifrados deben recibir un texto y devolverlo
    String cifrar(String texto);
    String descifrar(String texto);
}
