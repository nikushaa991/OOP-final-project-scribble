package listeners;

import login.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/* This class handles creation and destruction of new sessions. */
@WebListener
public class SessionCreatingListener implements HttpSessionListener{

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent)
    {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {}
}
