function load() {
	const listFooter = document.querySelector(".list-footer");
	
	/* 로그인이 되어 있는 상태에서, ROLE_ADMIN을 포함할 때 */
	if(getUser() != null) {
		if(getUser().userRoles.includes("ROLE_ADMIN")) {
			listFooter.innerHTML += `
				<button type="button" class="notice-add-button">글쓰기</button>
			`;
			
			const noticeAddButton = document.querySelector(".notice-add-button");
			
			noticeAddButton.onclick = () => {
				location.href = "/notice/addition";
			}
		}
	}
}

load();