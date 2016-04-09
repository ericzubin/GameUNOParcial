package servidorchat2;

import java.net.*;
import java.util.*;
import static servidorchat2.ChatHilo.verBaraja;


public class ServerChat
{
	 private static ArrayList<Carta> cartas;
         private static String color[] = {"Azul","Roja","Verde","Amarrilla","COMODIN"};
         private static String valor[] = {"0","1","2","3","4","5","6","7","8","9","CAMBIO DE SENTIDO"," PIERDE EL TURNO","ROBA DOS","CAMBIO DE COLOR","CAMBIO DE COLOR ROBA CUATRO"};
         private static ArrayList<Carta> mesa;
	 private static Vector<Socket> sockets;
         private static ArrayList<Carta> tusCartas;
         private static ArrayList<Mano> Mano;
         private static ArrayList<String> usuarios;	

	public ServerChat(){
		sockets = new Vector<Socket>();
                Mano=new ArrayList<Mano>();
                cartas = new ArrayList<Carta>();
                 mesa = new ArrayList<Carta>();
                 usuarios=new ArrayList<String>();
                  cartas.add(new Carta(color[0], valor[0]));
               cartas.add(new Carta(color[1], valor[0]));
               cartas.add(new Carta(color[2], valor[0]));
               cartas.add(new Carta(color[3], valor[0]));
               //Crear las cartas comodines y que tenga el valor de CAMBIO DE COLOR
               cartas.add(new Carta(color[4], valor[13]));
               cartas.add(new Carta(color[4], valor[13]));
               cartas.add(new Carta(color[4], valor[13]));
               cartas.add(new Carta(color[4], valor[13]));
               //Crear las cartas comodines y que tenga el valor de CAMBIO DE COLOR ROBA CUATRO
               cartas.add(new Carta(color[4], valor[14]));
               cartas.add(new Carta(color[4], valor[14]));
               cartas.add(new Carta(color[4], valor[14]));
               cartas.add(new Carta(color[4], valor[14]));
               //Crear las cartas con valor 1 al 9 con color, Roja,Azul,Verde,Amarrilla
               for(int i=1;i<=12;i++)
               {
                   for(int p=0;p<=3;p++)
                   {
                   cartas.add(new Carta(color[p], valor[i]));
                   cartas.add(new Carta(color[p], valor[i]));
                   }
               }
               barajar();

	}

	public void principal(){
		ServerSocket sSocket;
		try {
			sSocket = new ServerSocket(1901,4);
			while(true)
			{
				Socket socket = sSocket.accept();
				System.out.println(socket);
				sockets.add(socket);
                                primeraPartida();
                             // System.out.println("La cantidad de cartas es: "+ Mano.get(0).sizeCartas());

				ChatHilo chatHilo = new ChatHilo(socket, sockets,Mano,mesa,cartas);
				Thread hilo = new Thread(chatHilo);
				hilo.start();
			}
		} catch (Exception e){
			e.printStackTrace();
		}

	}
           public static String verBaraja(ArrayList<Carta> tipoCartas)
               {
                   String Salida="";
                    for (int i = 0; i < tipoCartas.size(); i++) {
                       Salida+=i +"--.";
                       Salida+=tipoCartas.get(i).getColor();
                       Salida+="__";
                       Salida+=tipoCartas.get(i).getValor();
                       Salida+="               ";
                        

		}
                    return Salida;
               }
            private static void primeraPartida() {
      	     tusCartas= new ArrayList<Carta>();

      for(int i=0;i<=6;i++)
           {
              tusCartas.add(cartas.get(0));
              cartas.remove(0);
           }
                        Mano.add(new Mano(tusCartas));
                      //  System.out.println(tusCartas.size());
                        

    }
 public static void barajar(){
        Collections.shuffle(cartas);
    }
	public static void main(String [] args){
		ServerChat sc = new ServerChat();
		sc.principal();
	}
}


