package de.at.home.ftptests;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import de.at.home.ftptests.R;

public class FtpServerActivity extends Activity
{
    private FtpServer server;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server);

        if (this.server != null) return;
        try
        {
            this.server = this.startWebServer();
        }
        catch (Throwable e)
        {
            ByteArrayOutputStream stack = new ByteArrayOutputStream();
            e.printStackTrace(new PrintStream(stack));
            try{stack.close();}catch(Exception e1){}
            ((TextView) findViewById(R.id.server_output)).setText(stack.toString());
        }
    }

    private FtpServer startWebServer() throws Exception
    {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();
        factory.setPort(8022);
        serverFactory.addListener("default", factory.createListener());
        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();

        UserManager um = userManagerFactory.createUserManager();
        BaseUser user = new BaseUser();
        user.setName("some_user");
        user.setPassword("some_password");
        user.setHomeDirectory(Environment.getExternalStorageDirectory().getAbsolutePath());

        List<Authority> auths = new ArrayList<>();
        auths.addAll(user.getAuthorities());
        auths.add(new WritePermission());

        user.setAuthorities(auths);
        um.save(user);

        serverFactory.setUserManager(um);
        FtpServer server = serverFactory.createServer();
        server.start();
        return server;
    }

}
