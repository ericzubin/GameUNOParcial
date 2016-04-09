package servidorchat2;

import java.io.*;
import java.net.*;
import java.util.*;

public class ChatHilo implements Runnable
{
	
	private static Vector<Socket> sockets;	
       private static ArrayList<String> usuarios=new ArrayList<String>();	
	private Socket socket;
	private DataInputStream entradaCliente;
  private static ArrayList<Carta> mesa;
      	      private static ArrayList<Carta> cartas;
             private static ArrayList<Mano> Mano;


    ChatHilo(Socket socket, Vector<Socket> sockets, ArrayList<Mano> Mano, ArrayList<Carta> mesa, ArrayList<Carta> cartas) {
            this.socket=socket;
            this.sockets=sockets;
            this.Mano=Mano;
            this.mesa=mesa;
            this.cartas=cartas;      }
                      

  

	private void inicializaEntrada(){
		try {
			entradaCliente = new DataInputStream(
						socket.getInputStream());						
		} catch (IOException ioe){
			System.out.println("Error al inicializar entrada");
		}
	}


	//Llamadas CallBack
	public void run(){
		for(;true;)
		{
			inicializaEntrada();
			try{
				String cadena = entradaCliente.readUTF();
				if (cadena.toUpperCase().startsWith("M")){
					enviaMensaje(cadena);
                                       mensajeMesaString(cadena);

                                        
				} else {
					if (cadena.toUpperCase().startsWith("J"))
					{
						mensajeEntrada(cadena);

					} else {
						if (cadena.toUpperCase().startsWith("P"))
						{
							mensajeSalida(cadena);
							socket.close();
							return;	
						}
					}
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}

	}


	public void enviaMensaje(String msg){
		for (Socket socket :  sockets){
			try{
				DataOutputStream salidaCliente;
				salidaCliente = new DataOutputStream(
							socket.getOutputStream());
                                StringTokenizer st = 
					new StringTokenizer(msg, "^");
		                st.nextToken();
                                String userIP = st.nextToken();
                                StringTokenizer stui = 
        			new StringTokenizer(userIP,"@");
                                String usuario =  stui.nextToken();
                                String ip = stui.nextToken();
                                st.nextToken();
		                String contenido=st.nextToken();
                                int Intusuario=usuarios.indexOf(usuario);
                                if(Intusuario==-1)
                                {
                                                                    salidaCliente.writeUTF(msg);
   
                                }else
                                {
                                   int contenidoN=Integer.parseInt(contenido);
                                   System.out.println("El contenido es: " + contenidoN);
                                   salidaCliente.writeUTF(msg); 
                                }
                          
                              

                                
                                   
                                 
			}catch (Exception e ){
				e.printStackTrace();
			}

		}
	} 
          public  void JugarCarta(int Mano,int Jugador){
       
             if(ComprobarColor(Mano,Jugador)||ComprobarNumero(Mano,Jugador))
             {
              //mesa.add(tusCartas.get(Mano));
                 mesa.add(ChatHilo.Mano.get(Jugador).getCartaM(Mano));
                 ChatHilo.Mano.remove(Mano);
             return ;
             }else
             {
                String mensaje="_____________Error_________";
                enviaMensaje(mensaje,Jugador);
                 return ;
             }
           
          

    }
          public  boolean ComprobarColor(int Mano,int Usuario)
      { 
          if(mesa.size()==0)
          {
              return true;
          }
          if(mesa.get(mesa.size()-1).getColor().equals(ChatHilo.Mano.get(Usuario).getCartaM(Mano).getColor()))
               {
                 return true;
               }
               else
                {
                 return false;
                }
      }
     
         public  boolean ComprobarNumero(int Mano,int Usuario)
      { 
           if(mesa.size()==0)
          {
              return true;
          }
          if(mesa.get(mesa.size()-1).getValor().equals(ChatHilo.Mano.get(Usuario).getCartaM(Mano).getValor()))
               {
                 return true;
               }
               else
                {
                 return false;
                }
      }
     public  boolean ComprobarColorComodines(int Mano,int Usuario) throws IOException
     {
            if(mesa.size()==0)
          {
              return true;
          }
          
          if("COMODIN".equals(ChatHilo.Mano.get(Usuario).getCartaM(Mano).getColor()))
               {
                  // System.out.println("Escoger el nuevo color \n1.- Azul  \n2.- Roja  \n3.-Verde \n4.-Amarrilla");
                  return true;
               }
               else
                {
                 return false;
                }   
     }
        /*
     public static void JugarCarta(int Mano,int Usuario){
       
             if(ComprobarColor(Mano,Usuario)||ComprobarNumero(Mano,Usuario))
             {
              mesa.add(tusCartas.get(Mano));
             tusCartas.remove(Mano);   
             return ;
             }else
             {
                 System.out.println("_____________Error_________");
                 return ;
             }
     }*/
           
             public void enviaMensaje(String msg,int Jugador){
			try{
				DataOutputStream salidaCliente;
				salidaCliente = new DataOutputStream(
							sockets.get(Jugador).getOutputStream());
				salidaCliente.writeUTF(msg);
			}catch (Exception e ){
				e.printStackTrace();
			}

		
	} 
	public void mensajeEntrada(String msg)throws Exception{
		StringTokenizer st = 
					new StringTokenizer(msg, "^");
		st.nextToken();
		String userIP = st.nextToken();
		StringTokenizer stui = 
					new StringTokenizer(userIP,"@");
		String usuario =  stui.nextToken();
		String ip = stui.nextToken();
		String cadena = "m^Server@" + 
				InetAddress.getLocalHost().getHostAddress()	
				 + 	"^–^" + usuario + " entro desde " + ip;
                this.usuarios.add(usuario);
		enviaMensaje(cadena);
                int Intusuario=usuarios.indexOf(usuario);
                System.out.println("IdUsuario: "+Intusuario);
                enviaMensaje(cadena + "TusCartas"+ verBaraja(Mano.get(Mano.size()-1).getTusCartas()),sockets.size()-1);
	}
                public void mensajeMesaString (String msg)throws Exception{
		StringTokenizer st = 
					new StringTokenizer(msg, "^");
		st.nextToken();
		String userIP = st.nextToken();
		StringTokenizer stui = 
					new StringTokenizer(userIP,"@");
		String usuario =  stui.nextToken();
		String ip = stui.nextToken();
		String cadena = "m^Server@" + 
				InetAddress.getLocalHost().getHostAddress()	
				 + 	"^–^";
                enviaMensaje(cadena + "La cartas de la Mesa"+ verBaraja(mesa));
	}
	public void mensajeSalida(String msg)throws Exception
	{
		StringTokenizer st = 
					new StringTokenizer(msg, "^");
		st.nextToken();
		String userIP = st.nextToken();
		StringTokenizer stui = 
					new StringTokenizer(userIP,"@");
		String usuario =  stui.nextToken();
		String ip = stui.nextToken();
		String cadena = "m^Server@" + 
				InetAddress.getLocalHost().getHostAddress()	
				 + 	"^–^" + usuario + "salio desde " + ip;
		sockets.remove(socket);		 
		enviaMensaje(cadena);
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

}