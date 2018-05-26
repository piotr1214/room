import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/echo/{roomnumber}")


public class EchoServer {

    @OnOpen
     public void onConnectionOpen(final Session session, @PathParam("roomnumber") final String roomnumber) {
        session.getUserProperties().put("roomnumber", roomnumber);
        SessionHandler.sessions.put(String.valueOf(session.getId()), session);
    }
    
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("roomnumber") final String roomnumber){
        System.out.println("Message from " + session.getId() + ": " + message);
        SessionHandler.sendAll(message, roomnumber);
    }

    @OnClose
    public void onClose(Session session) {
        SessionHandler.sessions.remove(session.getId());
    }

    

}


