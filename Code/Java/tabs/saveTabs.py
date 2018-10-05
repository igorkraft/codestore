#!/usr/bin/python
#coding=UTF-8

import sys
from io import StringIO
import uuid

template = \
	'<!DOCTYPE html>\n' + \
	'<html xmlns="http://www.w3.org/1999/xhtml">\n' + \
	'<head>\n' + \
	'	<meta charset="utf-8">\n' + \
	'	<style type="text/css">\n' + \
	'		input\n' + \
	'		{\n' + \
	'			width: 95%;\n' + \
	'		}\n' + \
	'	</style>\n' + \
	'</head>\n' + \
	'<body>\n' + \
	'	<form id="tabsForm" name="tabsForm" action="http://localhost:8080/openTabs" method="post">\n' + \
	'\n' + \
	'${link}\n' + \
	'\n' + \
	'		<button type="submit">Öffnen</button>\n' + \
	'\n' + \
	'	</form>\n' + \
	'</body>\n' + \
	'</html>';

test = sys.stdin.read();

s = StringIO(test);
fileName = s.readline().strip();

for line in s:
	name = str(uuid.uuid4().fields[0]);
	template = template.replace('${link}', '${link}\n\t\t<input name="' + name + '" value="' + line.strip() + '">');

template = template.replace('${link}\n','');

text_file = open(fileName + ".html", "wb");
text_file.write(template.encode('utf8'));
text_file.close();