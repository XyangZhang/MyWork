<?php
	include("conn.php"); // 引入连接数据库

	$username = $_POST['username'];
	$password = $_POST['password'];
	$sql = "SELECT `id`, `username`, `password` FROM `user` WHERE `username`='$username' && `password`='$password'";
	$set = mysql_query($sql);
	$result = mysql_fetch_array($set);
	print_r($result);
	if ($result[0]) {
		echo "登录成功";
	} else {
		echo "wrong";
	}
?>
