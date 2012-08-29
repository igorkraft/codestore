#include <string.h>

#include <panel-applet.h>
#include <gtk/gtklabel.h>

#define start 28
#define end 50

char ip[end - start + 1];

void detectIP()
{
    system("wget -P /home/user http://www.linkliste.l-seifert.de/webtools.php");
    //Datei einlesen
    FILE *datei;
    char text[14000+1];
    datei = fopen ("/home/user/webtools.php", "r");
    ip[end - start] = '\0';
    if (datei == NULL)
    {
        strcpy(ip, "IP nicht ermittelt");
        return;
    }
    fscanf (datei, "%14000c", text);
    fclose (datei);
    text[14000] = '\0';
    // IP parsen
    char* startsWithIP = strstr(text, "Ihre IP Adresse lautet:");
    int curChar;    
    for (curChar = start ; curChar < end ; curChar++)
    {
        if (startsWithIP[curChar] == '<')
        {
            ip[curChar - start] = '\0';
            break;
        }
        ip[curChar - start] = startsWithIP[curChar];
    }
    //Datei löschen
    system("rm /home/user/webtools.php");
}

static gboolean
myexample_applet_fill (PanelApplet *applet,
   const gchar *iid,
   gpointer data)
{
	GtkWidget *label;

	if (strcmp (iid, "OAFIID:ExampleApplet") != 0)
		return FALSE;

    detectIP();
	label = gtk_label_new (ip);
	gtk_container_add (GTK_CONTAINER (applet), label);

	gtk_widget_show_all (GTK_WIDGET (applet));

	return TRUE;
}

PANEL_APPLET_BONOBO_FACTORY ("OAFIID:ExampleApplet_Factory",
                             PANEL_TYPE_APPLET,
                             "The Hello World Applet",
                             "0",
                             myexample_applet_fill,
                             NULL);
