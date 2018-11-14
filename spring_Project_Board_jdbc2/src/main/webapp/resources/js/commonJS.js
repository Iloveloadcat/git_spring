function boardDelete(e, bId) {
//	e.preventDefault(); //a태그의 링크이벤트를 막고 원하는 이벤트를 처리하게 해줌
	if (confirm("삭제하시겠습니까?") == false) {
		console.log("삭제안한다");
	} else {
		console.log("삭제한다");
		location.href="delete?bId=" + bId;
	}
}