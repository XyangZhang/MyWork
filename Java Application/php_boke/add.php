<?php
	include("conn.php"); // 引入连接数据库
	if (!empty($_POST['sub'])) {
		$title = $_POST['title'];
		$con = $_POST['con'];
		$sql = "INSERT INTO news (id, title, date, contents) VALUES (null,'$title', now(), '$con')";
		$res = mysql_query($sql);
		echo "插入成功";
	}
?>
<form action="add.php" method="post">
	标题<input type="test" name="title"><br>
	内容<textarea rows="5" cols="50" name="con"></textarea><br>
	<input type="submit" name="sub" value="发表">
</form>