<html>
	<head>
		<title>注册页面</title>
		<meta charset="utf-8">
		<script type="text/javascript" src="js/emp.js"></script>
		<script type="text/javascript" src="js/zxy.js"></script>
		<link rel="stylesheet" type="text/css" href="css/zxy.css">
	</head>
	<body>
		<form action="" method="post" onsubmit="return myValidate()">
			<table border="1" cellpadding="5" cellspacing="0" width="100%" bgcolor="#F2F2F2">
				<tr onmousemove="changeColor(this, 'green')" onmouseout="changeColor(this, 'white')">
					<td colspan="3">
						<center>
							<span class="title">注册用户信息</span>
						</center>
					</td>
				</tr>
				<tr onmousemove="changeColor(this, 'green')" onmouseout="changeColor(this, 'white')">
					<td width="20%"><strong>用户名称：</strong></td>
					<td width="40%"><input type="text" name="username" id="username" class="init" maxlength="4" size="4" onblur="validateUsername()"></td>
					<td width="40%"><span id="empnoMsg"></span></td>
				</tr>
				<tr onmousemove="changeColor(this, 'green')" onmouseout="changeColor(this, 'white')">
					<td><strong>用户密码：</strong></td>
					<td><input type="password" name="password" id="password" class="init" onblur="validatePassword()"></td>
					<td><span id="passwordMsg"></span></td>
				</tr>
				<tr onmousemove="changeColor(this, 'green')" onmouseout="changeColor(this, 'white')">
					<td colspan="3">
						<input type="submit" value="提交">
						<input type="reset" value="重置">
					</td>
				</tr>
				<tr onmousemove="changeColor(this, 'green')" onmouseout="changeColor(this, 'white')">
					<td colspan="3">
						<a href="index.php"><input type="button" value="回到登陆界面"></a>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<?php
	include("conn.php"); // 引入连接数据库
	if (!empty($_POST['username'])) {
		@$username = $_POST['username'];
		@$password = $_POST['password'];
		$sql_find = "SELECT username FROM user WHERE username='$username'";
		$set = mysql_query($sql_find);
		$res_find = mysql_fetch_array($set);
		if ($res_find[0]) {
			echo "<h1>用户名存在！</h1>";
		} else {
			$sql = "INSERT INTO user (id, username, password) VALUES (null,'$username','$password')";
			$res = mysql_query($sql);
			if ($res) {
				echo "<h1>注册成功!</h1>";
			} else {
				echo "<h1>注册失败!</h1>";
			}
		}
	}
?>