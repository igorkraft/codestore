static gboolean
  on_button_press (GtkWidget      *event_box, 
                         GdkEventButton *event,
                         gpointer        data)
  {
	static int window_shown;
	static GtkWidget *window, *box, *image, *label;
	/* Don't react to anything other than the left mouse button;
	   return FALSE so the event is passed to the default handler */
	if (event->button != 1)
		return FALSE;

	if (!window_shown) {
		window = gtk_window_new (GTK_WINDOW_TOPLEVEL);
		box = GTK_BOX (gtk_vbox_new (TRUE, 12));
		gtk_container_add (GTK_CONTAINER (window), box);

		image = GTK_IMAGE (gtk_image_new_from_file ("/usr/share/pixmaps/mypicture.png"));
		gtk_box_pack_start (GTK_BOX (box), image, TRUE, TRUE, 12);

		label = gtk_label_new ("Hello World");
		gtk_box_pack_start (GTK_BOX (box), label, TRUE, TRUE, 12);
		
		gtk_widget_show_all (window);
	}
	else
		gtk_widget_hide (GTK_WIDGET (window));

	window_shown = !window_shown;
	return TRUE;
  }
