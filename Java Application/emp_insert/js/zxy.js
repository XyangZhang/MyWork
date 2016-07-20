// 保存所有的通用操作函数
function validateEmpty (eleName) {
	var obj = document.getElementById(eleName);
	var msg = document.getElementById(eleName + "Msg");
	if (obj.value != "") {
		obj.className = "right";
		if (msg) {
			msg.innerHTML = "<font color='green'>内容输入正确！</font>";
		}
		return true;
	} else {
		obj.className = "wrong";
		if (msg) {
			msg.innerHTML = "<font color='red'>内容不允许为空！</font>";
		}
		return false;
	}
}
function validateRegex (eleName, regex) {
	var obj = document.getElementById(eleName);
	var msg = document.getElementById(eleName + "Msg");
	if (regex.test(obj.value)) {
		obj.className = "right";
		if (msg) {
			msg.innerHTML = "<font color='green'>内容输入正确！</font>";
		}
		return true;
	} else {
		obj.className = "wrong";
		if (msg) {
			msg.innerHTML = "<font color='red'>输入内容格式错误！</font>";
		}
		return false;
	}
}
function changeColor (obj, color) {
	obj.bgColor = color;
}