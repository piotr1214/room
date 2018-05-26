import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;

public class SessionHandler {  
    
    public static final Map<String, Session> sessions = Collections.synchronizedMap(new HashMap<String, Session>());
   
    public static void sendToSession(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            Logger.getLogger(SessionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
         public static void sendAll(String text, String roomNumber) {
        synchronized (sessions) {
            for (Map.Entry<String, Session> entry : sessions.entrySet()) {
                Session s = entry.getValue();
                if (s.isOpen() && s.getUserProperties().get("roomnumber").equals(roomNumber)) {
                    entry.getValue().getAsyncRemote().sendText(text);
                }
            }
        }
    }
    
    
}
