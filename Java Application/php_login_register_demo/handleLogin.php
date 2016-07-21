<?php
	if ($_SERVER['REQUEST_METHOD'] == 'POST') {
		include("conn.php"); // 引入连接数据库
		if (!empty($_POST['username'])) {
			@$username = $_POST['username'];
			@$password = $_POST['password'];
			$sql = "SELECT `id`, `username`, `password` FROM `user` WHERE `username`='$username' && `password`='$password'";
			$set = mysql_query($sql);
			$res = mysql_fetch_array($set);
			if ($res[0]) {
				echo "<h1>登录成功!</h1>";
			} else {
				echo "<h1>登录失败!</h1>";
			}
		}	
	}	
?>