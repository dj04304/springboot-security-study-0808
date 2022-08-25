const searchButton = document.querySelector(".search-button");

let nowPage = 1;

searchButton.onclick = () => {
	load(1);
}

load(nowPage);

function load(nowPage) {
	const searchFlag = document.querySelector(".search-select").value;
	const searchValue = document.querySelector(".search-input").value;
	console.log("searchFlag: "  + searchFlag);
	console.log("searchValue: " + searchValue);


	$.ajax({
		async: false,
		type: "get",
		url: "/api/v1/notice/list/" + nowPage,
		data: {
			"searchFlag": searchFlag,
			"searchValue": searchValue
		},
		dataType: "json",
		success: (response) => {
			getList(response.data);
		},
		error: (error) => {
			console.log(error);
		}
		
	});

}

function getList(list) {
	const tBody = document.querySelector("tbody");
	tBody.innerHTML = "";
	
	list.forEach(notice => {
		tBody.innerHTML += `
			<tr class="notice-row">
                <td>${notice.noticeCode}</td>
                <td>${notice.noticeTitle}</td>
                <td>${notice.userId}</td>
                <td>${notice.createDate}</td>
                <td>${notice.noticeCount}</td>
        	</tr>
		`;
	});
}

function getWriteButton() {
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

getWriteButton();

