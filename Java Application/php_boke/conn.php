<?php
	header('Content-Type: text/html; charset=UTF-8');
	@mysql_connect("localhost:3306", "root", "") or die("mysql连接失败");
	@mysql_select_db("test") or die("db wrong");
	mysql_set_charset("utf-8");
?>