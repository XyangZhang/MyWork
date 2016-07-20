<a href="add.php">添加内容</a><hr><hr>
<?php
	include("conn.php");
	$sql = "SELECT * FROM news ORDER BY id DESC";
	$query = mysql_query($sql);
	
	while ($rs = mysql_fetch_array($query)) {
		# code...
?>
		
		<h2>标题：<?php echo $rs['title']?> | <a href="edit.php?id=<?php echo $rs['id']?>">编辑</a> | <a href="del.php?del=<?php echo $rs['id']?>">删除</a></h2>
		<li><?php echo $rs['date']?></li>
		<p><?php echo $rs['contents']?></p>
		<hr>
<?php
	}
?>