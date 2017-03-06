#!/usr/bin/python
#coding=UTF-8

import sublime;
import sublime_plugin;
import subprocess;

# Skript in den Ordner /home/user/.config/sublime-text-3/Packages/b64edit kopieren
# Sicherstellen, dass die Programme gzip, gunzip, openssl und base64 installiert sind
# Quellen:
# http://www.sublimetext.com/docs/api-reference
# https://www.sublimetext.com/docs/3/porting_guide.html

class BSixtyFourEditCommand(sublime_plugin.TextCommand):

	def __init__(self, *args, **kwargs):

		super(BSixtyFourEditCommand, self).__init__(*args, **kwargs);
		#print("create BSixtyFourEditCommand object");

	def run(self, edit, method="test", param_1=None):

		if (method == "test"):
			self.test(edit, param_1);
			return;

		if (method == "decrypt"):
			self.decrypt(edit, param_1);
			return;

		if (method == "encrypt"):
			self.encrypt(edit);
			return;

		if (method == "examine_for_repetitive_decryption"):
			self.examine_for_repetitive_decryption(edit);
			return;

	# test(...) kann auf der Konsole aufgerufen werden:
	# view.run_command('b_sixty_four_edit', {"method": "test", "param_1": "Text"})
	def test(self, edit, param_1):

		self.view.erase(edit, sublime.Region(0, self.view.size()));
		self.view.insert(edit, 0, "test_text: " + param_1);

	def decrypt(self, edit, pwd):

		file_path = self.view.file_name();
		p1 = subprocess.Popen(["base64", "-d", file_path], stdout=subprocess.PIPE);
		p2 = subprocess.Popen(["openssl", "enc", "-d", "-aes256", "-pass", "pass:" + pwd],
		     stdin=p1.stdout, stdout=subprocess.PIPE);
		p3 = subprocess.Popen(["gunzip"], stdin=p2.stdout, stdout=subprocess.PIPE);
		p1.stdout.close();
		output = p3.communicate()[0]; # output enthält bytes

		if (len(output) == 0):
			sublime.status_message("Incorrect password.");
			return;

		setattr(self.view, "pwd", pwd);

		self.view.erase(edit, sublime.Region(0, self.view.size()));
		self.view.insert(edit, 0, output.decode("utf-8"));

	def encrypt(self, edit):

		if (not hasattr(self.view, "pwd")): return;

		p1 = subprocess.Popen(["gzip", "-k", "-c", self.view.file_name()], stdout=subprocess.PIPE);
		p2 = subprocess.Popen(["openssl", "enc", "-e", "-aes256", "-pass", "pass:" + getattr(self.view, "pwd")],
		     stdin=p1.stdout, stdout=subprocess.PIPE);
		p3 = subprocess.Popen(["base64", "-w", "80"], stdin=p2.stdout, stdout=subprocess.PIPE);
		p1.stdout.close();
		output = p3.communicate()[0];

		text_file = open(self.view.file_name(), "w");
		text_file.write(output.decode("utf-8"));
		text_file.close();

		setattr(self.view, "recently_saved", True);

	def examine_for_repetitive_decryption(self, edit):

		if (not hasattr(self.view, "pwd")): return;
		if (not hasattr(self.view, "recently_saved")): return;
		if (not getattr(self.view, "recently_saved")): return;

		setattr(self.view, "recently_saved", False);

		self.decrypt(edit, getattr(self.view, "pwd"));

class EventHandler(sublime_plugin.EventListener):

	def on_load(self, view):

		if (not view.file_name().endswith(".b64")) : return;

		self.view = view;
		
		view.window().show_input_panel("Password:", "", self.on_done, None, None);

	def on_done(self, password):

		# Aufruf für Entschlüsselung
		self.view.run_command('b_sixty_four_edit', {"method": "decrypt", "param_1": password});

	def on_post_save(self, view):

		if (not view.file_name().endswith(".b64")) : return;

		# Aufruf für Verschlüsselung
		view.run_command('b_sixty_four_edit', {"method": "encrypt"});

	def on_modified_async(self, view):

		if (view.file_name() == None): return;
		if (not view.file_name().endswith(".b64")) : return;

		# nach dem Speichern und dem dadurch bedingten Neuladen, erneut entschlüsseln
		view.run_command('b_sixty_four_edit', {"method": "examine_for_repetitive_decryption"});