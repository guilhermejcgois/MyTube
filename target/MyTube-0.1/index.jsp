<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MyTube 552240</title>
    </head>
    <body>
        <form method="post" action="upload">
			<p><label for="videoToUpload">Upload:</label><input name="videoToUpload" type="file" /></p>
			<p><input type="submit" value="Enviar" /></p>
		</form>
        <form method="post" action="download">
			<p><label for="videoToDownload">Download:</label><input name="videoToDownload" type="file" /></p>
			<p><input type="submit" value="Enviar" /></p>
		</form>
    </body>
</html>
