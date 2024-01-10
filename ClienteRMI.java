import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
 
public class Cliente {
    //Constructor
    private Cliente(){}
 
    public static void main(String&#91;] args)
    {
        int numero;
        String direccionServidor;
        if (args.length == 2) {
            direccionServidor = args&#91;0];
            numero = Integer.parseInt(args&#91;1]);
        }
        else {
            Scanner lecturaTeclado = new Scanner(System.in);
            System.out.print("Introduzca la dirección IP o DNS del servidor RMI: ");
            direccionServidor = lecturaTeclado.nextLine();
            System.out.print("Introduzca un número: ");
            numero =  Integer.parseInt(lecturaTeclado.nextLine());
        }
        //Si no se indica dirección de servidor, se asume "null" (localhost)
        if (direccionServidor.equals("")) {
            direccionServidor = null;
        }
 
        try {
            //Obteniendo registro de rmiregistry
            System.out.println("Obteniendo registro de rmiregistry...");
            Registry registry = LocateRegistry.getRegistry(direccionServidor);
 
            //Buscando el objeto RMI remoto
            System.out.println("Buscando objeto RMI rmiPrimo y creando stub...");
            PrimoRMI stub = (PrimoRMI) registry.lookup("rmiPrimo");
            System.out.println("Objeto rmiPrimo remoto encontrado...");
 
            System.out.println();
            System.out.println("Ejecutando método remoto RMI esPrimo en servidor...");
            boolean resultado = stub.esPrimo(numero);
            if (resultado) {
                System.out.println("El número " + numero + " es primo según el servidor RMI");
            }
            else {
                System.out.println("El número " + numero + " NO es primo según el servidor RMI");
            }
 
            System.out.println();
            System.out.println("Fin programa cliente RMI");
        } catch (Exception e) {
            System.out.println("Error en cliente RMI: " + e.getMessage());
        }
    }
}