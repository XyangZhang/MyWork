<?php
	include("conn.php"); // 引入连接数据库
	if (!empty($_GET['id'])) {
		$id = $_GET['id'];
		$sql = "SELECT * FROM news WHERE id='$id'";
		$query = mysql_query($sql);
		$rs = mysql_fetch_array($query);
	}
	if (!empty($_POST['sub'])) {
		$title = $_POST['title'];
		$con = $_POST['con'];
		$sql = "UPDATE news SET title='$title', contents='$con'";
		$res=mysql_query($sql);
		echo "更新成功";
	}	
?>
<form action="edit.php" method="post">
	标题<input type="test" name="title" value="<?php echo $rs['title']?>"><br>
	内容<textarea rows="5" cols="50" name="con"><?php echo $rs['contents']?></textarea><br>
	<input type="submit" name="sub" value="发表">
</form>